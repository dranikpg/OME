package com.draniksoft.ome.editor.base_gfx;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import com.draniksoft.ome.main_menu.MainBase;

public class EditorScreen implements Screen {

    private static final String tag = "EditorScreen";

    World e;

    Viewport uiVP;
    Viewport gameVP;

    MainBase b;


    InputMultiplexer m;

    public EditorScreen(MainBase b) {
        this.b = b;
    }

    @Override
    public void show() {

        e = MainBase.engine;

        uiVP = e.getInjector().getRegistered("ui_vp");
        gameVP = e.getInjector().getRegistered("game_vp");

        m = e.getInjector().getRegistered(InputMultiplexer.class);

        Gdx.input.setInputProcessor(m);

    }

    @Override
    public void render(float delta) {

        e.setDelta(Gdx.graphics.getRawDeltaTime());
        e.process();

    }

    @Override
    public void resize(int width, int height) {

        uiVP.update(width, height, true);
        gameVP.update(width, height);

        try {

		e.getSystem(OmeEventSystem.class).dispatch(new ResizeEvent(width, height));

        } catch (Exception e) {

            Gdx.app.error(tag, "Event::RESIZE crash ", e);

        }

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
