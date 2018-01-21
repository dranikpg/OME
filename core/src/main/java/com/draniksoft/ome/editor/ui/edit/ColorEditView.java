package com.draniksoft.ome.editor.ui.edit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTree;

public class ColorEditView extends BaseWinView {

    private static final String tag = "ColorEditView";

    @LmlActor("root")
    VisTable root;

    @LmlActor("tree")
    VisTree tree;

    @LmlActor("edit")
    VisTable editT;

    ActionHandler handler;


    //

    // inits

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

    }

    void initDragAndDrop() {

	  DragAndDrop dd = new DragAndDrop();


    }

    private void updateSelection() {


    }

    String w_vw;

    @Override
    protected void handleInclude(String nm, BaseView vw) {
	  super.handleInclude(nm, vw);

    }


    // classses

    public interface ActionHandler {

    }

    public static class ProjValHandler implements ActionHandler {

    }


    // Lml actions

    public void add_group() {

    }

    public void add_leaf() {

    }

    public void save() {

    }

    public void restore() {

    }

    // dead


    @Override
    public Actor get() {
	  return root;
    }
}
