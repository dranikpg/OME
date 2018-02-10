package com.draniksoft.ome.editor.res.res_mgmnt_base.ui_br;

import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;

public interface NodeDeliverer<TYPE> {

    ResTreeNode<TYPE> node(ResConstructor<TYPE> ct);

}
