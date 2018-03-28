package com.draniksoft.ome.editor.res.drawable.typedata;

import com.draniksoft.ome.editor.res.drawable.simple.SimpleDrawable;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;
import com.draniksoft.ome.editor.res.impl.types.ResSubT;

public class SimpleDwbTD implements ResDataHandler<Drawable> {

    String uri;

    SimpleDrawable dwb;

    @Override
    public void initL(WeakLinkedResource<Drawable> link) {
	  dwb = new SimpleDrawable();
    }

    @Override
    public void deinit() {
	  dwb = null;
    }

    @Override
    public ResSubT type() {
	  return null;
    }

    @Override
    public Resource<Drawable> build() {
	  return null;
    }
}
