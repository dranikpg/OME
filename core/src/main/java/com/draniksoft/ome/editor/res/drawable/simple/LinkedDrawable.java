package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.LinkedResource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;

public class LinkedDrawable extends Drawable implements LinkedResource<Drawable> {

    public int id = -1;
    public Drawable link;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (link != null) link.draw(b, x, y, w, h);
    }


    @Override
    public int id() {
	  return id;
    }

    @Override
    public Drawable source() {
	  return link;
    }

    @Override
    public void ifor(RootResource<Drawable> rt, int id) {
	  link = rt.self();
	  this.id = id;
    }

    @Override
    public void msg(short msg, byte dir, Object data) {

    }
}
