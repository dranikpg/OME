package com.draniksoft.ome.editor;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.components.PosSizeC;
import com.draniksoft.ome.editor.components.TexRegC;
import com.draniksoft.ome.editor.esc_utils.PMIStrategy;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.manager.ArchTransmuterMgr;
import com.draniksoft.ome.editor.systems.file_mgmnt.MapLoadSystem;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.render.BaseRenderSys;
import com.draniksoft.ome.editor.systems.render.MapRDebugSys;
import com.draniksoft.ome.editor.systems.render.MapRenderSys;
import com.draniksoft.ome.editor.systems.support.ConsoleSys;
import com.draniksoft.ome.utils.GUtils;


public class EditorAdapter extends ApplicationAdapter {

    public static String tag = "EditorAdapter";

    private static World engine;
    MapLoadBundle bundle;

    Viewport gameVP;


    public EditorAdapter(MapLoadBundle bundle){
        this.bundle = bundle;

    }

    @Override
    public void create() {

        engine = loadEngine();

        loadMap(500,500);

        GUtils.getWindow().setVisible(true);


    }

    @Override
    public void render() {

        engine.process();

       // System.out.println(Gdx.graphics.getFramesPerSecond());

    }

    private void loadMap(int w, int h) {

        Texture t = new Texture(Gdx.files.absolute("/media/vlad/Second/dev/tmp/OME_C/map.png"));

        TextureRegion[][] ts = TextureRegion.split(t,w,h);

        Gdx.app.debug(tag," TS Size "  + ts.length);

        for(int i = 0; i < ts.length; i++){

            for(int j = 0; j < ts.length; j++){

                int e = engine.getSystem(ArchTransmuterMgr.class).build(ArchTransmuterMgr.Codes.MAP_C);

                TexRegC dc = engine.getMapper(TexRegC.class).get(e);
                dc.d = ts[i][j];

                PosSizeC ps = engine.getMapper(PosSizeC.class).get(e);
                ps.x = j*w;
                ps.y = t.getHeight() - (i*h);
                ps.w = w;
                ps.h = h;


            }

        }


    }

    public World loadEngine(){

        if(engine != null){
            return engine;
        }


        InputMultiplexer multiplexer = new InputMultiplexer();
        AssetManager manager = new AssetManager();

        SpriteBatch batch = new SpriteBatch();
        ShapeRenderer shapeRenderer = new ShapeRenderer();

        OrthographicCamera gameCam = new OrthographicCamera(640,480);
        gameVP = new ScreenViewport(gameCam);
        Viewport uiVP = new FitViewport(640,480);

        SpriteCache mapCache = new SpriteCache(100000,false);

        CameraInputController camCtrl = new CameraInputController(gameCam);
        camCtrl.translateUnits = 0.1f;
        camCtrl.pinchZoomFactor = .1f;
        camCtrl.activateKey = Input.Keys.F2;

        /*


         */

        WorldConfigurationBuilder cb = new WorldConfigurationBuilder();

        cb.with(new ArchTransmuterMgr());
        cb.with(new MapLoadSystem());

        cb.with(new CameraSys());

        cb.with(new BaseRenderSys());

        cb.with(new MapRenderSys());
        cb.with(new MapRDebugSys());


        cb.with(new ConsoleSys());


        /*


         */

        WorldConfiguration c = cb.build();

        c.register(multiplexer);
        c.register(bundle);
        c.register(manager);

        c.register(batch);
        c.register(shapeRenderer);

        c.register(mapCache);

        c.register(gameCam);

        c.setInvocationStrategy(new PMIStrategy());

        /*


         */


        multiplexer.addProcessor(camCtrl);


        Gdx.input.setInputProcessor(multiplexer);


        return new World(c);

    }

    @Override
    public void resize(int width, int height) {

        if(engine != null){

            gameVP.update(width,height);

            engine.getSystem(ConsoleSys.class).resize(width,height);

        }

    }
}

