package com.draniksoft.ome.editor.support.input.back;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import net.mostlyoriginal.api.event.common.EventSystem;

public class SelectIC implements InputController {

    final static String tag = "SelectIC";

    boolean it = false;

    Vector2 tV;
    IntArray es;

    World w;

    Viewport gameVP;
    Viewport uiVP;

    PositionSystem ps;

    @Override
    public void init(World w) {
        this.w = w;

        gameVP = w.getInjector().getRegistered("game_vp");
        uiVP = w.getInjector().getRegistered("ui_vp");

	  ps = w.getSystem(PositionSystem.class);

        tV = new Vector2();
        es = new IntArray();

    }

    @Override
    public void destruct() {

    }


    private void processI() {

        if (it) {
            it = false;
            return;
        }

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();

        tV.set(x, y);
        tV = gameVP.unproject(tV);

	  int e = ps.getTouch((int) tV.x, (int) tV.y);

        SelectionChangeE ev = new SelectionChangeE();
        ev.n = e;
	  ev.old = w.getSystem(EditorSystem.class).sel;

        w.getSystem(EventSystem.class).dispatch(ev);

	  Gdx.app.debug(tag, "NEW INPUT " + e);

    }

    @Override
    public void update() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        processI();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        it = true;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
