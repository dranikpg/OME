package com.draniksoft.ome.editor.support.render.core;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;

public interface SbsRenderer {

    void added(World _w);

    void draw(CompliantBatch<Quad2D> b, OrthographicCamera cam);

    void removed();

    int[] getPos();

}
