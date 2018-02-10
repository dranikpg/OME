package com.draniksoft.ome.editor.res.pv_res_wrapper;

import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.RootResource;

public abstract class PVResWrapper<T> {

    public enum MODIFIC {
	  RENAME, DELETE, CHANGE
    }

    public RootResource<T> res;
    public ResConstructor<T> ctr;

    public int id;

    public abstract boolean allowed(MODIFIC m);

    public abstract void setName(String name);

    public abstract String getName();

    public abstract boolean isBase();

}
