package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

public interface Resource<TYPE> {

    TYPE self();

    TYPE copy();

    Resource<TYPE> parent();

    void parent(Resource<TYPE> p);


}
