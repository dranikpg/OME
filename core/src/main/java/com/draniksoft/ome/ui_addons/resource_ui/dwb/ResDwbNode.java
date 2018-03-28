package com.draniksoft.ome.ui_addons.resource_ui.dwb;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;

public class ResDwbNode extends ResTreeNode<Drawable> {

    public ResDwbNode(ResConstructor<Drawable> ct, Actor a) {
	  super(ct, a);

	  setIcon(GdxCompatibleDrawable.from(ct.getSnapshot()));
	  getIcon().setMinHeight(50);
	  getIcon().setMinWidth(50);

    }


}
