package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.res.drawable.Drawable;

public class EmptyDrawable extends Drawable {

    @Override
    public void draw(FlexBatch b, int x, int y) {

    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {

    }

    @Override
    public boolean contains(Vector2 p) {
	  return false;
    }

    @Override
    public void size(Vector2 v) {

    }

    @Override
    public void msg(short msg, byte dir, Object data) {
	  // discard everything
    }
}
