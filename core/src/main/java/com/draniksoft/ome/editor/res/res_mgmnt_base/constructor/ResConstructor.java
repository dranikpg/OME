package com.draniksoft.ome.editor.res.res_mgmnt_base.constructor;

import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubT;
import com.draniksoft.ome.editor.res.res_mgmnt_base.ui_br.NodeDeliverer;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.support.pipemsg.MsgNode;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.struct.Pair;

public abstract class ResConstructor<TYPE> implements MsgNode {

    /*
    	100 - 110
     */
    public static class MSG {

	  public static final byte LIVE_MODE = 102;
	  public static final byte STORAGE_MODE = 103;

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
	  if (LIVE_MODE) updateType();
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

    protected void updateType() {

    }

    protected void updateSources() {

    }

    protected void init() {

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
    public void msg(byte msg, byte dir, short[] data) {
	  byte _dir = fetchDir(dir);
	  defMsgHandle(msg, data);

	  if (dir == MsgDirection.END) return;
	  else if (dir == MsgDirection.DOWN) {

		if (this instanceof GroupResConstructor) {
		    Array<ResConstructor> ar = ((GroupResConstructor) this).getAr();
		    for (ResConstructor ct : ar) {
			  ct.msg(msg, _dir, data);
		    }
		}
	  }

    }

    private byte fetchDir(byte dir) {
	  return dir;
    }

    private boolean defMsgHandle(byte msg, short[] data) {
	  switch (msg) {
		case MSG.LIVE_MODE:
		    extendData();
		    return true;
		case MSG.STORAGE_MODE:
		    shrinkData();
		    return true;
		case MsgBaseCodes.INIT:
		    init();
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

    public static class MsgHelper {


	  public static void liveMode(ResConstructor c) {
		c.msg(MSG.LIVE_MODE, MsgDirection.DOWN, FUtills.NULL_ARRAY);
	  }

	  public static void storageMode(ResConstructor c) {
		c.msg(MSG.LIVE_MODE, MsgDirection.DOWN, FUtills.NULL_ARRAY);
	  }

    }



}
