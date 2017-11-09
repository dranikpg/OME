package com.draniksoft.ome.main_menu;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.draniksoft.ome.editor.base_gfx.EditorScreen;
import com.draniksoft.ome.utils.GUtils;

public class MainBase extends Game {

    private static final String tag = "MainBase";

    public static World engine;

    MenuScreen ms;
    EditorScreen sc;

    SpriteBatch b;

    @Override
    public void create() {

        b = new SpriteBatch();

        Gdx.app.debug(tag, "\n\nCreated");

        ms = new MenuScreen(this);

        setScreen(ms);

        GUtils.getWindow().setSizeLimits(500, 500, 10000, 10000);

    }

    public void launchEditor() {
        launchEditor(false);
    }

    public void launchEditor(boolean forceNew) {

        if (engine == null || forceNew) {

            Gdx.app.debug(tag, "Redirecting request::open to engine build");

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
    public void dispose() {

        Gdx.app.debug(tag, "Disposing");

        super.dispose();
    }
}
