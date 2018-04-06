package com.draniksoft.ome.editor.res.color.simple;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;

public class WeakLinkColor extends ColorProvider implements WeakLinkedResource<ColorProvider> {

    ColorProvider r;

    @Override
    public void getC(Color c) {
	  if (r != null) r.getC(c);
    }


    @Override
    public Color getC() {
	  if (r != null) return r.getC();
	  return null;
    }

    @Override
    public void set(Resource<ColorProvider> r) {
	  this.r = r.self();
    }

    @Override
    public Resource<ColorProvider> get() {
	  return r;
    }

}
