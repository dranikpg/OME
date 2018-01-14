package com.draniksoft.ome.editor.base_gfx.drawable.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;

public interface Drawable {

    void draw(Batch b, float x, float y, float w, float h);

    Drawable copy();

    void serialize(JsonValue value);

}
