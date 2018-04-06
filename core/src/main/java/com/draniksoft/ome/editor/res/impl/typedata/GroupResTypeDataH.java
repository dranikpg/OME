package com.draniksoft.ome.editor.res.impl.typedata;

import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;

public interface GroupResTypeDataH<TYPE> {

    void initL(Array<TYPE> ar);

    void update(Array<TYPE> ar);

    Resource<TYPE> build(Array<TYPE> ar);

    ResDataHandler<TYPE> asHanlder();

}
