package com.draniksoft.ome.editor.ui.insp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.editor.ui.edit.AddCompView;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.ui.util.CompObViewIds;
import com.draniksoft.ome.support.ui.util.WindowAgent;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.Iterator;

public class InspView extends BaseWinView implements ActionContainer {


    public interface InspectorManagable {

	  void initFor(int e);

    }

    private static final String tag = "InspView";

    @LmlActor("root")
    VisTable root;

    @LmlActor("pane")
    VisScrollPane pane;

    @LmlActor("headc")
    Container headC;

    @LmlActor("content")
    VisTable content;

    @LmlActor("btm")
    VisTable bottom;


    int id;

    float tp;


    public void initFor(int id) {
	  this.id = id;
    }


    private void rebuild(boolean inj) {
	  Gdx.app.debug(tag, "Rebuilding on " + id + " inj conf " + inj);

	  if (id < 0) {
		Gdx.app.debug(tag, "Closing due to bad selection");
		closeWin();
	  }

	  clearInjectedIncludes();
	  content.clearChildren();
	  headC.setActor(null);

	  clearInclds();

	  Iterator<CompositionObserver> i = _w.getSystem(EditorSystem.class).getComObsI();
	  CompositionObserver c;

	  while (i.hasNext()) {
		c = i.next();
		if (c.isViewAv(CompObViewIds.VIEW_INSPECTOR)) {
		    String tag = c.HEAD ? "head" : String.valueOf(c.ID);
		    addIncld(tag, c.getViewID(CompObViewIds.VIEW_INSPECTOR));
		}
	  }

	  if (inj) AppDO.I.LML().injectIncludes(this);

    }

    @LmlAction("addclick")
    public void addclick() {

	  Gdx.app.debug(tag, "Add comp pressed !!");

	  _w.getSystem(UiSystem.class).openWin("add_comp", new WindowAgent() {
		@Override
		public void opened(BaseWinView vw) {
		    ((AddCompView) vw).setFor(id);
		}

		@Override
		public void notifyClosing() {
		    _w.getSystem(UiSystem.class).openInspector(id);
		}

		@Override
		public void closed() {
		    Gdx.app.debug(tag, "COMP WIN CLOSED");
		}
	  });

    }

    @Subscribe
    public void comChange(CompositionChangeE e) {

	  if (active && e.e == id) {
		rebuild(true);
	  }
    }

    @Override
    public void obtainIncld(String name, BaseView vw) {
	  super.obtainIncld(name, vw);

	  if (vw instanceof InspectorManagable) ((InspectorManagable) vw).initFor(id);

	  if (name.equals("head")) {
		headC.fill();
		headC.setActor(vw.get());
	  } else {

		content.add(vw.get()).expandX().fillX();
		content.row();

	  }
    }

    @Override
    public void closed() {
	  super.closed();
	  Gdx.app.debug(tag, "Closed");
	  content.clearChildren();
	  headC.clearChildren();
    }

    @Override
    public void opened() {
	  rebuild(false);
	  super.opened();
	  Gdx.app.debug(tag, "Opened");
    }

    @Override
    public Actor get() {
	  return root;
    }

    @Override
    public void preinit() {

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
    public void postinit() {

	  _w.getSystem(OmeEventSystem.class).registerEvents(this);

	  root.setDebug(true);

    }
}
