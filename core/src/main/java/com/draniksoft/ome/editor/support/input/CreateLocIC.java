package com.draniksoft.ome.editor.support.input;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.actions.loc.CreateLocationA;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;

/**
 * The input controller used for adding new locations
 */

public class CreateLocIC implements InputController {

    static final String tag = "CreateLocIC";

    boolean ter = false;

    World w;

    Vector2 tmp;

    @Override
    public void init(World w) {
        this.w = w;
        tmp = new Vector2();


        w.getSystem(InputSys.class).setDefIC(new TimedSelectIC(true));


    }

    @Override
    public void destruct() {
        w.getSystem(InputSys.class).setDefIC(new SelectIC());
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

        if (ter) {
            ter = false;
            return false;
        }

        tmp = ((Viewport) w.getInjector().getRegistered("game_vp")).unproject(tmp.set(screenX, screenY));

        w.getSystem(ActionSystem.class).exec(new CreateLocationA((int) tmp.x, (int) tmp.y, 50, 50, "DEF"));

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
