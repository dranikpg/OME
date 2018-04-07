package com.draniksoft.ome.utils.engine_load;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.WorldSerializationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.base_gfx.batchables.STB;
import com.draniksoft.ome.editor.esc_utils.OmeStrategy;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.manager.*;
import com.draniksoft.ome.editor.manager.uslac.TextureRManager;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjectLoadSystem;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.render.BaseRenderSys;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.render.map.MapRDebugSys;
import com.draniksoft.ome.editor.systems.render.map.MapRenderSys;
import com.draniksoft.ome.editor.systems.render.obj.LabelRenderSys;
import com.draniksoft.ome.editor.systems.render.obj.ObjRSys;
import com.draniksoft.ome.editor.systems.render.obj.PathRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.ConsoleSys;
import com.draniksoft.ome.editor.systems.support.ExecutionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.editor.systems.support.flows.ShowSystem;
import com.draniksoft.ome.editor.systems.support.flows.WorkflowSys;
import com.draniksoft.ome.editor.systems.time.ObjTimeCalcSys;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.support.execution_base.ut.StepLoader;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.GU;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EngineLoader {

    private static final String tag = "Engine Loader";

    public static WorldConfigurationBuilder cb;
    public static WorldConfiguration c;

    public static ResponseListener L;

    public static void clearStatics() {
        cb = null;
        c = null;

        l = null;
        cS = null;
        L = null;

	  execService = null;
    }

    public enum LoadS {
	  SNULL_PTR, CONFIF_B_B, CONFIG_B, DEPENDENCY_B, MGR_NTF, WORLD_B, UTILS, NULL_PTR
    }

    static LoadS cS = LoadS.CONFIF_B_B;

    static StepLoader l;
    static EngineLoadExecService execService;
    static ExecutorService nativeExeSerive = Executors.newFixedThreadPool(3);

    static volatile AssetManager assm = new AssetManager();


    public static void startLoad() {

        clearStatics();

        cS = LoadS.SNULL_PTR;

	  execService = new EngineLoadExecService(nativeExeSerive);

	  l = new StepLoader(execService);
	  l.setListener(new ResponseListener() {
		@Override
            public void onResponse(short code) {
		    if (code == ResponseCode.SUCCESSFUL) {
			  updateLoad();
                }
            }
        });

	  updateLoad();

        Gdx.app.debug(tag, "Started Engine Load");
    }

    private static void updateLoad() {

        cS = LoadS.values()[cS.ordinal() + 1];
	  Gdx.app.debug(tag, cS.name());

	  l.reset();

        if (cS == LoadS.CONFIF_B_B) {
		l.exec(new WorldConfigBdrBuilder());
	  } else if (cS == LoadS.CONFIG_B) {
		l.exec(new ConfigBuilder());
	  } else if (cS == LoadS.DEPENDENCY_B) {
		l.exec(new DependencyB());
		l.addShd(new GfxDependencyB());
	  } else if (cS == LoadS.WORLD_B) {
		l.exec(new WorldBuild());
	  } else if (cS == LoadS.MGR_NTF) {
		Iterator<AppDataManager> i = AppDO.I.getMgrI();
		AppDataManager m;
		while (i.hasNext()) {
		    m = i.next();
		    m.setLoadState(AppDataManager.ENGINE_LOAD);
		    l.exec(m);
		}
	  } else if (cS == LoadS.UTILS) {

		l.exec(new UtilClb());

	  } else if (cS == LoadS.NULL_PTR) {
		Gdx.app.debug(tag, "Passed states");
		L.onResponse((short) IntelligentLoader.LOAD_SUCCESS);
		execService.dispose();
	  }

    }

    public static void update() {
	  if (execService != null) execService.update();
    }

    private static class UtilClb implements Callable<Void> {

	  @Override
	  public Void call() throws Exception {

		STB.init();

		GU.whiteAC = FUtills.getRAC("i_casB@w");

		return null;

	  }
    }

    private static class WorldBuild implements Callable<Object> {

	  @Override
	  public Object call() throws Exception {
		try {
		    MainBase.engine = new com.artemis.World(c);
		} catch (Exception e) {
		    Gdx.app.error(tag, "", e);
		}
		return null;
	  }
    }

    private static class DependencyB implements Callable<Void> {

	  @Override
	  public Void call() throws Exception {
		InputMultiplexer mx = new InputMultiplexer();

		c.register(mx);
		c.register(assm);
		c.register(new MapLoadBundle());
		c.register("engine_l", l);

		c.register("exs", nativeExeSerive);

		Gdx.app.debug(tag, "Dependency B :: Logic ready");
		return null;
	  }
    }

    private static class ConfigBuilder implements Callable<Void> {

	  @Override
	  public Void call() throws Exception {
		c = cb.build();
		return null;
	  }
    }

    private static class GfxDependencyB extends SimpleSyncTask {
	  int cc = 0;

        Viewport gameVP;
        Viewport uiVP;

	  Batch b;

	  int base = 2;

	  public GfxDependencyB() {
		super(1);
	  }

	  void buildVPS() {

		OrthographicCamera gameC = new OrthographicCamera(1280, 960);
		gameC.setToOrtho(false);
		c.register("game_cam", gameC);
		OrthographicCamera uiCam = new OrthographicCamera(1280, 960);
		uiCam.setToOrtho(false);
		c.register("ui_cam", uiCam);

		gameVP = new ScreenViewport(gameC);
		uiVP = new ScreenViewport(uiCam);

		gameVP.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		uiVP.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		c.register("ui_vp", uiVP);
		c.register("game_vp", gameVP);
	  }

	  void buildRender() {
		ShapeRenderer r = new ShapeRenderer();
		GU.sr = r;
		c.register(r);
	  }

	  void buildRenderCache() {
		SpriteCache ca = new SpriteCache(200, false);
		c.register(ca);
	  }


	  @Override
	  public void run() {
		cc++;
		if (cc == base) {

		    GU.fetchMaxTexSize();

		} else if (cc == base + 1) {

		    buildVPS();


		} else if (cc == base + 2) {

		    buildRender();

		} else if (cc == base + 3) {

		    CompliantBatch<Quad2D> batch = new CompliantBatch<Quad2D>(Quad2D.class, 20000, true);
		    b = batch;
		    c.register("batch", batch);

		} else if (cc == base + 4) {

		    buildRenderCache();

		} else if (cc == base + 5) {

		    Stage uiS = new Stage(uiVP, b);

		    c.register("top_stage", uiS);

		} else if (cc >= base + 6) {

		    Gdx.app.debug(tag, "Dependency B :: GL_GFX finished passes");

		    active = false;
		}

        }


    }

    private static class WorldConfigBdrBuilder implements Callable<Void> {


	  @Override
	  public Void call() throws Exception {

		Gdx.app.debug(tag, "World config build");

		cb = new WorldConfigurationBuilder();
		// BASIC MANAGER

		cb.with(new IdentityManager());

		// USELESS ? not for long
		cb.with(new ProjectMgr());
		cb.with(new MapMgr());
		// old bollocks
		cb.with(new ResourceManager());
		cb.with(new SerializationManager());
		cb.with(new TimeMgr());
		// NEW PART
		cb.with(new ExtensionManager());
		cb.with(new TextureRManager());
		// SUPPORT SYSTEMS
		cb.with(new EditorSystem());
		cb.with(new ShowSystem());
		cb.with(new InputSys());
		cb.with(new ProjectLoadSystem());
		cb.with(new WorldSerializationManager());
		//
		cb.with(new ObjTimeCalcSys());
		cb.with(new ActionSystem());
		// PHYS POS SYS
		cb.with(new PositionSystem());
		cb.with(new CameraSys());
		// RENDER PART
		cb.with(new BaseRenderSys());
		//
		cb.with(new MapRenderSys());
		cb.with(new MapRDebugSys());
		//
		cb.with(new PathRenderSys());
		cb.with(new ObjRSys());
		cb.with(new LabelRenderSys());
		//
		cb.with(new OverlayRenderSys());
		//
		cb.with(new UiSystem());
		cb.with(new ConsoleSys());
		// Afterwards
		cb.with(new ExecutionSystem());
		cb.with(new WorkflowSys());
		cb.with(new OmeEventSystem());


		cb.register(new OmeStrategy());
		return null;
	  }
    }


}

