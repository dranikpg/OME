package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

/*
    Root of every tree

    Handles managment work

 */

public interface RootResource<TYPE> {

    TYPE self();

    TYPE getSub();

    /*
	  Update with new [FRESH!!] sources
        Calls USAGE_*** in most implementations
     */

    void update(Resource<TYPE> r);

    /*
        NOT AVAILABLE
     */

    void destruct();


}
