package com.draniksoft.ome.ui_addons.resource_ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.res_mgmnt.constructor.ResConstructor;
import com.kotcrab.vis.ui.widget.VisTree;

public class ResTreeNode<TYPE> extends VisTree.Node {

    public ResTreeNode(ResConstructor<TYPE> ct, Actor a) {
	  super(a);
	  setObject(ct);
    }

    public ResConstructor<TYPE> c() {
	  return (ResConstructor<TYPE>) getObject();
    }

}
