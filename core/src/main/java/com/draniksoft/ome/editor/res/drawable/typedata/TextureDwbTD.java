package com.draniksoft.ome.editor.res.drawable.typedata;

import com.artemis.World;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;

public class TextureDwbTD implements ResDataHandler<Drawable>, CamScaleable {

    int s1, s2;

    String uri;

    // transient SimpleDrawable dwb;

    @Override
    public void init() {

    }

    @Override
    public void initL(WeakLinkedResource<Drawable> link) {
	 /* dwb = new SimpleDrawable();
	  link.set(dwb);*/
    }

    @Override
    public void deinitL() {
        // dwb = null;
    }

    public void setUri(String uri) {
	  this.uri = uri;
	  // if(dwb != null) updatePreviewDwb(uri);
    }

    public void updatePreviewDwb(String uri) {
	  //    dwb.r = FUtills.getRAC(uri);
    }


    @Override
    public Resource<Drawable> build(World w) {
	  //   return new SimpleDrawable(FUtills.getRAC(uri));
	  return null;
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
    public void set(float lb, float ub) {

    }

    @Override
    public float getLowerB() {
        return 0;
    }

    @Override
    public float getUpperB() {
        return 0;
    }
}
