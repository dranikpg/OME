package com.draniksoft.ome.editor.base_gfx.drawable;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface Drawable {

    void draw(Batch b, float x, float y, float w, float h);

    Drawable copy();

    String serialize();
}
