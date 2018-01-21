package com.draniksoft.ome.editor.base_gfx.drawable.utils;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res_mgmnt.res_ifaces.Resource;

public abstract class Drawable implements Resource<Drawable> {


    public abstract void draw(Batch b, float x, float y, float w, float h);

    @Override
    public void init(World _w) {

    }

    @Override
    public void restore(World _w) {

    }

}
