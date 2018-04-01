package com.draniksoft.ome.editor.support.render.base_mo;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.support.ems.base_em.MoveMOEM;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.SbsRenderer;
import com.kotcrab.vis.ui.VisUI;

public class MoveMORenderer implements SbsRenderer {

    int e;
    MoveMOEM em;

    PosBoundsC _c;

    Color oldC;


    float defW = 5;
    float w = 0;


    @Override
    public void added(World _w) {
	  _c = _w.getMapper(PosBoundsC.class).get(e);
	  // mc = _w.getMapper(PositionC.class).get(e);


	  oldC = new Color(VisUI.getSkin().getColor("primary"));
	  oldC.a = 0.1f;
    }

    @Override
    public void draw(CompliantBatch<Quad2D> b, OrthographicCamera cam) {

    }

    @Override
    public void removed() {

    }

    @Override
    public int[] getPos() {
	  return new int[]{OverlayPlaces.ENTITY_MAIN_BODY};
    }

    public void setEm(MoveMOEM em) {
	  this.em = em;
    }

    public void setE(int e) {
	  this.e = e;
    }
}
