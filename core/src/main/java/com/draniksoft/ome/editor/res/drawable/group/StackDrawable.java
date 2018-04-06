package com.draniksoft.ome.editor.res.drawable.group;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.res.drawable.Drawable;

public class StackDrawable extends Drawable {

    Array<Drawable> ar;

    public StackDrawable(Array<Drawable> ar) {
	  this.ar = ar;
    }

    @Override
    public void draw(FlexBatch b, int x, int y) {
	  for (int i = 0; i < ar.size; i++) {
		ar.get(i).draw(b, x, y);
	  }
    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {
	  for (int i = 0; i < ar.size; i++) {
		ar.get(i).draw(b, x, y, w, h);
	  }
    }

    @Override
    public boolean contains(Vector2 p) {
	  for (int i = 0; i < ar.size; i++) {
		if (ar.get(i).contains(p)) return true;
	  }
	  return false;
    }

    @Override
    public void size(Vector2 v) {
	  for (int i = 0; i < ar.size; i++) {
		ar.get(i).size(v);
	  }
    }

    @Override
    protected void _update() {
	  for (int i = 0; i < ar.size; i++) {
		ar.get(i).update();
	  }
    }
}
