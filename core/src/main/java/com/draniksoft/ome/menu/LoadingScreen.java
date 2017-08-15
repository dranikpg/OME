package com.draniksoft.ome.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.Main;
import com.draniksoft.ome.preload.OneThreadPLoader;
import com.draniksoft.ome.preload.PreLoader;
import com.draniksoft.ome.utils.Const;
import com.draniksoft.ome.utils.ResponseListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisLabel;

public class LoadingScreen implements Screen{

    Main main;

    Stage stage;
    Viewport viewport;

    PreLoader loader;

    VisImage logo;

    VisLabel versionL;


    public LoadingScreen(Main main){

        this.main = main;

        stage = new Stage();
        VisUI.load();
        viewport = new FitViewport(640,480);

        logo = new VisImage(new Texture(Gdx.files.internal("menu/logo.png")));
        logo.setSize(640,480);
        logo.setPosition(0,0);
        stage.addActor(logo);

        versionL = new VisLabel(Const.appVFullName);
        versionL.setPosition(0,0);
        versionL.setColor(0,0,0,1);
        stage.addActor(versionL);


    }

    @Override
    public void show() {

        loader = new OneThreadPLoader();
        loader.init(new ResponseListener() {
            @Override
            public void onResponse(short code) {

                if(code == PreLoader.Codes.READY){

                    final MenuScreen.LaunchBundle b = new MenuScreen.LaunchBundle();
                    b.stage = stage;
                    b.viewport = viewport;

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            main.launchMS(b);
                        }
                    });
                }

            }
        });
        loader.startLoading();


    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

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
}
