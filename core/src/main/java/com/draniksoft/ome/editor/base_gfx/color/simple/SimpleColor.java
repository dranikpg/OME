package com.draniksoft.ome.editor.base_gfx.color.simple;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.base_gfx.color.utils.ColorProvider;

public class SimpleColor extends ColorProvider {

    public Color _c;

    @Override
    public void get(Color c) {
	  c.set(_c);
    }


    @Override
    public ColorProvider copy() {
	  return null;
    }
}
