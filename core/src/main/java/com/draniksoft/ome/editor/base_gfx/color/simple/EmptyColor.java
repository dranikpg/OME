package com.draniksoft.ome.editor.base_gfx.color.simple;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.base_gfx.color.utils.ColorProvider;

public class EmptyColor extends ColorProvider {

    @Override
    public void get(Color c) {

    }

    @Override
    public ColorProvider copy() {
	  return null;
    }
}
