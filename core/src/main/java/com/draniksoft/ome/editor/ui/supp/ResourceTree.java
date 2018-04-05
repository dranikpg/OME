package com.draniksoft.ome.editor.ui.supp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.impl.ui_br.NodeDeliverer;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.ui_addons.resource_ui.ResTreeNode;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTree;

public class ResourceTree extends BaseView {

    private static String tag = "ResourceTree";

    @LmlActor("root")
    VisTable root;

    @LmlActor("tree")
    VisTree tree;


    ResourceTreeL _ll;
    boolean dd = true;

    ResConstructor rC;

    //

    public void init(ResourceTreeL ll) {
	  clearTree();

	  this._ll = ll;
    }

    public void clearTree() {
	  _ll = null;
	  rC = null;
	  dd = true;
	  tree.clearChildren();
    }

    public void rootChanged(ResConstructor c) {
	  tree.clearChildren();
	  if (c != null) tree.add(c.getNode(getNodeD()));
	  rC = c;
    }

    private void initDD() {

	  DragAndDrop dd = new DragAndDrop();

	  dd.addSource(new DragAndDrop.Source(tree) {
		@Override
		public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {

		    DragAndDrop.Payload l = new DragAndDrop.Payload();
		    ResTreeNode n = (ResTreeNode) tree.getNodeAt(y);

		    if (n == null) return null;

		    tree.getSelection().clear();

		    n.setExpanded(false);

		    if (n.c().getParent() != null) n.c().getParent().remove(n.c(), getNodeD());


		    l.setObject(n.c());
		    l.setDragActor(n.getActor());


		    return l;
		}

		@Override
		public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
		    super.dragStop(event, x, y, pointer, payload, target);
		    if (target == null) {
			  Gdx.app.debug(tag, "Object dropped out of zone");
			  if (_ll != null) _ll.ddend((ResConstructor) payload.getObject(), true);
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

		    Gdx.app.debug(tag, "DD drop");

		    ResTreeNode n = (ResTreeNode) tree.getNodeAt(y);
		    ResConstructor cc = (ResConstructor) payload.getObject();

		    if (n == null) {
			  rC = cc;
			  tree.clear();
			  tree.add(cc.getNode(getNodeD()));
			  if (_ll != null) _ll.newRoot(cc);
		    } else {
			/*  Pair<ResConstructor, GroupResConstructor> nextG = n.c().findParentPrefix(true);
			  if (nextG == null) return;
			  nextG.V().add(cc, nextG.K(), getNodeD());*/
		    }

		    tree.getSelection().set(cc.getNode(getNodeD()));
		    cc.getNode(null).setExpanded(true);

		    if (_ll != null) _ll.ddend(cc, false);


		}
	  });

    }


    private NodeDeliverer getNodeD() {
	  if (_ll != null) {
		return _ll.getNoded();
	  } else {
		return null;
	  }

    }

    public void addOnSelection(ResConstructor c) {
	  ResTreeNode sel = (ResTreeNode) tree.getSelection().getLastSelected();
	  if (sel == null) {
		Gdx.app.debug(tag, "Add op on null v");
		rootChanged(c);
		if (_ll != null) _ll.newRoot(c);
		return;
	  }

	  /*Pair<ResConstructor, GroupResConstructor> nextG = sel.c().findParentPrefix(true);
	  if (nextG == null) return;
	  nextG.V().add(c, nextG.K(), getNodeD());*/

	  tree.getSelection().set(c.getNode(getNodeD()));
    }

    //

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

	  initDD();


	  tree.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {

		    if (_ll == null) return;

		    if (tree.getSelection().getLastSelected() == null) _ll.selChanged(null);

		    else _ll.selChanged((ResConstructor) tree.getSelection().getLastSelected().getObject());

		}
	  });

	  tree.getSelection().setMultiple(false);
	  tree.getSelection().setProgrammaticChangeEvents(true);
	  tree.setY(10);


    }

    @Override
    public void closed() {
	  super.closed();
	  clearTree();
    }


    //

    public interface ResourceTreeL {

	  void selChanged(ResConstructor ct);

	  void newRoot(ResConstructor ct);

	  void ddend(ResConstructor c, boolean removed);

	  NodeDeliverer getNoded();

    }

    //


    @Override
    public Actor get() {
	  return root;
    }

    public void setDragAndDrop(boolean dd) {
	  this.dd = dd;
    }
}
