package com.draniksoft.ome.editor.res.drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.support.pipemsg.MsgNode;

public abstract class Drawable implements Resource<Drawable>, MsgNode {

    private static final String tag = "Drawable";

    Drawable parent;


    /*
    	Main iface
     */

    /* Basic draw */
    /* x,y - center */
    public abstract void draw(FlexBatch b, int x, int y);

    /* Draw in specific rect borders without scale */
    /* x,y - center */
    /* w,h - size */
    public abstract void draw(FlexBatch b, int x, int y, int w, int h);

    /* Input contains */
    public abstract boolean contains(Vector2 p);

    /* Return max bounds size - max HALFwidth and HALFheight, like most distant point from center*/
    public abstract void size(Vector2 v);

    /*
    	Update colors and depencies
     */

    public void update() {
	  _update();
    }

    protected abstract void _update();

    /*
    	Resources
     */

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

    /*

     */


    /* function extension step */

    protected void _init() {

    }

    protected void _deinit() {

    }

    protected void _updateUsage(short sum, short delta) {

    }

    /*

     */


    @Override
    public void msg(short msg, byte dir, Object data) {

	  Gdx.app.debug(tag, "MSG at " + getClass().getSimpleName() + " :: " + msg);

	  if (_handleCustomMsg(msg, dir, data))
		return;

	  if (_handleInits(msg, dir, data))
		return;

	  if (_handleUsageCycle(msg, dir, data))
		return;

    }

    /*
    		Base msg handler
     */


    protected boolean _handleInits(short msg, byte dir, Object data) {
	  switch (msg) {
		case MsgBaseCodes.INIT:
		    _init();
		    forwardmsg(msg, dir, data);
		    return true;
		case MsgBaseCodes.DEINIT:
		    _deinit();
		    forwardmsg(msg, dir, data);
		    return true;
	  }
	  return false;
    }

    protected boolean _handleUsageCycle(short msg, byte dir, Object data) {
	  switch (msg) {
		case MSG.USAGE_CHANGE:
		    short[] data2 = (short[]) data;
		    _updateUsage(data2[0], data2[1]);
		    forwardmsg(msg, dir, data);
		    return true;
	  }
	  return false;
    }

    protected boolean _handleCustomMsg(short msg, byte dir, Object data) {
	  return false;
    }

    /*

     */


    protected void forwardmsg(short msg, byte dir, Object data) {
	  if (dir == MsgDirection.UP) _msgUp(msg, dir, data);
	  else if (dir == MsgDirection.DOWN) _msgDown(msg, dir, data);
    }

    /*
    	Direction handlers
     */

    protected void _msgUp(short msg, byte sdir, Object data) {
	  parent.msg(msg, sdir, data);
    }

    protected void _msgDown(short msg, byte sdir, Object data) {
	  // only existance overwritten
    }


    /*
    	Static helper
     */

    public static Drawable buildForEty(ResConstructor<Drawable> ctr) {
	  Drawable d = ctr.build(MainBase.engine).self();
	  d.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, null);
	  d.msg(MSG.USAGE_CHANGE, MsgDirection.DOWN, new short[]{1, 1});
	  return d;
    }

    public static void deleteForEty(Drawable d) {
	  d.msg(MSG.USAGE_CHANGE, MsgDirection.DOWN, new short[]{0, -1});
	  d.msg(MsgBaseCodes.DEINIT, MsgDirection.DOWN, null);
    }



}
