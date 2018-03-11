package com.draniksoft.ome.main_menu;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.draniksoft.ome.editor.base_gfx.screen_base.EditorScreen;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class MainBase extends Game {

    private static final String tag = "MainBase";

    public static volatile World engine;

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
        GUtils.frame();
        super.render();
    }

    @Override
    public void dispose() {

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
