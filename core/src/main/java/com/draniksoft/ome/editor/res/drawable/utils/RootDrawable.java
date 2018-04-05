package com.draniksoft.ome.editor.res.drawable.utils;

import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.editor.support.track.UsageTracker;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;

/*
      Top level drawable class.

 */

public class RootDrawable extends Drawable implements RootResource<Drawable>, UsageTracker {

    private static final String tag = "RootDrawable";

    public Drawable d;

    transient short uses = 0;


    @Override
    public void draw(FlexBatch b, int x, int y) {
	  if (d != null) d.draw(b, x, y);
    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {
	  if (d != null) d.draw(b, x, y, w, h);
    }

    @Override
    public boolean contains(Vector2 p) {
	  if (d == null) return false;
	  return d.contains(p);
    }

    @Override
    public void size(Vector2 v) {
	  if (d != null) d.size(v);
    }

    //

    @Override
    public Drawable getSub() {
	  return d;
    }

    @Override
    public void update(Resource<Drawable> r) {

	  if (d != null) {
		d.msg(MSG.USAGE_CHANGE, MsgDirection.DOWN, new short[]{0, (short) -uses});
		d.msg(MsgBaseCodes.DEINIT, MsgDirection.DOWN, null);
	  }

        d = r.self();

	  d.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, null);
	  if (uses != 0) d.msg(MSG.USAGE_CHANGE, MsgDirection.DOWN, new short[]{uses, uses});

    }

    @Override
    public UsageTracker usg() {
	  return this;
    }

    /*
        Usage tracking
     */

    @Deprecated
    public void updateUsage(short dlt) {
	  if (dlt == 0) return;
	  msg(MSG.USAGE_CHANGE, MsgDirection.DOWN, new short[]{dlt, 0});
    }


    @Override
    public short usages() {
	  return uses;
    }

    @Override
    public short usage(short delta) {
	  if (delta == 0) return
		    uses += delta;
	  msg(MSG.USAGE_CHANGE, MsgDirection.DOWN, new short[]{uses, delta});
	  return 0;
    }

    /*
    	Helper
     */


    /*
          Static helpers
     */

    /*
	  Build all tings for entity
     */


    @Override
    protected void _msgDown(short msg, byte sdir, Object data) {
	  if (d != null) d.msg(msg, sdir, data);
    }

}
