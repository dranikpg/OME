package com.draniksoft.ome.editor.res.color.utils;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.editor.support.track.UsageTracker;

public class RootColorProvider extends ColorProvider implements RootResource<ColorProvider> {

    public ColorProvider p;
    Color c;

    @Override
    public void getC(Color c) {
	  if (p != null) {
		p.getC(c);
	  }
    }

    public Color getC() {
	  getC(c);
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
    public void update(Resource<ColorProvider> r) {

    }

    @Override
    public UsageTracker usg() {
	  return null;
    }

}
