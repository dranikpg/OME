package com.draniksoft.ome.editor.support.render.def;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.support.render.core.SbsRenderer;

public class SelectionRenderer implements SbsRenderer {


    @Override
    public void added(World _w) {

    }

    @Override
    public void draw(CompliantBatch<Quad2D> b, OrthographicCamera cam) {

    }

    @Override
    public void removed() {

    }

    @Override
    public int[] getPos() {
	  return new int[0];
    }

}
