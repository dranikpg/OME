package com.draniksoft.ome.main_menu;

import com.artemis.World;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.draniksoft.ome.editor.base_gfx.screen_base.EditorScreen;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.mgmnt_base.base.BaseLoadController;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.load_screen.LoadingScreen;
import com.draniksoft.ome.utils.FM;
import com.draniksoft.ome.utils.GU;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.kotcrab.vis.ui.VisUI;

public class MainBase extends Game {

    private static final String tag = "MainBase";

    public static volatile World engine;

    EditorScreen sc;
    LoadingScreen ls;

    SpriteBatch b;

    BaseLoadController lc;

    @Override
    public void create() {
        Gdx.app.debug(tag, "Created");
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        VisUI.load("skin/skin.json");
        b = new SpriteBatch();

        lc = new BaseLoadController();
        lc.startLoad(new IntelligentLoader(), new ResponseListener() {
            @Override
            public void onResponse(short code) {
                lc = null;
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MainBase.this.launchEditor();
                    }
                });
            }
        });

        setScreen(new Screen() {
            @Override
            public void show() {

            }

            @Override
            public void render(float delta) {
                Gdx.gl.glClearColor(1,1,0.4f,1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            }

            @Override
            public void resize(int width, int height) {

            }

            @Override
            public void pause() {

            }

            @Override
            public void resume() {

            }

            @Override
            public void hide() {

            }

            @Override
            public void dispose() {

            }
        });


    }


    public void launchEditor() {
        launchEditor(false);
    }

    public void launchEditor(boolean forceNew) {

        if (engine == null || forceNew) {

		Gdx.app.debug(tag, "Redirecting request::open to engine construct");

            buildEngine();

        } else {

            if (sc == null) {
                Gdx.app.debug(tag, "Launchin editor screen");
                sc = new EditorScreen(this);
            }

            setScreen(sc);

        }

    }

    private void buildEngine() {

        EngineLoadingScreen sc = new EngineLoadingScreen(this);

        setScreen(sc);

    }

    public SpriteBatch getSupportBatch() {
        return b;
    }

    @Override
    public void render() {
	  FM.frame();
	  if(lc != null) lc.updateGL();
	  super.render();
    }

    @Override
    public void dispose() {

	  if (engine != null) engine.dispose();

        final IntelligentLoader l = new IntelligentLoader();
        l.setMaxTs(1);
        l.setPrefTs(1);

        AppDO.I.F().setLoadState(AppDataManager.TERMINATE_RUN);
        l.passRunnable(AppDO.I.F());

        AppDO.I.C().setLoadState(AppDataManager.TERMINATE_RUN);
        l.passRunnable(AppDO.I.C());


        AppDO.I.setLoadState(AppDataManager.TERMINATE_RUN);
        l.passRunnable(AppDO.I);

        l.start();

        l.setCompletionListener(new ResponseListener() {
            @Override
            public void onResponse(short code) {
                l.terminate();
            }
        });

        super.dispose();
    }
}
