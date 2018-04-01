package com.draniksoft.ome.editor.res.drawable.typedata;

import com.artemis.World;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;
import com.draniksoft.ome.editor.res.impl.types.ResSubT;

public class SimpleDwbTD implements ResDataHandler<Drawable> {

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
    public void deinit() {
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
    public ResSubT type() {
	  return ResSubT.SIMPLE;
    }

    @Override
    public Resource<Drawable> build(World w) {
	  //   return new SimpleDrawable(FUtills.getRAC(uri));
	  return null;
    }

    @Override
    public String toString() {
	  return " SD_TD(" + uri + ")";
    }
}
