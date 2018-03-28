package com.draniksoft.ome.editor.res.drawable.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.support.pipemsg.MsgNode;

public abstract class Drawable implements Resource<Drawable>, MsgNode {

    private static final String tag = "Drawable";


    Drawable parent;

    public abstract void draw(Batch b, float x, float y, float w, float h);


    public Drawable self() {
	  return this;
    }


    @Override
    public Resource<Drawable> parent() {
	  return parent;
    }

    @Override
    public void parent(Resource<Drawable> p) {
	  this.parent = p.self();
    }


    @Override
    public void msg(byte msg, byte dir, short[] data) {

	  Gdx.app.debug(tag, "MSG at " + getClass().getSimpleName() + " :: " + msg);

	  dir = updateDirectionCounter(dir);

	  byte ans;

	  ans = _handleCustomMsg(msg, dir, data);
	  if (ans != MsgDirection.UNDEFINED) {
		forwardmsg(msg, ans, data);
		return;
	  }

	  ans = _handleInits(msg, dir, data);
	  if (ans != MsgDirection.UNDEFINED) {
		forwardmsg(msg, ans, data);
		return;
	  }

	  ans = _handleUsageCycle(msg, dir, data);
	  if (ans != MsgDirection.UNDEFINED) {
		forwardmsg(msg, ans, data);
		return;
	  }

    }

    /*
    		Base msg handler
     */


    protected byte _handleInits(byte msg, byte dir, short[] data) {
	  switch (msg) {
		case MsgBaseCodes.INIT:
		    _init();
		    return dir;
		case MsgBaseCodes.DEINIT:
		    _deinit();
		    return dir;
	  }
	  return MsgDirection.UNDEFINED;
    }

    protected byte _handleUsageCycle(byte msg, byte dir, short[] data) {

	  switch (msg) {
		case MSG.USAGE_CHANGE:
		    _updateUsage(data[1], data[0]);
		    return dir;
	  }
	  return MsgDirection.UNDEFINED;
    }

    protected byte _handleCustomMsg(byte msg, byte dir, short[] data) {
	  return MsgDirection.UNDEFINED;
    }

    /*

     */

    protected byte updateDirectionCounter(byte d) {
	  return d;
    }

    protected void forwardmsg(byte msg, byte dir, short[] data) {
	  if (dir == MsgDirection.UP) _msgUp(msg, dir, data);
	  else if (dir == MsgDirection.DOWN) _msgDown(msg, dir, data);
    }

    /*
    	Direction handlers
     */

    protected void _msgUp(byte msg, byte sdir, short[] data) {
	  parent.msg(msg, sdir, data);
    }

    protected void _msgDown(byte msg, byte sdir, short[] data) {
	  // only existance overwritten
    }



    /* function extension step */

    protected void _init() {

    }

    protected void _deinit() {

    }

    protected void _updateUsage(short sum, short delta) {

    }


}
