package com.draniksoft.ome.editor.ui.edit;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.EmptyDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.DrawableParser;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.DwbConstructor;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.GroupConstructor;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.LeafConstructor;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.manager.ProjValsManager;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.CacheSystem;
import com.draniksoft.ome.editor.ui.edit.dwb_typevw.DwbEditI;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.kotcrab.vis.ui.widget.*;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class EditDwbView extends BaseWinView implements ActionContainer {

    private static String tag = "EditDwbView";

    @LmlActor("root")
    VisTable root;

    @LmlActor("tree")
    VisTree tree;

    @LmlActor("edit")
    VisTable editT;

    @LmlActor("fullimg")
    VisImage fullI;

    @LmlActor("leaf_b")
    VisTextButton leafB;

    @LmlActor("group_b")
    VisTextButton groupB;

    DwbEditI editVW;

    ActionHandler handler;

    volatile DwbConstructor rootC;


    public void init(ActionHandler h) {
	  this.handler = h;

	  rootC = null;
	  tree.clearChildren();

	  rootUpdated();
	  updateSelection();

	  new ParserC().start();

    }

    @Override
    public Actor getActor() {
	  return root;
    }

    @Override
    public void preinit() {


    }

    @Override
    public void postinit() {

	  tree.getSelection().setMultiple(false);
	  tree.getSelection().setProgrammaticChangeEvents(true);

	  tree.setYSpacing(10);

	  tree.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    updateSelection();
		}
	  });

	  initDragAndDrop();


	  try {
		Field field = Tree.class.getDeclaredField("indentSpacing");
		field.setAccessible(true);
		field.set(tree, 24);
	  } catch (Exception e) {
		e.printStackTrace();
	  }


    }

    String wt_view;

    private void updateEditT(DwbConstructor c) {


	  String incl = "";
	  if (c instanceof GroupConstructor) {
		incl = "dwbedit_group_prop";
	  } else {
		incl = "dwbedit_leaf_prop";
	  }

	  if (editVW != null && editVW.getID().equals(incl)) {
		editVW.setFor(c, handler);
		return;
	  }

	  editT.clearChildren();

	  removeIncldByName("edit");

	  wt_view = incl;

	  pushIncld("edit", incl);

    }

    @Override
    protected void handleInclude(String nm, BaseView vw) {
	  super.handleInclude(nm, vw);

	  if (!vw.ID.equals(wt_view)) return;

	  try {
		editVW = (DwbEditI) vw;

		editT.add(vw.getActor()).expand().fill();

		editVW.setFor((DwbConstructor) tree.getSelection().getLastSelected().getObject(), handler);

	  } catch (Exception e) {
		Gdx.app.error(tag, "", e);
	  }
    }

    private void updateSelection() {

	  Gdx.app.debug(tag, "Selection update");

	  leafB.setVisible(true);
	  groupB.setVisible(true);

	  if (tree.getSelection().isEmpty()) {
		editT.setVisible(false);
		return;
	  } else {
		editT.setVisible(true);
	  }

	  DwbConstructor c = (DwbConstructor) tree.getSelection().getLastSelected().getObject();

	  updateEditT(c);

    }


    private void initDragAndDrop() {

	  DragAndDrop dd = new DragAndDrop();

	  dd.addSource(new DragAndDrop.Source(tree) {
		@Override
		public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {

		    DwbNode n = (DwbNode) tree.getNodeAt(y);

		    if (n == null || tree.getSelection().getLastSelected() != n) return null;

		    DragAndDrop.Payload l = new DragAndDrop.Payload();
		    l.setObject(n.C());
		    VisLabel pzda = new VisLabel("LEL");
		    l.setDragActor(pzda);

		    if (n.C().getParent() != null) n.C().getParent().remove(n);
		    else {
			  tree.remove(n);
			  rootC = null;
		    }

		    return l;
		}

		@Override
		public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
		    super.dragStop(event, x, y, pointer, payload, target);
		    if (target == null) {
			  if (payload.getObject() == rootC) {
				rootC = null;
				rootUpdated();
			  }
			  Gdx.app.debug(tag, "Deleted");
		    }
		}
	  });

	  dd.addTarget(new DragAndDrop.Target(tree) {
		@Override
		public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
		    return true;
		}

		@Override
		public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {

		    DwbNode n = (DwbNode) tree.getNodeAt(y);

		    addOnDrop((DwbConstructor) payload.getObject(), n);

		}
	  });

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
    public void closed() {
	  super.closed();
	  removeIncldByName("edit");
	  handler = null;
    }

    private void connectRoot() {
	  tree.clearChildren();
	  tree.add(rootC.getNode());
    }

    private void rootUpdated() {
	  Gdx.app.debug(tag, "Root updated");
	  if (rootC != null) fullI.setDrawable(rootC.getGdxSnapshot());
	  else fullI.setDrawable(GdxCompatibleDrawable.from(new EmptyDrawable()));
	  updateSelection();
    }


    private void add(DwbConstructor c, @Nullable DwbNode parent, @Nullable DwbNode after) {

	  if (rootC == null) {

		tree.add(c.getNode());

		rootC = c;
		rootUpdated();

		Gdx.app.debug(tag, "Replaced root");

	  } else {

		if (parent == null) {

		    if (rootC instanceof GroupConstructor) {
			  if (after != null)
				((GroupConstructor) rootC).insertAfter(after, c);
			  else
				((GroupConstructor) rootC).add(c);

			  Gdx.app.debug(tag, "Added to root(group)");

		    } else {

			  GroupConstructor newR = new GroupConstructor();

			  rootC.getNode().remove();

			  newR.add(rootC);
			  newR.add(c);

			  tree.add(newR.getNode());

			  rootC = newR;
			  rootUpdated();

			  Gdx.app.debug(tag, "Emplaced root");

		    }

		} else {

		    if (after != null) {

			  ((GroupConstructor) parent.C()).insertAfter(after, c);

		    } else {

			  ((GroupConstructor) parent.C()).add(c);

		    }

		    parent.setExpanded(true);


		}

	  }

	  tree.getSelection().set(c.getNode());

    }

    private void addOnDrop(DwbConstructor c, DwbNode n) {

	  if (n == null) {
		add(c, null, null);
	  } else {
		if (n.C() instanceof GroupConstructor) {
		    add(c, n, null);
		} else {
		    add(c, (DwbNode) n.getParent(), n);
		}

	  }

    }

    private void addOnSel(DwbConstructor c) {


	  addOnDrop(c, (DwbNode) tree.getSelection().getLastSelected());

    }

    @LmlAction("leaf_add")
    public void leaf_add() {
	  addOnSel(new LeafConstructor());
    }

    @LmlAction("group_add")
    public void group_add() {
	  addOnSel(new GroupConstructor());
    }

    @LmlAction("save")
    public void save() {
	  Gdx.app.debug(tag, "Saving");
	  handler.save(rootC);
	  _w.getSystem(UiSystem.class).closeWin();
    }

    @LmlAction("restore")
    public void restore() {
    }

    public static class DwbNode extends Tree.Node {

	  public DwbNode(DwbConstructor c) {
		super(new DwbTable());

		setObject(c);

		setExpanded(true);

		setIcon(c.getGdxSnapshot());
		getIcon().setMinWidth(40);
		getIcon().setMinHeight(40);

	  }


	  public <TYPE extends DwbConstructor> TYPE C() {
		return (TYPE) getObject();
	  }


	  @Override
	  protected void addToTree(Tree tree) {
		super.addToTree(tree);
	  }


    }

    private static class DwbTable extends VisTable {

	  public DwbTable() {
		add("LLELLELE");
	  }
    }

    private final class ParserC extends Thread {
	  @Override
	  public void run() {
		DwbConstructor c = handler.prepare(_w);
		rootC = c;
		connectRoot();
		rootUpdated();

		updateSelection();
	  }
    }

    public interface ActionHandler {

	  DwbConstructor prepare(World w);

	  void save(DwbConstructor c);


    }

    public static class ValDwbHandler implements ActionHandler {

	  int id;

	  World _w;

	  public ValDwbHandler(int id) {
		this.id = id;
	  }


	  @Override
	  public DwbConstructor prepare(World w) {
		this._w = w;

		if (_w.getSystem(ProjValsManager.class).getDrawable(id) instanceof EmptyDrawable) return null;

		if (_w.getSystem(CacheSystem.class).hasGlobAttrib(CacheSystem.RESOURCE.DWB_CONST, id)) {
		    Gdx.app.debug(tag, "Fetching global from cache");
		    return _w.getSystem(CacheSystem.class).getGlobAttrib(CacheSystem.RESOURCE.DWB_CONST, id);
		} else {
		    Gdx.app.debug(tag, "Parsing from raw sources");
		    DwbConstructor c = DrawableParser.I().parse(_w.getSystem(ProjValsManager.class).getDwbRawSrc(id));
		    _w.getSystem(CacheSystem.class).putGlobAttrib(CacheSystem.RESOURCE.DWB_CONST, id, c);
		}

		return null;
	  }

	  @Override
	  public void save(DwbConstructor c) {

		Drawable build = c.construct();

		_w.getSystem(ProjValsManager.class).setDrawable(id, build);

		_w.getSystem(CacheSystem.class).putGlobAttrib(CacheSystem.RESOURCE.DWB_CONST, id, c);

	  }
    }

    public class EttyDwbHandler implements ActionHandler {

	  int id;


	  public EttyDwbHandler(int id) {
		this.id = id;
	  }


	  World _w;

	  @Override
	  public DwbConstructor prepare(World w) {
		this._w = w;

		if (_w.getSystem(CacheSystem.class).hasEttyAttrib(CacheSystem.RESOURCE.DWB_CONST, id)) {
		    return _w.getSystem(CacheSystem.class).getEttyAttrib(CacheSystem.RESOURCE.DWB_CONST, id);
		} else {
		    Gdx.app.debug(tag + " ETD", " Parsing from scratch ");
		    MObjectC mc = _w.getMapper(MObjectC.class).get(id);
		    DwbConstructor c = DrawableParser.I().parse(mc.dwbID);
		}

		return null;
	  }

	  @Override
	  public void save(DwbConstructor c) {

	  }
    }


}

