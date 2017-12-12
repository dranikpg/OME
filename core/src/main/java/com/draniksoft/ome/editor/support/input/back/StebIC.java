package com.draniksoft.ome.editor.support.input.back;

import com.artemis.World;
import com.draniksoft.ome.editor.support.input.InputController;

public class StebIC implements InputController {
    @Override
    public void init(World w) {

    }

    @Override
    public void destruct() {

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
}
