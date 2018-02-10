package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

public interface RootResource<TYPE> {

    TYPE self();

    TYPE getSub();

    void set(Resource<TYPE> r);

}
