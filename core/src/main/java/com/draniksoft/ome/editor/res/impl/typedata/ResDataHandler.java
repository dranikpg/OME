package com.draniksoft.ome.editor.res.impl.typedata;

import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.types.ResSubT;

public interface ResDataHandler<TYPE> {

    void initL(WeakLinkedResource<TYPE> link);

    void deinit();

    ResSubT type();

    // UNUSED WHEN GROUP
    Resource<TYPE> build();

}
