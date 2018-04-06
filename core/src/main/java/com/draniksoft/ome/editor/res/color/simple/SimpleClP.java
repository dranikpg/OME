package com.draniksoft.ome.editor.res.color.simple;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.color.ColorProvider;

public class SimpleClP extends ColorProvider {


    static float ipol = 0.08f;

    private Color _c = Color.WHITE;

    public void color(Color c) {
	  _c = new Color(c);
    }

    @Override
    public void getC(Color c) {
	  if (_c.equals(c)) return;
	  c.r = c.r + (_c.r - c.r) * ipol;
	  c.g = c.g + (_c.g - c.g) * ipol;
	  c.b = c.b + (_c.b - c.b) * ipol;
	  c.a = c.a + (_c.a - c.a) * ipol;
    }

    @Override
    public Color getC() {
	  return new Color(_c);
    }
}
