package com.draniksoft.ome.editor.base_gfx.drawable;

import com.badlogic.gdx.graphics.g2d.Batch;

public class LinkedDrawable implements Drawable {

    public int linkID;
    public Drawable link;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (link != null) link.draw(b, x, y, w, h);
    }


    public Drawable copy() {
	  return null;
    }

    @Override
    public String serialize() {
	  return null;
    }
}
