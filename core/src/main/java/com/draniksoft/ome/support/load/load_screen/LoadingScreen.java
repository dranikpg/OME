package com.draniksoft.ome.support.load.load_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.BaseLoadController;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.GU;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.kotcrab.vis.ui.VisUI;

public class LoadingScreen implements Screen {

    BaseLoadController lc;

    ShapeRenderer r;
    SpriteBatch b;
    TextureRegion logoT;

    Viewport vp;

    boolean lR = false;


    @Override
    public void show() {

        startLoad();

        r = new ShapeRenderer();
        b = new SpriteBatch();
        vp = new FitViewport(1500, 700);

        VisUI.load("skin/skin.json");
	  logoT = VisUI.getSkin().getRegion("logo-dark");

        vp.apply();


    }

    private void startLoad() {

        lc = new BaseLoadController();
        lc.startLoad(new IntelligentLoader(), new ResponseListener() {
            @Override
            public void onResponse(short code) {

                if (code == IntelligentLoader.LOAD_FAILED) {


                }


                lR = true;
            }
        });


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(c, c, c, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        calc();

        setRArea();

        renderImg();

        lc.updateGL();

        updateL();

    }

    private void updateL() {

        if (lR & t == maxT) {


            lR = false;

        }


    }


    int maxX = 1500 - 600;
    int maxR = 350;

    int x = 0;
    int rd = 0;

    float maxT = 2f;
    float t;

    float c = .95f;

    private void calc() {
        t += Gdx.graphics.getDeltaTime();
        t = Math.min(maxT, t);

        x = (int) (Interpolation.fade.apply(t / maxT) * maxX);
        rd = (int) (Interpolation.fade.apply(t / maxT) * maxR);
    }

    private void setRArea() {
        Gdx.gl.glClearDepthf(1f);
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glDepthFunc(GL20.GL_LESS);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthMask(true);
        Gdx.gl.glColorMask(false, false, false, false);

        r.setProjectionMatrix(vp.getCamera().combined);
        r.setColor(c, c, c, 1f);
        r.begin(ShapeRenderer.ShapeType.Filled);

        r.circle(350, 400, rd);
        r.rect(650, 200, x, 700);

        r.end();
    }

    private void renderImg() {

        b.setProjectionMatrix(vp.getCamera().combined);
        b.begin();

        Gdx.gl.glColorMask(true, true, true, true);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_EQUAL);

        b.draw(logoT, 50, 200);

        b.end();
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
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
