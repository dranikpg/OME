package com.draniksoft.ome.editor.res.drawable.group;

import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.GroupResource;

public abstract class GroupDrawable extends Drawable implements GroupResource<Drawable> {

    @Override
    protected void _msgDown(byte msg, byte dir, short[] data) {
	  super._msgDown(msg, dir, data);
	  for (Drawable d : getChildren()) {
		d.msg(msg, dir, data);
	  }
    }
}
