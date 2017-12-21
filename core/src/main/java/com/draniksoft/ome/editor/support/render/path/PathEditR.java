package com.draniksoft.ome.editor.support.render.path;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.support.container.path.PathDesc;
import com.draniksoft.ome.editor.support.container.path.PathSDesc;
import com.draniksoft.ome.editor.support.ems.path.EditPathEM;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.kotcrab.vis.ui.VisUI;

public class PathEditR implements OverlyRendererI {

    public int _e;
    public EditPathEM em;

    float cclR = 5f;

    @Override
    public void render(ShapeRenderer r, OrthographicCamera c) {


	  r.setColor(VisUI.getSkin().getColor("grey"));
	  Array<PathSDesc> dar = em.getdC().ar;
	  for (PathDesc _d : dar) {
		if (em.getPathDesc() != _d && _d.ar.size < 2) continue;
		Vector2 p = _d.ar.get(_d.ar.size - 1);
		r.circle(p.x, p.y, cclR);
	  }

	  if (em.getCurIDX() < 0) return;

	  r.setColor(VisUI.getSkin().getColor("secondary"));
	  PathDesc d = em.getPathDesc();
	  int idx = 0;
	  for (Vector2 p : d.ar) {
		r.circle(p.x, p.y, cclR * ((idx == em.getDragIDX()) ? 1.5f : 1));
		idx++;
	  }

    }


    public void newSel() {
    }

    @Override
    public void added(World _w) {
    }

    @Override
    public int[] getPos() {
	  return new int[]{OverlayPlaces.PATH};
    }

    @Override
    public int getId() {
	  return IDs.PathR;
    }

    @Override
    public void render(SpriteBatch b, OrthographicCamera c) {


    }

}
