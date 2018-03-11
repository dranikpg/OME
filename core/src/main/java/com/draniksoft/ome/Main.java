package com.draniksoft.ome;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.support.load.load_screen.LoadingScreen;
import com.draniksoft.ome.utils.GUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 *  Main Application manager until Editor/Presenter win is opened
 *
 *
 * */
public class Main extends Game {

    public Main(){

    }

    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        setScreen(new LoadingScreen());


    }

    @Override
    public void render() {
        GUtils.frame();
        super.render();
    }
}