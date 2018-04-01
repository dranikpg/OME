package com.draniksoft.ome.editor.support.input.base_mo;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.support.ems.base_em.MoveMOEM;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;

public class MoveMOIC implements InputController {

    private static final String tag = "MoveMOIC";


    MoveMOEM em;
    int e;

    World w;
    Viewport vp;

    Vector2 tmp;
    boolean reactOnKill = true;

    PosBoundsC c;

    @Override
    public void init(World w) {
	  this.w = w;
	  tmp = new Vector2();
        vp = w.getInjector().getRegistered("game_vp");
	  c = w.getMapper(PosBoundsC.class).get(e);
    }

    @Override
    public void destruct() {

    }

    @Override
    public void update() {
        tmp = vp.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY()));
	  w.getSystem(PositionSystem.class).setByCenterPos(e, (int) tmp.x, (int) tmp.y);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

	  if (keycode == Input.Keys.ESCAPE) em.notify(0);

	  if (keycode == Input.Keys.ENTER) em.notify(1);

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	  if (button == Input.Buttons.RIGHT) {
		update();
		em.notify(1);
	  }
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


    public void setReactOnKill(boolean reactOnKill) {
        this.reactOnKill = reactOnKill;
    }

    public void setE(int e) {
        this.e = e;
    }
}
