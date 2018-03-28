package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;

public class WeakLinkDrawable extends Drawable implements WeakLinkedResource<Drawable> {

    private static final String tag = "WeakLinkedDrawable";

    Drawable r;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (r != null) r.draw(b, x, y, w, h);
    }

    @Override
    public void msg(short msg, byte dir, Object data) {
	  Gdx.app.error(tag, "Part of message chain !! somsingswrong");
    }

    @Override
    public void set(Resource<Drawable> r2) {
	  r = r2.self();
    }

    @Override
    public Resource<Drawable> get() {
	  return r;
    }
}
