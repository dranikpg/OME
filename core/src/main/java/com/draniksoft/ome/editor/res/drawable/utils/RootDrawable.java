package com.draniksoft.ome.editor.res.drawable.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.RootResource;
import com.draniksoft.ome.support.pipemsg.MsgDirection;

/*
      Top level drawable class.

 */

public class RootDrawable extends Drawable implements RootResource<Drawable> {

    private static final String tag = "RootDrawable";

    public Drawable d;

    short uses = 0;

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

     */

    @Override
    protected byte _handleUsageCycle(byte msg, byte dir, short[] data) {
        byte ans = super._handleUsageCycle(msg, dir, data);
        if (ans == MsgDirection.UNDEFINED) return ans;
        Gdx.app.debug(tag, "Usage update " + data[0]);
        uses += data[1];
        data[0] = uses;
        return ans;
    }


    /*
          Static helpers
     */

    // wrap entity drawable
    public static RootDrawable wrap(Resource<Drawable> d) {
        RootDrawable r = new RootDrawable();
        r.update(r);
        return r;
    }



}
