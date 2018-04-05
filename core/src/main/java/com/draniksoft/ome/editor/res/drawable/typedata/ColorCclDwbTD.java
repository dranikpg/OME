package com.draniksoft.ome.editor.res.drawable.typedata;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.drawable.simple.ColorCircleDwb;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;

public class ColorCclDwbTD implements ResDataHandler<Drawable>, CamScaleable {

    public boolean outL = false;
    public int r = 20;
    public Color c = Color.CYAN;

    float mns = 1, mxs = 1;

    transient ColorCircleDwb prw;

    @Override
    public void init() {

    }

    @Override
    public void initL(WeakLinkedResource<Drawable> link) {

	  prw = new ColorCircleDwb(r, mns, mxs, c);
	  link.set(prw);

    }

    @Override
    public void deinitL() {

	  prw = null;

    }


    @Override
    public Resource<Drawable> build(World w) {
	  return new ColorCircleDwb(r, mns, mxs, c);
    }


    @Override
    public void set(float lb, float ub) {
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
	  d.c = new Color(c);
	  d.mns = mns;
	  d.mxs = mxs;
	  d.outL = outL;
	  d.r = r;
	  return d;
    }
}
