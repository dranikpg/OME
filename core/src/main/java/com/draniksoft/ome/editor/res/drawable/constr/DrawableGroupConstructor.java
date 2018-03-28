package com.draniksoft.ome.editor.res.drawable.constr;

import com.draniksoft.ome.editor.res.drawable.simple.WeakLinkDrawable;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.constructor.GroupResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;

public class DrawableGroupConstructor extends GroupResConstructor<Drawable> {

    private static String tag = "DrawableGroupConstructor";

    transient WeakLinkDrawable sp;

    public DrawableGroupConstructor() {
	  sp = new WeakLinkDrawable();
    }

    @Override
    public WeakLinkedResource<Drawable> getSnapshotLink() {
	  return sp;
    }

    @Override
    public Drawable getSnapshot() {
	  return sp;
    }

}
