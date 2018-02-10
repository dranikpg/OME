package com.draniksoft.ome.editor.res.res_mgmnt_base.types;

import com.draniksoft.ome.editor.res.drawable.utils.RootDrawable;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.RootResource;


public enum ResTypes {

    DRAWABLE {
	  @Override
	  public RootResource createRoot() {
		return new RootDrawable();
	  }
    },


    COLOR;

    public RootResource createRoot() {
	  return null;
    }

}
