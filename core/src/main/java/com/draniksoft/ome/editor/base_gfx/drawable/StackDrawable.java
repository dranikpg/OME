package com.draniksoft.ome.editor.base_gfx.drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class StackDrawable implements Drawable {

    Array<Drawable> ar;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  for (Drawable d : ar) {
		d.draw(b, x, y, w, h);
	  }
    }

    public Drawable copy() {
	  return null;
    }

    @Override
    public String serialize() {
	  return null;
    }
}
