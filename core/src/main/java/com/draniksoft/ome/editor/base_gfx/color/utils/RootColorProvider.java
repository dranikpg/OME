package com.draniksoft.ome.editor.base_gfx.color.utils;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res_mgmnt.res_ifaces.RootResource;

public class RootColorProvider extends ColorProvider implements RootResource<Drawable> {

    public ColorProvider p;
    Color c;


    public Color get() {
	  if (c != null) p.get(c);
	  return c;
    }

    @Override
    public void get(Color c) {
	  c.set(get());
    }

    @Override
    public ColorProvider copy() {
	  return null;
    }

    @Override
    public void init(World _w) {

    }

    @Override
    public void restore(World _w) {

    }
}
