package com.draniksoft.ome.editor.support.render.base_mo;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.srz.MapDimensC;
import com.draniksoft.ome.editor.support.ems.base_em.MoveMOEM;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.OverlayRendererI;
import com.kotcrab.vis.ui.VisUI;

public class MoveMORenderer implements OverlayRendererI {

    public int xp, yp;

    int e;
    MoveMOEM em;

    PosSizeC _c;
    MapDimensC mc;

    Color oldC;

    @Override
    public void render(SpriteBatch b, OrthographicCamera c) {


    }

    float defW = 5;
    float w = 0;

    @Override
    public void render(ShapeRenderer r, OrthographicCamera c) {

	  r.set(ShapeRenderer.ShapeType.Filled);

	  r.setColor(oldC);

	  renderOld(r, c);

	  r.setColor(VisUI.getSkin().getColor("secondary"));

	  renderNew(r, c);


    }

    private void renderNew(ShapeRenderer r, OrthographicCamera c) {
	  w = defW * MathUtils.clamp(c.zoom, 0.1f, 2f);

	  float s = w * 1 / 4;
	  r.rect(_c.x - w - s, _c.y - s - w, w, _c.h + 2 * s + 2 * w); // left
	  r.rect(_c.x + _c.w + s, _c.y - w - s, w, _c.h + 2 * s + 2 * w); // right
	  r.rect(_c.x - s, _c.y - s - w, _c.w + 2 * s, w); // bottom
	  r.rect(_c.x - s, _c.y + _c.h + s, _c.w + 2 * s, w); // top
    }

    private void renderOld(ShapeRenderer r, OrthographicCamera c) {
	  w = defW * MathUtils.clamp(c.zoom, 0.1f, 2f);

	  float s = w * 1 / 4;
	  r.rect(mc.x - w - s, mc.y - s - w, w, mc.h + 2 * s + 2 * w); // left
	  r.rect(mc.x + mc.w + s, mc.y - w - s, w, mc.h + 2 * s + 2 * w); // right
	  r.rect(mc.x - s, mc.y - s - w, mc.w + 2 * s, w); // bottom
	  r.rect(mc.x - s, mc.y + mc.h + s, mc.w + 2 * s, w); // top

    }

    @Override
    public void added(World _w) {
	  _c = _w.getMapper(PosSizeC.class).get(e);
	  mc = _w.getMapper(MapDimensC.class).get(e);


	  oldC = new Color(VisUI.getSkin().getColor("primary"));
	  oldC.a = 0.1f;
    }

    @Override
    public int[] getPos() {
	  return new int[]{OverlayPlaces.ENTITY_MAIN_BODY};
    }

    @Override
    public int getId() {
	  return OverlayRendererI.IDs.MoveMO;
    }

    public void setEm(MoveMOEM em) {
	  this.em = em;
    }

    public void setE(int e) {
	  this.e = e;
    }
}
