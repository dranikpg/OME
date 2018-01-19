package com.draniksoft.ome.editor.base_gfx.color.utils;

import com.badlogic.gdx.graphics.Color;

public class RootColorProvider extends Color implements ColorProvider {

    public Color get() {
	  return this;
    }


    @Override
    public void get(Color c) {
	  c.set(get());
    }
}
