package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

import com.badlogic.gdx.utils.Array;

public interface GroupResource<TYPE> {

    /*

    	getChildren -> replace with iterator in quite some time to avoid mem copy

     */

    TYPE[] getChildren();

    void update(Array<TYPE> ar);

}
