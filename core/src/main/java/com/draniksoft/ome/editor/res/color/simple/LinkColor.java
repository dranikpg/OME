package com.draniksoft.ome.editor.res.color.simple;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.color.utils.ColorProvider;

public class LinkColor extends ColorProvider {

    // const provider
    public ColorProvider pv;

    public int id = -1;

    @Override
    public void get(Color c) {
        pv.get(c);
    }

    @Override
    public ColorProvider copy() {
	  LinkColor c = new LinkColor();
	  c.pv = pv;
	  c.id = id;
	  return c;
    }
}
