package com.draniksoft.ome.editor.res_mgmnt.constructor;

import com.draniksoft.ome.editor.res_mgmnt.ui_br.NodeDeliverer;

public abstract class LeafConstructor<TYPE> extends ResConstructor<TYPE> {

    @Override
    public void newNode(NodeDeliverer<TYPE> del) {
	  nodeRRQ = false;
	  node = del.node(this);
    }
}
