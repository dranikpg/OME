package com.draniksoft.ome.editor.res.drawable.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.main_menu.MainBase;
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
    public void draw(FlexBatch b, int x, int y) {

    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {

    }

    @Override
    public boolean contains(Vector2 p) {
	  return false;
    }

    @Override
    public void size(Vector2 v) {

    }

    //

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
    protected boolean _handleUsageCycle(short msg, byte dir, Object data) {

	  if (!super._handleUsageCycle(msg, dir, data)) return false;

	  short[
		    ] data2 = (short[]) data;

	  Gdx.app.debug(tag, "Usage update " + data2[0]);
	  uses += data2[0];
	  data2[1] = uses;
	  return true;
    }


    /*
          Static helpers
     */

    /*
	  Build all tings for entity
     */
    public static RootDrawable forEntityLoad(ResConstructor<Drawable> cc) {
	  RootDrawable rdwb = new RootDrawable();
	  Drawable cdwb = cc.build(MainBase.engine).self();
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
    protected void _msgDown(short msg, byte sdir, Object data) {
	  if (d != null) d.msg(msg, sdir, data);
    }


    @Override
    public void msg(short msg, byte dir, Object data) {

    }
}
