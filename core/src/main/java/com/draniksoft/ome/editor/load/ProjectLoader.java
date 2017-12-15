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
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.manager.EntitySrzMgr;
import com.draniksoft.ome.editor.manager.MapMgr;
import com.draniksoft.ome.editor.manager.ProjectMgr;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.manager.drawable.SimpleDrawableMgr;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class ProjectLoader {

    private static final String tag = "ProjectSaver";

    IntelligentLoader l;

    MapLoadBundle b;
    ResponseListener lst;

    volatile JsonValue indexV;

    AssetManager assM;

    World w;

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

	  s = Step.IDLE;


	  l = new IntelligentLoader();
	  l.setCompletionListener(new ResponseListener() {
		@Override
		public void onResponse(short code) {
		    if (code != IntelligentLoader.LOAD_FAILED) {
			  updateLoad();
		    } else {
			  notfiyFail();
		    }
		}
	  });
	  assM = w.getInjector().getRegistered(AssetManager.class);
	  updateLoad();


	  l.setMaxTs(5);
	  l.setPrefTs(5);
	  l.start();
    }

    private void updateLoad() {

	  s = Step.values()[s.ordinal() + 1];


	  Gdx.app.debug(tag, s.toString());

	  if (s == Step.NULL_PTR) {
		notifyEnd();
	  } else if (s == Step.DATA_RELEASE) {
		l.passRunnable(new Ts());
		//w.getSystem(EventSystem.class).dispatch(new ReleaseDataE());
		updateLoad();
	  } else if (s == Step.UNZIP) {
		updateLoad();
	  } else if (s == Step.INDEX_FETCH) {
		l.passRunnable(new IndexF());
	  } else if (s == Step.BASE) {
		l.passRunnable(new LoadT(w.getSystem(ProjectMgr.class)));
		l.passRunnable(new LoadT(w.getSystem(MapMgr.class)));
		l.passRunnable(new LoadT(w.getSystem(SimpleDrawableMgr.class)));
		l.passRunnable(new LoadT(w.getSystem(TimeMgr.class)));
		l.passRunnable(new LoadT(w.getSystem(EntitySrzMgr.class)));

	  } else if (s == Step.GFX_PAIR) {
		l.passGLRunnable(new GfxC());
	  }

    }

    private class Ts implements IRunnable {
	  @Override
	  public void run(IntelligentLoader l) {
		w.create();
	  }

	  @Override
	  public byte getState() {
		return IRunnable.RUNNING;
	  }
    }

    private class GfxC implements IGLRunnable {

	  IntBag ettyS;

	  ComponentMapper<MObjectC> moM;
	  ComponentMapper<DrawableC> dwc;
	  ComponentMapper<PosSizeC> psM;

	  int i = 0;

	  public GfxC() {

		ettyS = w.getAspectSubscriptionManager().get(Aspect.all(MObjectC.class)).getEntities();

		moM = w.getMapper(MObjectC.class);
		dwc = w.getMapper(DrawableC.class);
		psM = w.getMapper(PosSizeC.class);

	  }

	  @Override
	  public byte run() {

		if (i == ettyS.size()) {
		    Gdx.app.debug(tag, "Assembled " + i + " ettys");
		    return IGLRunnable.READY;
		}

		int e = ettyS.get(i);

		MObjectC mc = moM.get(e);

		DrawableC dwC = dwc.create(e);
		dwC.d = FUtills.fetchDrawable(mc.dwbID);

		PosSizeC psc = psM.create(e);
		psc.x = mc.x;
		psc.y = mc.y;
		psc.w = mc.w;
		psc.h = mc.h;

		i++;

		return IGLRunnable.RUNNING;
	  }
    }

    private class LoadT implements IRunnable {

	  public LoadT(LoadSaveManager mgr) {
		this.mgr = mgr;
	  }

	  LoadSaveManager mgr;


	  @Override
	  public void run(IntelligentLoader l) {

		mgr.load(l, ProjectLoader.this);

	  }

	  @Override
	  public byte getState() {
		return IRunnable.RUNNING;
	  }
    }

    private class IndexF implements IRunnable {
	  @Override
	  public void run(IntelligentLoader l) {
		indexV = FUtills.r.parse(Gdx.files.absolute(AppDO.I.F().getTmpDir().getAbsolutePath() + "/index.json"));
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
	  l.terminate();
    }


    public void update() {
	  l.update();
	  assM.update();
    }

}
