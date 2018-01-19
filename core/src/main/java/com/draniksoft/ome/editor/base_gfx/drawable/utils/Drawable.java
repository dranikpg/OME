package com.draniksoft.ome.editor.base_gfx.drawable.utils;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Drawable {

    void draw(Batch b, float x, float y, float w, float h);

    void destruct();
}
