package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

import com.badlogic.gdx.utils.Array;

public interface GroupResource<TYPE> {

    /*
    	Methods for constructors to copy data without type fetching
     */


    void update(Array<TYPE> ar);

}
