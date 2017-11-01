package com.draniksoft.ome.utils.engine_load;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.manager.*;
import com.draniksoft.ome.editor.systems.file_mgmnt.AssetLScheduleSys;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.editor.systems.render.BaseRenderSys;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.render.map.MapRDebugSys;
import com.draniksoft.ome.editor.systems.render.map.MapRenderSys;
import com.draniksoft.ome.editor.systems.render.obj.ObjRSys;
import com.draniksoft.ome.editor.systems.render.obj.PhysRDebugSys;
import com.draniksoft.ome.editor.systems.render.ui.UIRenderSystem;
import com.draniksoft.ome.editor.systems.support.*;
import com.draniksoft.ome.editor.systems.time.TimeActivitySys;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.struct.ResponseListener;
import net.mostlyoriginal.api.event.common.EventSystem;

public class EngineLoader {

    private static final String tag = "Engine Loader";

    public static WorldConfigurationBuilder cb;
    public static WorldConfiguration c;

    public static void clearStatics() {
        cb = null;
        c = null;

        l = null;
        cS = null;
    }

    public enum LoadS {
        SNULL_PTR, CONFIF_B_B, CONFIG_B, DEPENDENCY_B, WORLD_B, NULL_PTR
    }

    static LoadS cS = LoadS.CONFIF_B_B;

    static IntelligentLoader l;

    public static void startLoad() {

        clearStatics();

        cS = LoadS.CONFIF_B_B;
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

        }

    }

    public static void update() {

        if (l != null) l.update();

    }

    private static class DependencyB implements IRunnable {

        @Override
        public void run(IntelligentLoader l) {

            World phys = new World(new Vector2(0, 0), true);
            InputMultiplexer mx = new InputMultiplexer();
            AssetManager assm = new AssetManager();

            c.register(phys);
            c.register(mx);
            c.register(assm);

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

        @Override
        public byte run() {
            cc++;
            if (cc == 1) {

                OrthographicCamera gameC = new OrthographicCamera(640, 480);
                c.register("game_cam", gameC);
                OrthographicCamera uiCam = new OrthographicCamera(640, 480);
                c.register("ui_cam", uiCam);

                gameVP = new ScreenViewport();
                uiVP = new ScreenViewport();

                gameVP.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                uiVP.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

                c.register("ui_vp", uiVP);
                c.register("game_vp", gameVP);


            } else if (cc == 2) {

                SpriteBatch b = new SpriteBatch();

                c.register(b);

                ShapeRenderer r = new ShapeRenderer();

                c.register(r);

            } else if (cc == 3) {

                SpriteCache ca = new SpriteCache(1000, false);

                c.register(ca);

            } else if (cc == 4) {

                Stage uiS = new Stage();

                c.register("top_stage", uiS);

            } else if (cc == 5) {

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

            cb.with(new DrawableMgr());

            cb.with(new EntitySrzMgr());

            cb.with(new TimeMgr());

            // SUPPORT SYSTEMS

            cb.with(new EditorSystem());

            cb.with(new InputSys());

            cb.with(new ProjecetLoadSys());

            //

            cb.with(new UiSystem());

            cb.with(new TimeActivitySys());

            cb.with(new ActionSystem());


            // PHYS POS SYS

            cb.with(new PhysicsSys());

            // RENDER PART

            cb.with(new CameraSys());

            cb.with(new BaseRenderSys());

            cb.with(new MapRenderSys());
            cb.with(new MapRDebugSys());


            cb.with(new OverlayRenderSys());


            cb.with(new ObjRSys());

            cb.with(new PhysRDebugSys());

            cb.with(new UIRenderSystem());

            cb.with(new ConsoleSys());


            // Afterwards

            cb.with(new AssetLScheduleSys());

            cb.with(new WorkflowSys());

            cb.with(new EventSystem());

        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }


}

