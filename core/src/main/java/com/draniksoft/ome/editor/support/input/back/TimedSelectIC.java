package com.draniksoft.ome.editor.support.input.back;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;

public class TimedSelectIC implements InputController {

    final static String tag = "TimedSelectIC";

    float tdt = -1;

    /**
     * Removes main tool on selecting or deselecting something
     */
    boolean removeMain = false;

    float tl = 10;
    float pressT = .1f;

    Vector2 tV;
    IntArray es;

    World w;
    Viewport gameVP;
    Viewport uiVP;
    PositionSystem ps;

    public TimedSelectIC(boolean removeMain) {
        this.removeMain = removeMain;
    }

    public TimedSelectIC() {
    }

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

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();

        tV.set(x, y);

        Gdx.app.debug(tag, "TD AT " + tV.toString());

        tV = gameVP.unproject(tV);

	  int e = ps.getTouch(x, y);

        SelectionChangeE ev = new SelectionChangeE();
        ev.n = e;
	  ev.old = w.getSystem(EditorSystem.class).sel;

	  w.getSystem(OmeEventSystem.class).dispatch(ev);


    }

    @Override
    public void update() {
        if (tdt >= 0) {
            tdt += Gdx.graphics.getDeltaTime();
        }

        if (tdt > pressT) {
            Gdx.app.debug(tag, "Touch down");
            processI();
            tdt = -1;
        }
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
        tdt = 0;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        tdt = -1;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        tdt = -1;
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
