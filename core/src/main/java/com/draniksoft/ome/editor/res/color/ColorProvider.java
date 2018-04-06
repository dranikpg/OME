package com.draniksoft.ome.editor.res.color;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.support.pipemsg.MsgNode;

public abstract class ColorProvider implements Resource<ColorProvider>, MsgNode {

    /*

     */

    public abstract void getC(Color c);

    // get immediately
    public abstract Color getC();

    /*
    	MSG
     */

    @Override
    public void msg(short msg, byte dir, Object data) {
	  if (handleInits(msg, dir, data)) return;
    }

    protected boolean handleInits(short msg, byte dir, Object data) {
	  if (msg == MsgBaseCodes.INIT) {
		_init();
		forward(msg, dir, data);
		return true;
	  } else if (msg == MsgBaseCodes.DEINIT) {
		_deinit();
		forward(msg, dir, data);
		return true;
	  }

	  return false;
    }

    protected void forward(short msg, byte dir, Object data) {
	  if (dir == MsgDirection.DOWN) {
		_sendDown(msg, dir, data);
	  } else {
		_sendUp(msg, dir, data);
	  }
    }

    protected void _sendDown(short msg, byte dir, Object data) {

    }

    protected void _sendUp(short msg, byte dir, Object data) {
	  if (p != null) p.msg(msg, dir, data);
    }

    /*
    	MSG call funtions
     */

    protected void _init() {

    }

    protected void _deinit() {

    }
    /*

     */

    ColorProvider p;

    @Override
    public ColorProvider self() {
	  return this;
    }

    @Override
    public void parent(Resource<ColorProvider> p) {
	  this.p = p.self();
    }

    @Override
    public Resource<ColorProvider> parent() {
	  return p;
    }
}
