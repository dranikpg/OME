package com.draniksoft.ome.editor.support.input.base_mo;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.ems.base_em.NewMOEM;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.support.render.base_mo.NewMORenderer;

/**
 * The input controller used for adding new locations
 */

public class NewMOIC implements InputController {

    static final String tag = "NewMOIC";

    boolean ter = false;

    World w;

    Vector2 tmp;
    Viewport vp;

    NewMORenderer rdr;
    NewMOEM m;

    public boolean ignoreDestruct = false;

    public void setRdr(NewMORenderer rdr) {
        this.rdr = rdr;
    }

    public void setOwnerMode(NewMOEM m) {
        this.m = m;
    }

    @Override
    public void init(World w) {
        this.w = w;
        tmp = new Vector2();


        vp = w.getInjector().getRegistered("game_vp");
    }

    @Override
    public void destruct() {

        if (ignoreDestruct) return;

        m.inputStopped();

    }


    @Override
    public void update() {

        tmp = vp.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY()));

        rdr.setMousePos(tmp.x, tmp.y);

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

        Gdx.app.debug(tag, "TD");

        if (ter) {
            ter = false;
            return false;
        }

        tmp = ((Viewport) w.getInjector().getRegistered("game_vp")).unproject(tmp.set(screenX, screenY));

        m.createLoc(tmp);

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        ter = true;
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
