package com.draniksoft.ome.editor.base_gfx.drawable.utils;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res_mgmnt.res_ifaces.RootResource;

/*
	Top level drawable class. Will be used in future for extensions/caching
 */

public class RootDrawable extends Drawable implements RootResource<Drawable> {

    public Drawable d;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (d != null) d.draw(b, x, y, w, h);
    }


    @Override
    public Drawable copy() {
	  return null;
    }

    @Override
    public void init(World _w) {

    }

    @Override
    public void restore(World _w) {

    }
}
