package com.draniksoft.ome.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class MenuScreen implements Screen {

    MainBase base;

    Stage stage;
    Viewport vp;

    VisTable rootT;
    VisTable contentT;
    VisTable controlT;

    Color c;

    public MenuScreen(final MainBase base) {
        this.base = base;

        vp = new ScreenViewport();
        stage = new Stage(vp, base.getSupportBatch());

        rootT = new VisTable();
        rootT.setFillParent(true);
        stage.addActor(rootT);

        contentT = new VisTable();
        controlT = new VisTable();

        VisImage logo = new VisImage("logo");
        controlT.add(logo);
        controlT.row();
        logo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                base.launchEditor(true);
                return true;
            }
        });

        controlT.add(new VisTextButton("LOL"));
        controlT.row();
        controlT.add(new VisTextButton("LOL"));
        controlT.row();
        controlT.add(new VisTextButton("LOL"));
        controlT.row();

        rootT.add(controlT).fill().padBottom(50).padRight(50).padLeft(10).padTop(50);
        rootT.add(contentT).expand().fill().pad(50);

        contentT.add("HELLO GUYS THIS IT THE NEW SERIES").left().expandX();


        c = VisUI.getSkin().getColor("grey");
        Gdx.input.setInputProcessor(stage);

        rootT.setDebug(true, true);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClearColor(c.r, c.g, c.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        vp.apply();

        stage.act();
        stage.draw();

    }


    @Override
    public void resize(int width, int height) {

        vp.update(width, height, true);

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
