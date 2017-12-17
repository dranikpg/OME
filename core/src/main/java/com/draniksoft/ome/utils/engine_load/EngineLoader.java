package com.draniksoft.ome.utils.engine_load;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.WorldSerializationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.esc_utils.OmeStrategy;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.manager.*;
import com.draniksoft.ome.editor.manager.drawable.SimpleDrawableMgr;
import com.draniksoft.ome.editor.systems.file_mgmnt.AssetLScheduleSys;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjectLoadSystem;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.render.BaseRenderSys;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.render.map.MapRDebugSys;
import com.draniksoft.ome.editor.systems.render.map.MapRenderSys;
import com.draniksoft.ome.editor.systems.render.obj.ObjRSys;
import com.draniksoft.ome.editor.systems.render.obj.PathRenderSys;
import com.draniksoft.ome.editor.systems.support.*;
import com.draniksoft.ome.editor.systems.time.ObjTimeCalcSys;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.struct.ResponseListener;
import net.mostlyoriginal.api.event.common.EventSystem;

import java.util.Iterator;

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
    }

    public enum LoadS {
	  SNULL_PTR, CONFIF_B_B, CONFIG_B, DEPENDENCY_B, MGR_NTF, WORLD_B, NULL_PTR
    }

    static LoadS cS = LoadS.CONFIF_B_B;

    static IntelligentLoader l;

    public static void startLoad() {

        clearStatics();

        cS = LoadS.SNULL_PTR;
        l = new IntelligentLoader();
        l.setCompletionListener(new ResponseListener() {
            @Override
            public void onResponse(short code) {
                if (code == IntelligentLoader.LOAD_SUCCESS) {
                    updateLoad();
                }
            }
        });

        updateLoad();

        l.start();

        Gdx.app.debug(tag, "Started Engine Load");
    }

    private static void updateLoad() {

        cS = LoadS.values()[cS.ordinal() + 1];

        Gdx.app.debug(tag, cS.name());

        if (cS == LoadS.CONFIF_B_B) {
            l.passRunnable(new WorldConfigBdrBuilder());
        } else if (cS == LoadS.CONFIG_B) {
            l.passRunnable(new ConfigBuilder());
        } else if (cS == LoadS.DEPENDENCY_B) {
            l.passRunnable(new DependencyB());
            l.passGLRunnable(new GfxDependencyB());
	  } else if (cS == LoadS.WORLD_B) {
		l.passRunnable(new WorldBuild());
	  } else if (cS == LoadS.MGR_NTF) {
		Iterator<AppDataManager> i = AppDO.I.getMgrI();
		AppDataManager m;
		while (i.hasNext()) {
		    m = i.next();
		    m.setLoadState(AppDataManager.ENGINE_LOAD);
		    l.passRunnable(m);
		}
	  } else if (cS == LoadS.NULL_PTR) {
		Gdx.app.debug(tag, "Passed states");
		L.onResponse((short) IntelligentLoader.LOAD_SUCCESS);
		l.terminate();
	  }

    }

    public static void update() {

        if (l != null) l.update();

    }

    private static class WorldBuild implements IRunnable {

        @Override
        public void run(IntelligentLoader l) {
            MainBase.engine = new com.artemis.World(c);
        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

    private static class DependencyB implements IRunnable {

        @Override
        public void run(IntelligentLoader l) {

            InputMultiplexer mx = new InputMultiplexer();
            AssetManager assm = new AssetManager();

            c.register(mx);
            c.register(assm);

            c.register(new MapLoadBundle());

            c.register("engine_l", l);

            Gdx.app.debug(tag, "Dependency B :: Logic ready");
        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

    private static class ConfigBuilder implements IRunnable {
        @Override
        public void run(IntelligentLoader l) {

            c = cb.build();

        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

    private static class GfxDependencyB implements IGLRunnable {
        int cc = 0;

        Viewport gameVP;
        Viewport uiVP;

        SpriteBatch b;

        @Override
        public byte run() {

            cc++;
            if (cc == 1) {

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


            } else if (cc == 2) {

                b = new SpriteBatch();

                c.register(b);

                ShapeRenderer r = new ShapeRenderer();

                GUtils.sr = r;
                c.register(r);

            } else if (cc == 3) {

                SpriteCache ca = new SpriteCache(10000, false);

                c.register(ca);

            } else if (cc == 4) {

                Stage uiS = new Stage(uiVP, b);

                c.register("top_stage", uiS);

            } else if (cc >= 5) {

                Gdx.app.debug(tag, "Dependency B :: GL_GFX finished passes");

                return IGLRunnable.READY;
            }


            return IGLRunnable.RUNNING;
        }
    }

    private static class WorldConfigBdrBuilder implements IRunnable {

        @Override
        public void run(IntelligentLoader l) {
            cb = new WorldConfigurationBuilder();

            // ESC MANAGER

            cb.with(new ArchTransmuterMgr());

            // OTHER MANAGER

            cb.with(new ProjectMgr());

            cb.with(new MapMgr());

		cb.with(new SimpleDrawableMgr());

            cb.with(new EntitySrzMgr());

            cb.with(new TimeMgr());

            // SUPPORT SYSTEMS

            cb.with(new EditorSystem());

            cb.with(new InputSys());

		cb.with(new ProjectLoadSystem());

		cb.with(new WorldSerializationManager());

            //


		cb.with(new ObjTimeCalcSys());

            cb.with(new ActionSystem());


            // PHYS POS SYS

		cb.with(new PositionSystem());

            // RENDER PART

            cb.with(new CameraSys());

            cb.with(new BaseRenderSys());

            cb.with(new MapRenderSys());
            cb.with(new MapRDebugSys());


            cb.with(new OverlayRenderSys());


		cb.with(new PathRenderSys());

            cb.with(new ObjRSys());

            cb.with(new UiSystem());

            cb.with(new ConsoleSys());


            // Afterwards

            cb.with(new AssetLScheduleSys());

            cb.with(new WorkflowSys());

            cb.with(new EventSystem());

		cb.register(new OmeStrategy());

        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }


}

