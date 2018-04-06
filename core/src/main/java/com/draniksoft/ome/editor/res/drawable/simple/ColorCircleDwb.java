package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.base_gfx.batchables.STB;
import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.utils.GU;

public class ColorCircleDwb extends Drawable {

    private int r = 20;
    private float mns = 1, mxs = 1;

    private ColorProvider pv;
    private Color c = new Color(Color.WHITE);

    public ColorCircleDwb(int r, float mns, float mxs, ColorProvider pv) {
	  this.r = r;
	  this.mns = mns;
	  this.mxs = mxs;
	  this.pv = pv;
    }

    //

    @Override
    public void draw(FlexBatch b, int x, int y) {

	  b.draw(STB.circle(x, y, r(), c));

    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {

	  int nr = Math.min(Math.min(w, h) / 2, r);

	  b.draw(STB.circle(x, y, nr, c));

    }

    @Override
    public void _update() {
	  updtc();
    }

    @Override
    public boolean contains(Vector2 p) {
	  return p.dst(0, 0) < r();
    }

    @Override
    public void size(Vector2 v) {
	  v.x = Math.max(v.x, r());
	  v.y = Math.max(v.y, r());
    }


    //

    @Override
    protected void _init() {
	  super._init();
	  //  if(pv != null) c  = pv.getC();
    }


    //

    private void updtc() {
	  pv.getC(c);
    }

    public int r() {
	  return (int) (r * GU.CAM_SCALE(mns, mxs));
    }

}
