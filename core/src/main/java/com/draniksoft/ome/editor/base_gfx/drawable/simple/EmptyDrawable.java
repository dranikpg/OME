package com.draniksoft.ome.editor.base_gfx.drawable.simple;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;

public class EmptyDrawable implements Drawable {

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {

    }

    public Drawable copy() {
	  return null;
    }

    @Override
    public void serialize(JsonValue value) {
	  return;
    }
}
