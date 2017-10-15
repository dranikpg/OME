package com.draniksoft.ome.editor;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.esc_utils.PMIStrategy;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.manager.*;
import com.draniksoft.ome.editor.systems.file_mgmnt.AssetLScheduleSys;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.gui.FloatUILSupSys;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.editor.systems.render.BaseRenderSys;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.render.map.MapRDebugSys;
import com.draniksoft.ome.editor.systems.render.map.MapRenderSys;
import com.draniksoft.ome.editor.systems.render.obj.ObjRSys;
import com.draniksoft.ome.editor.systems.render.obj.PhysRDebugSys;
import com.draniksoft.ome.editor.systems.render.ui.FloatUIRenderSys;
import com.draniksoft.ome.editor.systems.render.ui.UIRenderSystem;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.ConsoleSys;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.time.TimeActivitySys;
import com.draniksoft.ome.mgmnt_base.AppDataObserver;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.ResponseListener;
import net.mostlyoriginal.api.event.common.EventSystem;


public class EditorAdapter extends ApplicationAdapter {

    public static String tag = "EditorAdapter";

    private static World engine;
    MapLoadBundle bundle;

    Viewport gameVP;
    Viewport uiVP;


    public EditorAdapter(MapLoadBundle bundle){
        this.bundle = bundle;

    }

    @Override
    public void create() {


        engine = loadEngine();

        GUtils.getWindow().setVisible(true);


    }

    @Override
    public void render() {

        engine.process();

       // System.out.println(Gdx.graphics.getFramesPerSecond());

    }


    public World loadEngine(){

        if(engine != null){
            return engine;
        }


        InputMultiplexer multiplexer = new InputMultiplexer();
        AssetManager manager = new AssetManager();

        OrthographicCamera gameCam = new OrthographicCamera(640,480);
        OrthographicCamera uiCam = new OrthographicCamera(640, 480);

        gameVP = new ScreenViewport(gameCam);
        uiVP = new ScreenViewport(uiCam);


        SpriteBatch batch = new SpriteBatch();
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        Stage uiStage = new Stage(uiVP);
        Stage gameStage = new Stage(uiVP);

        SpriteCache mapCache = new SpriteCache(1000, false);

        com.badlogic.gdx.physics.box2d.World phys = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, 0), true);


        /*


         */

        WorldConfigurationBuilder cb = new WorldConfigurationBuilder();

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

        cb.with(new FloatUILSupSys());

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


        cb.with(new FloatUIRenderSys());

        cb.with(new UIRenderSystem());

        cb.with(new ConsoleSys());


        // Afterwards

        cb.with(new AssetLScheduleSys());


        /*




         */

        // multiplexer.addProcessor(uiStage);

        /*



         */
        WorldConfiguration c = cb.build();

        c.register(multiplexer);
        c.register(bundle);
        c.register(manager);

        c.register("ui_vp", uiVP);
        c.register("game_vp", gameVP);

        c.register("game_cam", gameCam);
        c.register("ui_cam", uiCam);

        c.register(batch);
        c.register(shapeRenderer);

        c.register("top_stage", uiStage);
        c.register("game_stage", gameStage);

        c.register(mapCache);

        c.register(phys);

        c.setSystem(EventSystem.class);


        c.setInvocationStrategy(new PMIStrategy());

        /*


         */


        Gdx.input.setInputProcessor(multiplexer);


        return new World(c);

    }

    @Override
    public void resize(int width, int height) {

        if(engine != null){

            gameVP.update(width,height);

            uiVP.update(width, height, true);

            engine.getSystem(ConsoleSys.class).resize(width,height);

        }

    }


    @Override
    public void pause() {

        Gdx.app.debug(tag, "AppAdapter :: Paused ");

    }


    @Override
    public void dispose() {

        Gdx.app.debug(tag, "Disposed");

        AppDataObserver.getI().save(new ResponseListener() {
            @Override
            public void onResponse(short code) {

            }
        }, false);


    }
}

