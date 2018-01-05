package com.draniksoft.ome.editor.base_gfx.drawable;

import com.badlogic.gdx.graphics.g2d.Batch;

public class EmptyDrawable implements Drawable {

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {

    }

    public Drawable copy() {
	  return null;
    }

    @Override
    public String serialize() {
	  return null;
    }
}
