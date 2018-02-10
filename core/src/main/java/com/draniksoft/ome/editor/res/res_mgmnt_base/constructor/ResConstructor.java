package com.draniksoft.ome.editor.res.res_mgmnt_base.constructor;

import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubT;
import com.draniksoft.ome.editor.res.res_mgmnt_base.ui_br.NodeDeliverer;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;
import com.draniksoft.ome.utils.struct.Pair;

public abstract class ResConstructor<TYPE> {

    GroupResConstructor<TYPE> parent;

    //
    ResTreeNode<TYPE> node;

    // Node reconstruction required
    boolean nodeRRQ = false;

    // type
    ResSubT tt = ResSubT.NULL;

    protected int T = -1;

    /*
		Node utils
     */

    public ResTreeNode<TYPE> getNode(NodeDeliverer<TYPE> del) {
	  if (node == null || nodeRRQ) newNode(del);
	  return node;
    }

    public void setNode(ResTreeNode<TYPE> node, NodeDeliverer<TYPE> del) {
	  this.node = node;
    }

    public abstract void newNode(NodeDeliverer<TYPE> del);

    /*
	Type managment
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
    		Type fetching for upper class abstraction
     */

    public abstract int group();

    /*
		Life cycle
     */

    public void shrinkData() {
	  node = null;

    }

    public void extendData() {

    }

    /*
	Tree management
     */
    public Pair<ResConstructor<TYPE>, GroupResConstructor<TYPE>> findParentPrefix(boolean incl) {

	  if (incl && this instanceof GroupResConstructor && ((GroupResConstructor) this).can(false))
		return Pair.P(this, (GroupResConstructor<TYPE>) this);

	  ResConstructor<TYPE> t = this;

	  while (t != null) {
		if (t.getParent() != null && t.getParent().can(false)) return Pair.P(t, t.getParent());
		else t = t.getParent();
	  }

	  return null;

    }

    public GroupResConstructor<TYPE> getParent() {
	  return parent;
    }

    void setParent(GroupResConstructor<TYPE> parent) {
	  this.parent = parent;
    }

    /*

     */

    // constant linked snapshot - no update calls !!
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



}
