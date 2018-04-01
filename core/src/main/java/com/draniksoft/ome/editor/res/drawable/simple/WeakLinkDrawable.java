package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;


public class WeakLinkDrawable extends Drawable implements WeakLinkedResource<Drawable> {

    private static final String tag = "WeakLinkedDrawable";

    Drawable r;

    @Override
    public void draw(FlexBatch b, int x, int y) {
	  if (r != null)
		r.draw(b, x, y);
    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {
	  if (r != null)
		r.draw(b, x, y, w, h);
    }

    @Override
    public boolean contains(Vector2 p) {
	  if (r == null) return false;
	  return r.contains(p);
    }

    @Override
    public void size(Vector2 v) {
	  if (r != null)
		r.size(v);
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
