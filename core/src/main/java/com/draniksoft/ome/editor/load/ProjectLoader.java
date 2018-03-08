package com.draniksoft.ome.editor.load;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.srz.DrawableSrcC;
import com.draniksoft.ome.editor.components.srz.MapDimensC;
import com.draniksoft.ome.editor.manager.EntitySrzMgr;
import com.draniksoft.ome.editor.manager.MapMgr;
import com.draniksoft.ome.editor.manager.ProjectMgr;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.manager.drawable.SimpleAssMgr;
import com.draniksoft.ome.editor.systems.support.ExecutionSystem;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.support.execution_base.ut.CblT;
import com.draniksoft.ome.support.execution_base.ut.StepLoader;
import com.draniksoft.ome.support.execution_base.ut.SyncCblt;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ProjectLoader implements ExecutionProvider {

    private static final String tag = "ProjectLoader";

    StepLoader l;
    ExecutionProvider provider;

    AssetManager assM;

    MapLoadBundle b;
    ResponseListener lst;

    volatile JsonValue indexV;
    World w;

    @Override
    public <T> Future<T> exec(Callable<T> c) {
	  return provider.exec(c);
    }

    @Override
    public void addShd(SyncTask t) {
	  provider.addShd(t);
    }

    private enum Step {
	  IDLE,
	  DATA_RELEASE,
	  UNZIP, INDEX_FETCH,
	  BASE, // Starts entity load, loads assets, adds loader, loads map
	  GFX_PAIR,
	  NULL_PTR
    }

    Step s = Step.IDLE;

    public void setW(World w) {
	  this.w = w;
    }

    public void start(MapLoadBundle b, ResponseListener lst) {
	  this.lst = lst;
	  this.b = b;
	  if (w == null) notfiyFail();
	  provider = w.getSystem(ExecutionSystem.class);
	  assM = w.getInjector().getRegistered(AssetManager.class);

	  s = Step.IDLE;

	  l = new StepLoader(provider, assM, new ResponseListener() {
		@Override
		public void onResponse(short code) {
		    updateLoad();
		}
	  });

	  updateLoad();

    }

    private void updateLoad() {
	  s = Step.values()[s.ordinal() + 1];

	  l.reset();
	  Gdx.app.debug(tag, s.toString());

	  if (s == Step.NULL_PTR) {
		l.dispose();
		notifyEnd();
	  } else if (s == Step.DATA_RELEASE) {
		l.exec(CblT.WRAP(new ReleaseData()));
		updateLoad();
	  } else if (s == Step.UNZIP) {
		updateLoad();
	  } else if (s == Step.INDEX_FETCH) {
		l.exec(CblT.WRAP(new IndexF()));
	  } else if (s == Step.BASE) {
		l.exec(new LoadT(w.getSystem(ProjectMgr.class)));
		l.exec(new LoadT(w.getSystem(MapMgr.class)));
		l.exec(new LoadT(w.getSystem(SimpleAssMgr.class)));
		l.exec(new LoadT(w.getSystem(TimeMgr.class)));
		l.exec(new LoadT(w.getSystem(EntitySrzMgr.class)));
	  } else if (s == Step.GFX_PAIR) {
		l.addShd(SyncCblt.WRAP(new GfxC()));
	  }

    }

    private class ReleaseData implements IRunnable {
	  @Override
	  public void run(IntelligentLoader l) {
		IntBag e = w.getAspectSubscriptionManager().get(Aspect.all()).getEntities();
		for (int i = 0; i < e.size(); i++) {
		    w.delete(e.get(i));
		}

	  }

	  @Override
	  public byte getState() {
		return IRunnable.RUNNING;
	  }
    }

    private class GfxC implements IGLRunnable {

	  IntBag ettyS;

	  ComponentMapper<MapDimensC> moM;
	  ComponentMapper<DrawableC> dwc;
	  ComponentMapper<PosSizeC> psM;
	  ComponentMapper<DrawableSrcC> dwbSCM;

	  int i = 0;

	  public GfxC() {

		ettyS = w.getAspectSubscriptionManager().get(Aspect.all(MapDimensC.class)).getEntities();

		moM = w.getMapper(MapDimensC.class);
		dwc = w.getMapper(DrawableC.class);
		psM = w.getMapper(PosSizeC.class);
		dwbSCM = w.getMapper(DrawableSrcC.class);

	  }

	  @Override
	  public byte run() {

		if (i == ettyS.size()) {
		    Gdx.app.debug(tag, "Assembled " + i + " ettys");
		    return IGLRunnable.READY;
		}

		int e = ettyS.get(i);

		MapDimensC mc = moM.get(e);


		DrawableC dwC = dwc.create(e);
		DrawableSrcC dwbSC = dwbSCM.get(e);

		dwbSC.c = GUtils.fetchIt();

		dwC.d = dwbSC.c.build();
		dwC.d.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, FUtills.NULL_ARRAY);

		PosSizeC psc = psM.create(e);
		psc.x = mc.x;
		psc.y = mc.y;
		psc.w = mc.w;
		psc.h = mc.h;

		i++;

		return IGLRunnable.RUNNING;
	  }
    }

    private class LoadT implements Callable {

	  public LoadT(LoadSaveManager mgr) {
		this.mgr = mgr;
	  }
	  LoadSaveManager mgr;
	  @Override
	  public Object call() throws Exception {
		mgr.load(ProjectLoader.this);
		return null;
	  }

    }

    private class IndexF implements IRunnable {
	  @Override
	  public void run(IntelligentLoader l) {
		indexV = FUtills.r.parse(Gdx.files.absolute(AppDO.I.F().getTmpDir().path() + "/index.json"));
		Gdx.app.debug(tag, "Parsed JSON");
	  }

	  @Override
	  public byte getState() {
		return IRunnable.RUNNING;
	  }
    }

    public JsonValue getIndexV() {
	  return indexV;
    }

    private void notifyEnd() {

	  Gdx.app.debug(tag, "Load successful");

	  lst.onResponse(ResponseCode.SUCCESSFUL);

    }

    private void notfiyFail() {

	  lst.onResponse(ResponseCode.FAILED);

    }

    public void dispose() {

    }

}
