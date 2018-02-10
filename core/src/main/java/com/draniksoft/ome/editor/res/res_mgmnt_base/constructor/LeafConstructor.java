package com.draniksoft.ome.editor.res.res_mgmnt_base.constructor;

import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubGroups;
import com.draniksoft.ome.editor.res.res_mgmnt_base.ui_br.NodeDeliverer;

public abstract class LeafConstructor<TYPE> extends ResConstructor<TYPE> {

    @Override
    public void newNode(NodeDeliverer<TYPE> del) {
	  nodeRRQ = false;
	  node = del.node(this);
    }

    @Override
    public int group() {
	  return ResSubGroups.SIMPLE;
    }
}
