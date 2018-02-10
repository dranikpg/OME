package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

import com.artemis.World;

public interface Resource<TYPE> {

    TYPE self();

    TYPE copy();

    void init(World _w);

    void restore(World _w);

}
