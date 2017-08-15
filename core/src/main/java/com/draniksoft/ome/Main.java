package com.draniksoft.ome;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.menu.LoadingScreen;
import com.draniksoft.ome.menu.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 *  Main Application manager until Editor/Presenter win is opened
 *
 *
 * */
public class Main extends Game {

    public Main(){

    }

    MenuScreen ms;

    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        setScreen(new LoadingScreen(this));

    }

    public void launchMS(MenuScreen.LaunchBundle b){

        ms = new MenuScreen(b);

        setScreen(ms);

    }



}