package com.draniksoft.ome.editor.support.render.ut;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.base_gfx.batchables.STB;
import com.draniksoft.ome.editor.support.render.core.OverlayRenderer;

public class FixedLineOverlayR implements OverlayRenderer {

    public FixedLineOverlayR() {
    }

    @Override
    public void added(World _w) {

    }

    public int t() {
	  return 1;
    }

    @Override
    public void draw(CompliantBatch<Quad2D> b, OrthographicCamera cam) {

	  float w = cam.viewportWidth * cam.zoom;
	  float h = cam.viewportHeight * cam.zoom;

	  int s = (int) ((cam.position.x - w) / 100f) - 1;
	  int e = (int) ((cam.position.x + w) / 100f) + 1;


	  for (int i = s; i < e; i++) {
		b.draw(STB.rect(
			  i * 100 - t() / 2,
			  cam.position.y - h / 2,
			  t(),
			  h,
			  Color.LIGHT_GRAY
		));
	  }


    }

    @Override
    public void removed() {

    }

    @Override
    public int[] getPos() {
	  return new int[0];
    }
}
