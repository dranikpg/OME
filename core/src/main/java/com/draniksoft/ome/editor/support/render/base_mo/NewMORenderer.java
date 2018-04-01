package com.draniksoft.ome.editor.support.render.base_mo;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.support.render.core.SbsRenderer;

public class NewMORenderer implements SbsRenderer {

    private static final String tag = "NewMORenderer";

    TextureRegion d;
    World _w;

    float x = -1;
    float y = -1;

    int w;
    int h;

    public void setMousePos(float x, float y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void added(World _w) {
	  this._w = _w;
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
