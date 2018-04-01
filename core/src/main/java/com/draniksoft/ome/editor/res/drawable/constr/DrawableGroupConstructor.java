package com.draniksoft.ome.editor.res.drawable.constr;

import com.draniksoft.ome.editor.res.drawable.simple.WeakLinkDrawable;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.constructor.GroupResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.types.ResTypes;

public class DrawableGroupConstructor extends GroupResConstructor<Drawable> {

    transient WeakLinkDrawable sp;

    public DrawableGroupConstructor() {
	  T = ResTypes.DRAWABLE;
	  sp = new WeakLinkDrawable();
    }

    @Override
    public WeakLinkedResource<Drawable> getSnapshotLink() {
	  return sp;
    }

}
