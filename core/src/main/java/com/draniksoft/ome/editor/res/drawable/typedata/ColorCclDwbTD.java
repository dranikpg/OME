package com.draniksoft.ome.editor.res.drawable.typedata;

import com.artemis.World;
import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.drawable.simple.ColorCircleDwb;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;

public class ColorCclDwbTD implements ResDataHandler<Drawable>, CamScaleable {

    public boolean outL = false;
    public int r = 20;

    float mns = 1, mxs = 1;

    public ResConstructor<ColorProvider> pv;

    transient ColorCircleDwb prw;

    @Override
    public void init() {

    }

    @Override
    public void initL(WeakLinkedResource<Drawable> link) {
	  prw = new ColorCircleDwb(r, mns, mxs, null);
	  link.set(prw);
    }

    @Override
    public void deinitL() {
	  prw = null;
    }

    @Override
    public Resource<Drawable> build(World w) {
	  ColorProvider bp = pv.build(w).self();
	  bp.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, null);
	  return new ColorCircleDwb(r, mns, mxs, bp);
    }

    @Override
    public void setCS(float lb, float ub) {
	  this.mns = lb;
	  this.mxs = ub;
    }

    @Override
    public float getLowerB() {
	  return mns;
    }

    @Override
    public float getUpperB() {
	  return mxs;
    }

    @Override
    public ResDataHandler<Drawable> copy() {
	  ColorCclDwbTD d = new ColorCclDwbTD();
	  d.pv = pv.copy();
	  d.mns = mns;
	  d.mxs = mxs;
	  d.outL = outL;
	  d.r = r;
	  return d;
    }

}
