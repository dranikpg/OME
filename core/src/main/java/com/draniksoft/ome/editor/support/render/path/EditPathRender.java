package com.draniksoft.ome.editor.support.render.path;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.struct.path.srz.PathSzr;
import com.draniksoft.ome.editor.support.ems.path.EditPathEM;
import com.draniksoft.ome.editor.support.render.core.OverlayRendererI;
import com.kotcrab.vis.ui.VisUI;

public class EditPathRender implements OverlayRendererI {

    public EditPathEM em;

    PathSzr srz;

    public void updateSelection() {
	  srz = em.getSelSrz();
    }

    public void updateMode() {

    }

    @Override
    public void render(SpriteBatch b, OrthographicCamera c) {

    }

    @Override
    public void render(ShapeRenderer r, OrthographicCamera c) {
	  if (srz == null) return;
	  if (!em.editing()) return;

	  if (r.getCurrentType() != ShapeRenderer.ShapeType.Filled) r.set(ShapeRenderer.ShapeType.Filled);

	  r.setColor(VisUI.getSkin().getColor("secondary"));
	  for (Vector2 p : srz.pts) {
		r.circle(p.x, p.y, 10);
	  }

    }

    @Override
    public void added(World _w) {

    }

    @Override
    public int[] getPos() {
	  return new int[0];
    }

    @Override
    public int getId() {
	  return 0;
    }
}
