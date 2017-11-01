package com.draniksoft.ome.main_menu;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainBase extends Game {

    private static final String tag = "MainBase";

    public static World engine;

    MenuScreen ms;

    SpriteBatch b;

    @Override
    public void create() {

        b = new SpriteBatch();

        Gdx.app.debug(tag, "Created");

        ms = new MenuScreen(this);

        setScreen(ms);

    }

    public void launchEditor() {
        launchEditor(false);
    }

    public void launchEditor(boolean forceNew) {

        if (engine == null || forceNew) {

            buildEngine();

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
    public void dispose() {

        Gdx.app.debug(tag, "Disposing");

        super.dispose();
    }
}
