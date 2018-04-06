package com.draniksoft.ome.editor.res.drawable.typedata;

import com.artemis.World;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.drawable.simple.TextureQuadDwb;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;
import com.draniksoft.ome.utils.FUtills;

public class TextureDwbTD implements ResDataHandler<Drawable>, CamScaleable {


    float lB = 1f, uB = 1;
    public int w = 40, h = 40;
    String uri;


    @Override
    public void init() {

    }

    @Override
    public void initL(WeakLinkedResource<Drawable> link) {

    }

    @Override
    public void deinitL() {
    }

    public void setUri(String uri) {
	  this.uri = uri;
    }


    @Override
    public Resource<Drawable> build(World _w) {
	  return new TextureQuadDwb(FUtills.getRAC(uri), w, h, lB, uB);
    }

    @Override
    public ResDataHandler<Drawable> copy() {
        return null;
    }

    @Override
    public String toString() {
	  return " SD_TD(" + uri + ")";
    }

    @Override
    public void setCS(float lb, float ub) {
	  this.lB = lb;
	  this.uB = ub;
    }

    @Override
    public float getLowerB() {
	  return lB;
    }

    @Override
    public float getUpperB() {
	  return uB;
    }
}
