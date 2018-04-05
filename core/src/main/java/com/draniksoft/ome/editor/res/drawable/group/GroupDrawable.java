package com.draniksoft.ome.editor.res.drawable.group;

import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.GroupResource;

public abstract class GroupDrawable extends Drawable implements GroupResource<Drawable> {

    @Override
    protected void _msgDown(short msg, byte dir, Object data) {
	  super._msgDown(msg, dir, data);
	  for (Drawable d : getChildren()) {
		d.msg(msg, dir, data);
	  }
    }
}
