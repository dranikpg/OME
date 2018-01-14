package com.draniksoft.ome.editor.base_gfx.drawable_contructor;

import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.base_gfx.drawable.group.StackDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;

public class GroupConstructor extends DwbConstructor {

    private static final String tag = "GroupConstructor";

    public void add(DwbConstructor c) {

	  car.add(c);
	  c.setParent(this);

	  getNode().add(c.getNode());

	  rebuild();

    }

    public void insertAfter(DwbConstructor fc, DwbConstructor c) {

	  int id = car.indexOf(fc, true);

	  if (id > 0) {
		car.insert(id, c);
		c.setParent(this);
		getNode().insert(id, c.getNode());

		rebuild();

	  } else {
		add(c);
	  }


    }

    public void insertAfter(EditDwbView.DwbNode n, DwbConstructor c) {
	  DwbConstructor cc = (DwbConstructor) n.getObject();
	  insertAfter(cc, c);
    }

    public void remove(EditDwbView.DwbNode node) {
	  car.removeValue((DwbConstructor) node.getObject(), true);
	  getNode().remove(node);
	  node.C().setNode(null);
	  rebuild();
    }

    @Override
    public void setNode(EditDwbView.DwbNode node) {
	  super.setNode(node);

	  if (node == null) return;

	  for (DwbConstructor c : car) {
		node.add(c.getNode());
	  }

	  getNode().updateChildren();

    }

    @Override
    protected void newNode() {
	  node = new EditDwbView.DwbNode(this);

	  for (DwbConstructor c : car) {
		node.add(c.getNode());
	  }

    }

    Array<DwbConstructor> car;

    LinkedDrawable linkedD;

    Drawable dwb;

    public GroupConstructor() {
	  car = new Array<DwbConstructor>();
	  linkedD = new LinkedDrawable();

	  dwb = new StackDrawable();
    }

    public void updateSources() {
	  rebuild();
    }

    private void rebuild() {
	  Array<Drawable> dwbA = new Array<Drawable>(car.size);

	  for (DwbConstructor c : car) dwbA.add(c.getSnapshot());

	  dwb = new StackDrawable(dwbA);

	  linkedD.link = dwb;
    }


    @Override
    public GdxCompatibleDrawable getGdxSnapshot() {
	  return GdxCompatibleDrawable.from(linkedD);
    }

    @Override
    public Drawable getSnapshot() {
	  return linkedD;
    }


}
