package com.draniksoft.ome.editor.res.drawable.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.GroupResource;

public class StackDrawable extends GroupDrawable implements GroupResource<Drawable> {

    Array<Drawable> ar;

    public StackDrawable(Array<Drawable> ar) {
	  this.ar = new Array<Drawable>(ar);
    }

    public StackDrawable() {
	  ar = new Array<Drawable>();
    }

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  for (Drawable d : ar) {
		d.draw(b, x, y, w, h);
	  }
    }


    @Override
    public Drawable[] getChildren() {
	  Drawable[] da = ar.toArray(Drawable.class);
	  return da;
    }

    @Override
    public void update(Array<Drawable> ar) {
	  this.ar.clear();
	  this.ar.addAll(ar);
    }

    @Override
    public Drawable copy() {
	  StackDrawable d = new StackDrawable(ar);
	  return d;
    }
}
