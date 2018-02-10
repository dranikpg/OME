package com.draniksoft.ome.editor.res.color.utils;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource;

public abstract class ColorProvider implements Resource<ColorProvider> {

    public abstract void get(Color c);

    @Override
    public ColorProvider self() {
	  return this;
    }

    @Override
    public void init(World _w) {

    }

    @Override
    public void restore(World _w) {

    }
}
