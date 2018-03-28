package com.draniksoft.ome.editor.res.impl.res_ifaces;

public interface WeakLinkedResource<TYPE> {

    void set(Resource<TYPE> r);

    Resource<TYPE> get();

    Resource<TYPE> self();

}
