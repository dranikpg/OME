package com.draniksoft.ome.editor.res_mgmnt.ui_br;

import com.draniksoft.ome.editor.res_mgmnt.constructor.ResConstructor;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;

public interface NodeDeliverer<TYPE> {

    ResTreeNode<TYPE> node(ResConstructor<TYPE> ct);

}
