package com.draniksoft.ome.editor.base_gfx.drawable.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;

public class StackDrawable implements Drawable {

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

    public Drawable copy() {
	  return null;
    }

    @Override
    public void serialize(JsonValue value) {
	  return;
    }
}
