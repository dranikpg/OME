package com.draniksoft.ome.editor.support.input.path;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.struct.path.srz.PathSzr;
import com.draniksoft.ome.editor.support.ems.path.EditPathEM;
import com.draniksoft.ome.editor.support.input.InputController;

public class EditPathIC implements InputController {

    private static final String tag = "EditPathIC";

    public EditPathEM em;

    PathSzr p;

    Viewport vp;

    public void updateSelection() {
	  p = em.getSelSrz();
    }

    public void updateMode() {

    }

    @Override
    public void init(World w) {

	  vp = w.getInjector().getRegistered("game_vp");

    }

    @Override
    public void destruct() {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean keyDown(int keycode) {
	  if (keycode == Input.Keys.SPACE) {
		if (!em.editing()) em.handle(EditPathEM.ACTION.NEW_PATH);
	  } else if (keycode == Input.Keys.ESCAPE) {
		em.handle(EditPathEM.ACTION.EXIT);
	  } else if (keycode == Input.Keys.ENTER) {
		em.handle(EditPathEM.ACTION.SAVE);
	  }
	  return false;
    }

    @Override
    public boolean keyUp(int keycode) {
	  return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

	  if (!em.editing() || p == null) return false;

	  if (button == Input.Buttons.LEFT) return false;

	  Gdx.app.debug(tag, "Touchdown");

	  Vector2 v = new Vector2(screenX, screenY);
	  vp.unproject(v);

	  p.pts.add(v);
	  em.updateEditing();

	  return true;
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
