package com.draniksoft.ome.editor.base_gfx.drawable_contructor;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable.group.AnimatedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.group.GroupDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.group.StackDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.t.DwbGroupTypes;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;
import com.draniksoft.ome.utils.JsonUtils;

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
		car.insert(id + 1, c);
		c.setParent(this);
		getNode().insert(id + 1, c.getNode());

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

    public Array<DwbConstructor> getChildren() {
	  return car;
    }

    Array<DwbConstructor> car;
    LinkedDrawable linkedD;

    Drawable dwb;
    DwbGroupTypes type;

    public GroupConstructor() {
	  car = new Array<DwbConstructor>();
	  linkedD = new LinkedDrawable();

	  dwb = new StackDrawable();
	  type = DwbGroupTypes.STACK;
    }

    public void updateSources() {
	  rebuild();
    }

    private void rebuild() {
	  Array<Drawable> dwbA = new Array<Drawable>(car.size);
	  for (DwbConstructor c : car) dwbA.add(c.getSnapshot());
	  dwb = ((GroupDrawable) dwb).copy(dwbA);
	  linkedD.link = dwb;
    }

    private void fullRebuild() {
	  Array<Drawable> dwbA = new Array<Drawable>(car.size);
	  for (DwbConstructor c : car) dwbA.add(c.getSnapshot());
	  dwb = ((GroupDrawable) dwb).newCopy(dwbA);
    }

    public void setType(DwbGroupTypes t) {
	  if (t.equals(type)) return;


	  Array<Drawable> dwbA = new Array<Drawable>(car.size);
	  for (DwbConstructor c : car) dwbA.add(c.getSnapshot());

	  if (t == DwbGroupTypes.STACK) {
		dwb = new StackDrawable(dwbA);
	  } else {
		dwb = new AnimatedDrawable(dwbA);
	  }

	  linkedD.link = dwb;
	  type = t;
    }

    public DwbGroupTypes getType() {
	  return type;
    }

    @Override
    public GdxCompatibleDrawable getGdxSnapshot() {
	  return GdxCompatibleDrawable.from(linkedD);
    }

    @Override
    public Drawable getSnapshot() {
	  return linkedD;
    }

    @Override
    public Drawable construct() {
	  updateSources();
	  return dwb;
    }

    @Override
    public void putData(JsonValue v) {
	  v.addChild("t", JsonUtils.createIntV(getType().id()));
    }

    @Override
    public void fetchData(JsonValue v) {

    }

    @Override
    public void destruct() {

    }
}
