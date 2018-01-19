package com.draniksoft.ome.editor.base_gfx.drawable_contructor;

import com.badlogic.gdx.utils.JsonValue;
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

    public abstract void updateSources();

    /*
    	Should probably update it's sources as double links will occur
     */
    public abstract Drawable construct();

    // children are auto managed
    public abstract void putData(JsonValue v);

    // Directly followed by updateSources call in parse stream
    public abstract void fetchData(JsonValue v);

    /*
    	Garbage part
     */

    public abstract void destruct();


}
