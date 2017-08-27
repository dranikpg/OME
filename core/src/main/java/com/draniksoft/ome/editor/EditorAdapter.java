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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.esc_utils.PMIStrategy;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.manager.ArchTransmuterMgr;
import com.draniksoft.ome.editor.manager.MapManager;
import com.draniksoft.ome.editor.manager.ProjectManager;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.render.BaseRenderSys;
import com.draniksoft.ome.editor.systems.render.MapRDebugSys;
import com.draniksoft.ome.editor.systems.render.MapRenderSys;
import com.draniksoft.ome.editor.systems.render.UIRenderSystem;
import com.draniksoft.ome.editor.systems.support.ConsoleSys;
import com.draniksoft.ome.editor.systems.support.UiSystem;
import com.draniksoft.ome.utils.GUtils;


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

        SpriteCache mapCache = new SpriteCache(1000, false);

        /*


         */

        WorldConfigurationBuilder cb = new WorldConfigurationBuilder();

        cb.with(new ArchTransmuterMgr());
        cb.with(new ProjectManager());
        cb.with(new MapManager());

        cb.with(new ProjecetLoadSys());

        cb.with(new UiSystem());

        cb.with(new CameraSys());

        cb.with(new BaseRenderSys());

        cb.with(new MapRenderSys());
        cb.with(new MapRDebugSys());

        cb.with(new UIRenderSystem());

        cb.with(new ConsoleSys());


        /*


         */

        WorldConfiguration c = cb.build();

        c.register(multiplexer);
        c.register(bundle);
        c.register(manager);

        c.register("game_cam", gameCam);
        c.register("ui_cam", uiCam);

        c.register(batch);
        c.register(shapeRenderer);

        c.register("top_stage", uiStage);

        c.register(mapCache);


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
}

