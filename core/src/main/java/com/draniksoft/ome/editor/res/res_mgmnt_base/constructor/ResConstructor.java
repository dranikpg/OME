package com.draniksoft.ome.editor.res.res_mgmnt_base.constructor;

import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubT;
import com.draniksoft.ome.editor.res.res_mgmnt_base.ui_br.NodeDeliverer;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgNode;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;
import com.draniksoft.ome.utils.struct.Pair;

public abstract class ResConstructor<TYPE> implements MsgNode {

    public static class MsgIDs {
	  public static final byte UPDATE_TYPE = 5;
    }

    // dabl pointer trap, use event flow for parent settin
    transient GroupResConstructor<TYPE> parent;
    transient ResTreeNode<TYPE> node;
    // Node reconstruction required
    transient boolean nodeRRQ = false;

    // type
    ResSubT tt = ResSubT.NULL;

    protected boolean LIVE_MODE = false;

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
	  updateType();
    }

    public ResSubT type() {
	  return tt;
    }

    /*
    		Type fetching for upper class abstraction
     */

    public abstract int group();

    /*
		Util methods for msg sytem

     */

    protected void shrinkData() {
	  node = null;
	  LIVE_MODE = false;
    }

    protected void extendData() {
	  LIVE_MODE = true;
    }

    public void updateType() {

    }

    public void updateSources() {

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
    	MSG
     */

    @Override
    public void msg(byte msg, byte dir, byte[] data) {
	  byte _dir = fetchDir(dir);
    }

    private byte fetchDir(byte dir) {
	  return dir;
    }

    private boolean defMsgHandle(byte msg, byte[] data) {
	  switch (msg) {
		case MsgBaseCodes.INIT:
		    extendData();
		    return true;
		case MsgBaseCodes.DEINIT:
		    shrinkData();
		    return true;
	  }
	  return false;

    }

    /*

     */

    // constant linked snapshot - no update calls !!
    public abstract TYPE getSnapshot();

    // return new copy
    public abstract TYPE build();

    public abstract ResConstructor<TYPE> copy();



}
