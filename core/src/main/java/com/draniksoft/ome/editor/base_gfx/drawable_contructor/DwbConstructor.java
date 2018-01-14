package com.draniksoft.ome.editor.base_gfx.drawable_contructor;

import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;

public abstract class DwbConstructor {

    GroupConstructor parent;
    EditDwbView.DwbNode node;


    public void setParent(GroupConstructor parent) {
	  this.parent = parent;
    }

    public GroupConstructor getParent() {
	  return parent;
    }

    public EditDwbView.DwbNode getNode() {
	  if (node == null) newNode();
	  return node;
    }

    protected abstract void newNode();

    public void setNode(EditDwbView.DwbNode node) {
	  this.node = node;
    }

    public abstract GdxCompatibleDrawable getGdxSnapshot();

    public abstract Drawable getSnapshot();


}
