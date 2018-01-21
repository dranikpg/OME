package com.draniksoft.ome.editor.base_gfx.drawable.simple;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;

public class LinkedDrawable extends Drawable {

    public int linkID = -1;
    public Drawable link;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (link != null) link.draw(b, x, y, w, h);
    }


    @Override
    public Drawable copy() {
	  return null;
    }
}
