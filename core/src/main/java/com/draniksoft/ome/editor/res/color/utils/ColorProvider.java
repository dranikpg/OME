package com.draniksoft.ome.editor.res.color.utils;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource;

public abstract class ColorProvider implements Resource<ColorProvider> {

    public abstract void get(Color c);

    ColorProvider p;

    @Override
    public ColorProvider self() {
	  return this;
    }


    @Override
    public void parent(Resource<ColorProvider> p) {
	  this.p = p.self();
    }

    @Override
    public Resource<ColorProvider> parent() {
	  return p;
    }
}
