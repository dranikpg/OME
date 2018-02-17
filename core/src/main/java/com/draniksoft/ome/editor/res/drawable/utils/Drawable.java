package com.draniksoft.ome.editor.res.drawable.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.GroupResource;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.support.pipemsg.MsgNode;

public abstract class Drawable implements Resource<Drawable>, MsgNode {


    Drawable parent;

    public abstract void draw(Batch b, float x, float y, float w, float h);


    public Drawable self() {
	  return this;
    }


    @Override
    public void msg(byte msg, byte dir, byte[] data) {
	  byte _dir = simplifyDirection(dir);

	  if (_dir == MsgDirection.END || !_msg(msg, data)) return;

	  if (_dir == MsgDirection.UP) {
		if (parent != null) parent.msg(msg, dir, data);
	  } else {
		if (this instanceof GroupResource) {
		    GroupResource<Drawable> gr = (GroupResource) this;
		    for (Drawable dwb : gr.getChildren()) {
			  if (dwb != null) dwb.msg(msg, dir, data);
		    }
		}
	  }

    }

    private boolean _msg(byte msg, byte[] data) {
	  return true;
    }

    public byte simplifyDirection(byte d) {
	  return d;
	  // TODO (some time later)
    }


    @Override
    public Resource<Drawable> parent() {
	  return parent;
    }

    @Override
    public void parent(Resource<Drawable> p) {
	  this.parent = p.self();
    }
}
