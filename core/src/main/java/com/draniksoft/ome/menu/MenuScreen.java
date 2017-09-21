package com.draniksoft.ome.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.menu.button_bar.ButtonBar;
import com.draniksoft.ome.menu.dynamic_vs.DynamicViewController;
import com.draniksoft.ome.mgmnt_base.AppDataObserver;
import com.draniksoft.ome.utils.ResponseListener;

public class MenuScreen implements Screen {

    public static final String tag = "MenuScreen";

    public static class LaunchBundle{

        public Stage stage;
        public Viewport viewport;

    }

    Stage stage;
    Viewport viewport;

    ButtonBar bar;
    DynamicViewController vc;
    Group vcDrawGroup;

    // barwidth,
    int dimens[] = {150,10};

    public MenuScreen(LaunchBundle b){
        this.stage = b.stage;
        this.viewport = b.viewport;

        Gdx.app.debug(tag,"Creating");

        stage.clear();

        vcDrawGroup = new Group();
        vc = new DynamicViewController(this,
                new int[]{
                        dimens[0]+dimens[1],
                        dimens[1],
                        (int) (viewport.getWorldWidth() - (dimens[0]+2*dimens[1])),
                        (int) (viewport.getWorldHeight()-2*dimens[1])
                },vcDrawGroup);
        vc.open(DynamicViewController.Codes.EDITOR);

        bar = new ButtonBar(vc);
        bar.background("window-bg");
        bar.setPosition(0,0);
        bar.setSize(dimens[0],viewport.getWorldHeight());

        stage.addActor(vcDrawGroup);
        stage.addActor(bar);

    }



    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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

        AppDataObserver.getI().save(new ResponseListener() {
            @Override
            public void onResponse(short code) {

            }
        }, true);


    }
}
