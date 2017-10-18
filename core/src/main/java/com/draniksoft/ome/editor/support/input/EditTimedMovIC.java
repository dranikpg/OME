package com.draniksoft.ome.editor.support.input;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.ems.timed.EditTimedMovsEM;

public class EditTimedMovIC implements InputController {


    EditTimedMovsEM em;
    private Array<MoveDesc> arr;

    Viewport vp;
    Vector2 tv;

    @Override
    public void init(World w) {


        tv = new Vector2();
        vp = w.getInjector().getRegistered("game_vp");

    }

    @Override
    public void destruct() {

    }

    @Override
    public void update() {

        calcPos(tv.set(Gdx.input.getX(), Gdx.input.getY()));

        em.px = tv.x;
        em.py = tv.y;

    }

    public void setArr(Array<MoveDesc> arr) {
        this.arr = arr;
    }

    private void calcPos(Vector2 v) {
        vp.unproject(v);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        em.handleKey(keycode);

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        calcPos(tv.set(screenX, screenY));

        Gdx.app.debug("", button + "");

        if (em.newM && button == 1) {

            em.addDescOnLP();

            return false;
        }

        int i = 0;
        for (MoveDesc d : arr) {

            if (matches(d)) {

                em.setSelI(i);

                return false;
            }

            i++;

        }

        return false;
    }

    float ac = 20;

    private boolean matches(MoveDesc d) {

        return d.end_x - ac < tv.x && tv.x < d.end_x + ac && d.end_y - ac < tv.y && tv.y < d.end_y + ac;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

    public void setEm(EditTimedMovsEM em) {
        this.em = em;
    }


}
