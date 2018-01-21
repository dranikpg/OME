package com.draniksoft.ome.editor.res_mgmnt.constructor;

import com.artemis.World;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.res_mgmnt.t.ResSubT;
import com.draniksoft.ome.editor.res_mgmnt.ui_br.NodeDeliverer;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;

public abstract class ResConstructor<TYPE> {

    GroupResConstructor<TYPE> parent;

    protected ResTreeNode<TYPE> node;

    protected boolean nodeRRQ = false;

    ResSubT tt = ResSubT.NULL;

    protected int T = -1;

    /*

     */

    public ResTreeNode<TYPE> getNode(NodeDeliverer<TYPE> del) {
	  if (node == null || nodeRRQ) newNode(del);
	  return node;
    }

    public void setNode(ResTreeNode<TYPE> node, NodeDeliverer<TYPE> del) {
	  this.node = node;
    }

    public abstract void newNode(NodeDeliverer<TYPE> del);


    // DD UTILS

    public void dragging() {

    }

    /*


     */

    public void setType(ResSubT nt) {
	  if (tt == nt) return;
	  this.tt = nt;
	  typeUpdate();
    }

    public ResSubT type() {
	  return tt;
    }

    /*
    
     */

    public GroupResConstructor<TYPE> getParent() {
	  return parent;
    }

    public void setParent(GroupResConstructor<TYPE> parent) {
	  this.parent = parent;
    }

    /*

     */

    public abstract TYPE getSnapshot();

    // return new copy
    public abstract TYPE build();

    /*
     	Called after array or property change, should update its snapshot;
     */

    public abstract void updateSources();

    public abstract void typeUpdate();



    /*
   	Child operations are performed automatically in groupConstructors
    
     */

    public abstract void parseFrom(JsonValue v, World _w);

    public abstract void serializeTo(JsonValue v);


}
