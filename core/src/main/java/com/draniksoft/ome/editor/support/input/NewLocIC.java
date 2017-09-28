package com.draniksoft.ome.editor.support.input;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.render.NewLocRenderer;
import com.draniksoft.ome.editor.support.workflow.NewLocEditMode;
import com.draniksoft.ome.editor.systems.support.InputSys;

/**
 * The input controller used for adding new locations
 */

public class NewLocIC implements InputController {

    static final String tag = "NewLocIC";

    boolean ter = false;

    World w;

    Vector2 tmp;

    NewLocRenderer rdr;
    NewLocEditMode m;

    public boolean ignoreDestruct = false;

    public void setRdr(NewLocRenderer rdr) {
        this.rdr = rdr;
    }

    public void setOwnerMode(NewLocEditMode m) {
        this.m = m;
    }

    @Override
    public void init(World w) {
        this.w = w;
        tmp = new Vector2();


        w.getSystem(InputSys.class).setDefIC(new TimedSelectIC(true));


    }

    @Override
    public void destruct() {

        w.getSystem(InputSys.class).setDefIC(new SelectIC());

        if (ignoreDestruct) return;

        m.inputStopped();

    }


    @Override
    public void update() {

        tmp = ((Viewport) w.getInjector().getRegistered("game_vp")).unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY()));

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
