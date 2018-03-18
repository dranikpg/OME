package com.draniksoft.ome.editor.res.drawable.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.RootResource;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.utils.FUtills;

/*
      Top level drawable class.

 */

public class RootDrawable extends Drawable implements RootResource<Drawable> {

    private static final String tag = "RootDrawable";

    public Drawable d;

    transient short uses = 0;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (d != null) d.draw(b, x, y, w, h);
    }

    @Override
    public Drawable copy() {
	  return null;
    }


    @Override
    public Drawable getSub() {
	  return d;
    }

    @Override
    public void update(Resource<Drawable> r) {
        d = r.self();
    }

    @Override
    public void destruct() {

    }

    /*
	  Init
     */

    public void init() {
	  msg(MsgBaseCodes.INIT, MsgDirection.DOWN, FUtills.NULL_ARRAY);
    }

    /*
        Usage tracking
     */

    public void updateUsage(short dlt) {
	  if (dlt == 0) return;
	  msg(MSG.USAGE_CHANGE, MsgDirection.DOWN, new short[]{dlt, 0});
    }

    @Override
    protected byte _handleUsageCycle(byte msg, byte dir, short[] data) {
        byte ans = super._handleUsageCycle(msg, dir, data);
        if (ans == MsgDirection.UNDEFINED) return ans;
        Gdx.app.debug(tag, "Usage update " + data[0]);
	  uses += data[0];
	  data[1] = uses;
	  return ans;
    }


    /*
          Static helpers
     */

    /*
	  Build all tings for entity
     */
    public static RootDrawable forEntityLoad(ResConstructor<Drawable> cc) {
	  RootDrawable rdwb = new RootDrawable();
	  Drawable cdwb = cc.build();
	  rdwb.update(cdwb);
	  rdwb.init();
	  rdwb.updateUsage((short) 1);
	  return rdwb;
    }

    // wrap entity drawable
    public static RootDrawable wrap(Resource<Drawable> d) {
        RootDrawable r = new RootDrawable();
        r.update(r);
        return r;
    }


    @Override
    protected void _msgDown(byte msg, byte sdir, short[] data) {
	  if (d != null) d.msg(msg, sdir, data);
    }
}
