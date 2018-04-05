package com.draniksoft.ome.editor.res.drawable.constr;

import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.drawable.simple.WeakLinkDrawable;
import com.draniksoft.ome.editor.res.impl.constructor.LeafConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.types.ResTypes;

public class DrawableLeafContructor extends LeafConstructor<Drawable> {

    transient WeakLinkDrawable sp;

    public DrawableLeafContructor() {
	  T = ResTypes.DRAWABLE;
	  sp = new WeakLinkDrawable();
    }

    @Override
    public WeakLinkedResource<Drawable> getSnapshotLink() {
	  return sp;
    }
}
