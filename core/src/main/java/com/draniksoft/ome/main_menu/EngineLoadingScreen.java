package com.draniksoft.ome.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.SUtils;
import com.draniksoft.ome.utils.engine_load.EngineLoader;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.kotcrab.vis.ui.VisUI;
import com.rafaskoberg.gdx.typinglabel.TypingAdapter;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

public class EngineLoadingScreen implements Screen {

    private static final String tag = "EngineLoadingScreen";

    MainBase b;

    public EngineLoadingScreen(MainBase b) {
        this.b = b;
    }

    boolean launchReq = false;


    TypingLabel l;
    SpriteBatch batch;
    Stage st;


    @Override
    public void show() {

        EngineLoader.startLoad();
        EngineLoader.L = new ResponseListener() {
            @Override
            public void onResponse(short code) {
                if (code == IntelligentLoader.LOAD_SUCCESS) {
                    launchReq = true;
                }
            }
        };

        batch = b.getSupportBatch();
        st = new Stage(new ScreenViewport(), batch);


        l = new TypingLabel("{SLOW} {JUMP=0.7;1.5;0.7f;1} "
                + SUtils.getS("els_loadmsg") +
                "{WAIT} {ENDJUMP}", VisUI.getSkin(), "title");


        l.setTypingListener(new TypingAdapter() {
            @Override
            public void end() {
                l.restart();
            }
        });

        st.addActor(l);
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.9f, .9f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        st.act();
        st.draw();

        l.setPosition(Gdx.graphics.getWidth() / 2 - l.getWidth() / 5f, Gdx.graphics.getHeight() / 2 - l.getHeight() / 2);

        EngineLoader.update();

        if (launchReq) {
            b.launchEditor();
        }

        if (Gdx.graphics.getFramesPerSecond() < 30) {
            Gdx.app.debug(tag, "low fps");
        }

    }

    @Override
    public void resize(int width, int height) {

        st.getViewport().update(width, height);

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
