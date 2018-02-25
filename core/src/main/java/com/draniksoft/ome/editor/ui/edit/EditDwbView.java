package com.draniksoft.ome.editor.ui.edit;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.srz.DrawableSrcC;
import com.draniksoft.ome.editor.manager.ResourceManager;
import com.draniksoft.ome.editor.res.drawable.constr.DrawableGroupConstructor;
import com.draniksoft.ome.editor.res.drawable.constr.DrawableLeafContructor;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.GroupResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.LeafConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubT;
import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResTypes;
import com.draniksoft.ome.editor.res.res_mgmnt_base.ui_br.NodeDeliverer;
import com.draniksoft.ome.editor.ui.edit.dwb_typevw.DwbEditI;
import com.draniksoft.ome.editor.ui.supp.ResourceTree;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;
import com.draniksoft.ome.ui_addons.resource_ui.dwb.ResDwbNode;
import com.draniksoft.ome.utils.FUtills;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;

public class EditDwbView extends BaseWinView implements ActionContainer {

    private static String tag = "EditDwbView";

    @LmlActor("root")
    VisTable root;

    @LmlActor("tree")
    VisTable treeT;

    @LmlActor("edit")
    VisTable editT;

    ResourceTree tree;
    ResConstructor rootC;
    String waitID;

    Handler h;
    DwbEditI editUI;

    ResConstructor target;


    public void ifor(Handler h) {
	  this.h = h;
	  rootC = h.get(_w);
	  if (tree != null) {
		tree.clearTree();
		tree.rootChanged(rootC);
	  }
    }



    @Override
    protected void handleInclude(String nm, BaseView vw) {
	  super.handleInclude(nm, vw);

	  if (nm.equals("edit")) {

		if (editUI != null) {
		    removeIncldbVID(editUI.getID());
		}

		if (vw == null) return;

		if (!vw.ID.equals(waitID)) return;
		waitID = null;

		editUI = (DwbEditI) vw;
		editUI.setFor(target, h);

		editT.clearChildren();
		editT.add(vw.get()).expand().fill();

	  } else if (nm.equals("tree")) {
		handleTree((ResourceTree) vw);
	  }

    }

    private void handleTree(ResourceTree t) {

	  Gdx.app.debug(tag, "Tree injection");

	  tree = t;
	  tree.clearTree();
	  tree.init(new ResourceTree.ResourceTreeL() {

		@Override
		public void selChanged(ResConstructor ct) {
		    updateSel(ct);
		}

		@Override
		public void newRoot(ResConstructor ct) {
		    EditDwbView.this.rootC = ct;
		}

		@Override
		public void ddend(ResConstructor c, boolean removed) {

		    if (removed) {
			  if (c.getParent() != null) {
				c.getParent().remove(c, getNoded());
			  } else if (c == rootC) {
				newRoot(null);
			  }
		    }

		}

		@Override
		public NodeDeliverer getNoded() {
		    return new NodeDeliverer() {
			  @Override
			  public ResTreeNode node(ResConstructor ct) {
				return getNode(ct);
			  }
		    };
		}
	  });
	  tree.rootChanged(rootC);


	  treeT.clearChildren();
	  treeT.add(tree.get()).expandY().fillY();
    }

    private ResTreeNode getNode(final ResConstructor ct) {

	  VisTable t = new VisTable();

	  VisSelectBox<ResSubT> sb = new VisSelectBox<ResSubT>() {
		@Override
		public void draw(Batch batch, float parentAlpha) {
		    super.draw(batch, parentAlpha);
		    if (this.getSelected() != ct.type()) {
			  ct.setType(getSelected());
			  if (ct == target) updateSel(ct);
		    }
		}
	  };

	  sb.setItems(ResSubT.fetchAll(ResTypes.DRAWABLE, ct.group()));

	  t.add("::");
	  t.add(sb);
	  ResDwbNode n = new ResDwbNode(ct, t);
	  n.setExpanded(true);

	  return n;
    }

    protected void updateDeTT(ResConstructor ct) {

	  String w = "";

	  if (ct instanceof GroupResConstructor) {
		w = "dwbedit_group_prop";
	  } else if (ct instanceof LeafConstructor) {
		w = "dwbedit_leaf_prop";
	  } else {
		editT.clearChildren();
		removeIncldByName("edit");
		return;
	  }

	  if (editUI != null && w.equals(editUI.getID())) {
		editUI.setFor(ct, h);
		return;
	  }

	  if (w.equals(waitID)) {
		return;
	  }

	  waitID = w;
	  pushIncld("edit", w);

    }

    private void updateSel(ResConstructor ct) {

	  target = ct;

	  updateDeTT(ct);

    }

    //

    @Override
    public void closed() {
	  super.closed();
	  treeT.clearChildren();
	  editT.clearChildren();
	  tree = null;
	  removeIncldByName("edit");
    }


    //

    @LmlAction("leaf_add")
    public void add_leaf() {
	  DrawableLeafContructor leafC = new DrawableLeafContructor();
	  leafC.msg(MsgBaseCodes.INIT, MsgDirection.END, null);
	  leafC.updateSources();
	  if (tree != null) tree.addOnSelection(leafC);
    }

    @LmlAction("group_add")
    public void add_group() {
	  DrawableGroupConstructor groupC = new DrawableGroupConstructor();
	  groupC.msg(MsgBaseCodes.INIT, MsgDirection.END, null);
	  groupC.updateSources();
	  if (tree != null) tree.addOnSelection(groupC);
    }

    public void save() {
	  h.save(rootC, _w);
	  closeWin();
    }

    //

    public interface Handler {

	  ResConstructor<Drawable> get(World w);

	  void save(ResConstructor<Drawable> root, World w);

    }

    public static class ProjDwbHandler implements Handler {

	  int id;

	  public ProjDwbHandler(int id) {
		this.id = id;
	  }

	  @Override
	  public ResConstructor<Drawable> get(World w) {

		Gdx.app.debug(tag, "Constructor lookup for " + id);

		ResourceManager m = w.getSystem(ResourceManager.class);
		if (m.get(ResTypes.DRAWABLE, id).ctr != null) {
		    Gdx.app.debug(tag, "Found chached constructor");
		    ResConstructor<Drawable> c = m.get(ResTypes.DRAWABLE, id).ctr;
		    c.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, null);
		    return c;
		}
		return null;
	  }

	  @Override
	  public void save(ResConstructor<Drawable> root, World w) {

		Gdx.app.debug(tag, "Saving " + id);

		Drawable rb = FUtills.buildDrawable(root);

		if (root != null) root.msg(MsgBaseCodes.DEINIT, MsgDirection.DOWN, null);

		w.getSystem(ResourceManager.class).update(ResTypes.DRAWABLE, rb, id);
		if (root != null) w.getSystem(ResourceManager.class).updateConstructor(ResTypes.DRAWABLE, root, id);


	  }
    }

    public static class EntityDwbHandler implements Handler {

	  public EntityDwbHandler(int e) {
		this.e = e;
	  }

	  int e;

	  @Override
	  public ResConstructor<Drawable> get(World w) {
		DrawableSrcC c = w.getMapper(DrawableSrcC.class).get(e);
		c.c.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, null);
		return c.c;
	  }

	  @Override
	  public void save(ResConstructor<Drawable> root, World w) {
		Drawable rb = FUtills.buildDrawable(root);

		root.msg(MsgBaseCodes.DEINIT, MsgDirection.DOWN, null);

		w.getMapper(DrawableC.class).get(e).d = rb;
		w.getMapper(DrawableSrcC.class).get(e).c = root;
	  }
    }

    //
    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

    }

    @Override
    public void prepareParser(LmlParser p) {
	  super.prepareParser(p);
	  p.getData().addActionContainer("l", this);
    }

    @Override
    public void clearParser(LmlParser p) {
	  super.clearParser(p);
	  p.getData().removeActionContainer("l");
    }

    @Override
    public Actor get() {
	  return root;
    }
}

