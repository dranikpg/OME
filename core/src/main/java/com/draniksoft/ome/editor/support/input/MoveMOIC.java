package com.draniksoft.ome.editor.support.input;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.render.MoveMORenderer;
import com.draniksoft.ome.editor.support.workflow.def_ems.MoveMOEM;

public class MoveMOIC implements InputController {

    private static final String tag = "MoveMOIC";

    MoveMORenderer rdr;
    MoveMOEM em;
    int e;

    World w;
    Viewport vp;

    Vector2 tmp;
    boolean reactOnKill = true;

    @Override
    public void init(World w) {
        tmp = new Vector2();
        vp = w.getInjector().getRegistered("game_vp");
    }

    @Override
    public void destruct() {

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

        if (keycode == Input.Keys.ESCAPE) em.keyPressed(0);

        if (keycode == Input.Keys.ENTER) em.keyPressed(1);

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
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

    public void setEm(MoveMOEM em) {
        this.em = em;
    }

    public void setRdr(MoveMORenderer rdr) {
        this.rdr = rdr;
    }

    public void setReactOnKill(boolean reactOnKill) {
        this.reactOnKill = reactOnKill;
    }

    public void setE(int e) {
        this.e = e;
    }
}
