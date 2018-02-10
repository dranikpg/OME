package com.draniksoft.ome.editor.res.color.utils;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.RootResource;

public class RootColorProvider extends ColorProvider implements RootResource<ColorProvider> {

    public ColorProvider p;
    Color c;


    @Override
    public void get(Color c) {
	  if (p != null) {
		p.get(c);
	  }
    }

    public Color get() {
	  get(c);
	  return c;
    }

    @Override
    public ColorProvider self() {
	  return this;
    }

    @Override
    public ColorProvider getSub() {
	  return null;
    }

    @Override
    public void set(Resource<ColorProvider> r) {

    }

    @Override
    public ColorProvider copy() {
	  return null;
    }


}
