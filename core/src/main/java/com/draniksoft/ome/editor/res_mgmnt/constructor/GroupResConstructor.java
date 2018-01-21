package com.draniksoft.ome.editor.res_mgmnt.constructor;

import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res_mgmnt.ui_br.NodeDeliverer;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;
import org.jetbrains.annotations.Nullable;

public abstract class GroupResConstructor<TYPE> extends ResConstructor<TYPE> {

    Array<ResConstructor<TYPE>> ar;

    boolean order = false;

    public GroupResConstructor() {
	  ar = new Array<ResConstructor<TYPE>>();
    }

    public void add(ResConstructor<TYPE> ct, @Nullable NodeDeliverer<TYPE> del) {

	  ar.add(ct);

	  if (node != null && del != null) getNode(del).add(ct.getNode(del));
	  else nodeRRQ = true;

	  ct.setParent(this);

	  if (order) reorder(del);


	  updateSources();
    }

    public void remove(ResConstructor<TYPE> ct, @Nullable NodeDeliverer<TYPE> del) {
	  ar.removeValue(ct, true);

	  if (ct.node != null && del != null) ct.getNode(del).remove();
	  else nodeRRQ = true;

	  ct.setParent(null);

	  if (order) reorder(del);


	  updateSources();
    }

    public void add(ResConstructor<TYPE> ct, ResConstructor<TYPE> after, @Nullable NodeDeliverer<TYPE> del) {

	  int id;
	  if (after == this) id = 0;
	  else {
		id = ar.indexOf(after, true) + 1;
		if (id == -1) id = ar.size;
	  }

	  ar.insert(id, ct);

	  if (node != null && del != null) getNode(del).insert(id, ct.getNode(del));
	  else nodeRRQ = true;

	  ct.setParent(this);

	  if (order) reorder(del);

	  updateSources();
    }

    private void reorder(NodeDeliverer<TYPE> del) {
	  orderArray();

	  if (node == null || del == null) {
		nodeRRQ = true;
		return;
	  }

	  getNode(del).removeAll();

	  for (ResConstructor<TYPE> ct : ar) {
		getNode(del).add(ct.getNode(del));
	  }

    }

    private void orderArray() {

    }

    public void setOrder(boolean order) {
	  this.order = order;
    }

    public boolean isOrder() {
	  return order;
    }

    //

    @Override
    public void setNode(ResTreeNode<TYPE> node, NodeDeliverer<TYPE> del) {
	  super.setNode(node, del);

	  if (node == null) return;

	  if (del == null) {
		nodeRRQ = true;
		return;
	  }

	  nodeRRQ = false;

	  for (ResConstructor<TYPE> ct : ar) {
		node.add(ct.getNode(del));
	  }

	  getNode(del).updateChildren();

    }

    @Override
    public void newNode(NodeDeliverer<TYPE> del) {

	  if (del == null) return;

	  node = del.node(this);

	  nodeRRQ = false;
	  for (ResConstructor<TYPE> ct : ar) {
		node.add(ct.getNode(del));
	  }

    }

    //


    public Array<ResConstructor<TYPE>> getAr() {
	  return ar;
    }
}
