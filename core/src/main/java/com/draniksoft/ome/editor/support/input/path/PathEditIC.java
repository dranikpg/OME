package com.draniksoft.ome.editor.support.input.path;

import com.artemis.World;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.res.path.b.PathDesc;
import com.draniksoft.ome.editor.res.path.b.PathSDesc;
import com.draniksoft.ome.editor.support.ems.path.EditPathEM;
import com.draniksoft.ome.editor.support.input.InputController;

public class PathEditIC implements InputController {

    public int _e;
    public EditPathEM em;

    Viewport vp;

    Vector2 tmpV;
    Rectangle rect;

    @Override
    public void init(World w) {

	  tmpV = new Vector2();
	  rect = new Rectangle();
	  rect.setSize(20, 20);

	  vp = w.getInjector().getRegistered("game_vp");

    }

    public void newSel() {
	  if (em.getCurIDX() < 0) {
		sIDX = -1;
		em.setDragIDX(sIDX);
	  }
	  hadChanges = false;
    }

    int sIDX = -1;

    boolean hadChanges = false;

    @Override
    public boolean keyDown(int keycode) {


	  if (keycode == Input.Keys.SPACE) {
		if (em.isNewM() && em.getCurIDX() > -1) {
		    em.setSel(-1);
		} else {
		    em.newPath();
		}
	  } else if (keycode == Input.Keys.ESCAPE) {
		if (em.getCurIDX() < 0) {
		    em.selfDestroy();
		} else {
		    em.setRevert(true);
		    em.setSel(-1);
		}
	  } else if (keycode == Input.Keys.BACKSPACE) {
		if (em.getCurIDX() < 0 && !em.isNewM()) return false;
		PathDesc d = em.getPathDesc();
		if (d.ar.size > 1) {
		    d.ar.removeIndex(d.ar.size - 1);
		    em.recompute();
		}
	  }

	  return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

	  tmpV.set(screenX, screenY);
	  vp.unproject(tmpV);

	  if (em.isNewM() && button == Input.Buttons.RIGHT) {

		addPoint(tmpV.x, tmpV.y);
		hadChanges = true;

		em.recompute();

		return true;

	  } else if (em.getCurIDX() > -1) {
		int i = 0;
		sIDX = -1;
		for (Vector2 p : em.getPathDesc().ar) {
		    rect.setCenter(p);
		    if (rect.contains(tmpV)) {
			  sIDX = i;
			  break;
		    }
		    i++;
		}
		em.setDragIDX(sIDX);
		return sIDX > 0;
	  }

	  return false;
    }

    private void addPoint(float x, float y) {
	  PathDesc d = em.getPathDesc();
	  if (d != null) d.ar.add(new Vector2(x, y));

    }

    public boolean hadChanges() {
	  return hadChanges;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

	  if (sIDX > -1) {
		tmpV.set(screenX, screenY);
		vp.unproject(tmpV);
		em.getPathDesc().ar.get(sIDX).set(tmpV);

		hadChanges = true;

		if (sIDX == em.getPathDesc().ar.size - 1) {
		    PathDescC dc = em.getdC();
		    if (dc.ar.size > em.getCurIDX() + 1) {
			  PathSDesc d = dc.ar.get(em.getCurIDX() + 1);
			  if (d.alignToPrev) {
				if (d.ar.size > 0)
				    d.ar.get(0).set(tmpV);
			  }
		    }
		}

		em.recompute();

		return true;
	  }
	  return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	  return false;
    }

    @Override
    public boolean keyUp(int keycode) {
	  return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
	  return false;
    }

    @Override
    public void destruct() {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean scrolled(int amount) {
	  return false;
    }


}
