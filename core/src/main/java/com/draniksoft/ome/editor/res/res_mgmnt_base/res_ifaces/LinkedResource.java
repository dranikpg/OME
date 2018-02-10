package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

public interface LinkedResource<TYPE> {

    int id();

    TYPE source();

    void ifor(RootResource<TYPE> rt, int id);

}
