package com.draniksoft.ome.editor.res.color.utils;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.editor.support.track.UsageTracker;

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
    public void update(Resource<ColorProvider> r) {

    }

    @Override
    public UsageTracker usg() {
	  return null;
    }

}
