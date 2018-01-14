package com.draniksoft.ome.editor.base_gfx.drawable.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;

/*
	Top level drawable class. Will be used in future for extensions/caching
 */

public class RootDrawable implements Drawable {
    @Override
    public void draw(Batch b, float x, float y, float w, float h) {

    }

    @Override
    public Drawable copy() {
	  return null;
    }

    @Override
    public void serialize(JsonValue value) {

    }
}
