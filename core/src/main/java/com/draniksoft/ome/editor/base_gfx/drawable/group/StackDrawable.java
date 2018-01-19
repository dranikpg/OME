package com.draniksoft.ome.editor.base_gfx.drawable.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;

public class StackDrawable implements Drawable, GroupDrawable {

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
    public void destruct() {

    }

    @Override
    public Drawable copy(Array<Drawable> ar) {
	  StackDrawable dwb = new StackDrawable(ar);
	  return dwb;
    }

    @Override
    public Drawable newCopy(Array<Drawable> ar) {
	  return copy(ar);
    }

}
