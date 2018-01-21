package com.draniksoft.ome.editor.res_mgmnt.res_ifaces;

import com.artemis.World;

public interface Resource<TYPE> {

    TYPE copy();

    void init(World _w);

    void restore(World _w);

}
