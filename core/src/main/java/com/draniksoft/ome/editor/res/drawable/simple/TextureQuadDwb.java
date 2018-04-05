package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.base_gfx.batchables.STB;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.utils.GU;

public class TextureQuadDwb extends Drawable {

    private TextureRAccesor ac;
    private int w = 20, h = 20;
    private float mns = 1, mxs = 1;

    public TextureQuadDwb(TextureRAccesor ac, int w, int h, float mns, float mxs) {
	  this.ac = ac;
	  this.w = w;
	  this.h = h;
	  this.mns = mns;
	  this.mxs = mxs;
    }

    @Override
    public void draw(FlexBatch b, int x, int y) {
	  if (ac == null) return;
	  int bx = x - w() / 2;
	  int by = y - h() / 2;
	  STB.rect(bx, by, w(), h(), ac.acl());
    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {
	  if (ac == null) return;
	  int nw = Math.min(w, this.w);
	  int nh = Math.min(h, this.h);
	  int bx = x - nw / 2;
	  int by = y - nh / 2;
	  STB.rect(bx, by, nw, nh, ac.acl());
    }

    @Override
    public boolean contains(Vector2 p) {
	  return Math.abs(p.x) < w() / 2 && Math.abs(p.y) < h() / 2;
    }

    @Override
    public void size(Vector2 v) {
	  v.x = Math.max(w(), v.x);
	  v.y = Math.max(h(), v.y);
    }

    public int w() {
	  return (int) (w * GU.CAM_SCALE(mns, mxs));
    }

    public int h() {
	  return (int) (h * GU.CAM_SCALE(mns, mxs));
    }
}
