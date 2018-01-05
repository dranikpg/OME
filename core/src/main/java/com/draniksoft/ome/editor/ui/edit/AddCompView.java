package com.draniksoft.ome.editor.ui.edit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.Iterator;

public class AddCompView extends BaseWinView implements ActionContainer {

    private static final String tag = "AddCompView";

    int e;

    @LmlActor("root")
    VisTable root;

    @LmlActor("cnt")
    VisTable content;

    float rowS = 10;

    private void initFor(int _e, boolean immdReb) {
	  this.e = _e;

	  if (immdReb) {
		rebuild();
	  }

    }

    private void rebuild() {

	  Gdx.app.debug(tag, "Rebuilding");

	  content.clearChildren();

	  if (e < 0) return;

	  Iterator<CompositionObserver> i = _w.getSystem(EditorSystem.class).getComObsI();
	  CompositionObserver c;

	  while (i.hasNext()) {
		c = i.next();
		if (c.isAviab(ActionDesc.BaseCodes.ACTION_EDITVW_CREATE)) {

		    ActionDesc d = c.getDesc(ActionDesc.BaseCodes.ACTION_EDITVW_CREATE);

		    if (d == null) {
			  Gdx.app.error(tag, "Given action (5) is null for " + c);
			  continue;
		    }

		    addDesc(d, c);

		}
	  }

    }

    private void addDesc(ActionDesc d, final CompositionObserver o) {

	  VisTextButton b = new VisTextButton("", "big");

	  b.setText(d.getName());

	  b.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    o.execA(ActionDesc.BaseCodes.ACTION_CREATE, e, true);
		    close();
		}
	  });

	  content.add(b).expandX().fillX().padBottom(rowS);
	  content.row();

    }

    @LmlAction("l.cancel")
    public void cancel() {
	  close();
    }

    public void close() {
	  _w.getSystem(UiSystem.class).closeWin();
    }

    public void setFor(int _e) {
	  initFor(_e, false);
    }

    public void initFor(int _e) {
	  initFor(_e, true);
    }

    @Override
    public void opened() {
	  super.opened();
	  rebuild();
    }

    @Override
    public Actor getActor() {
	  return root;
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

	  rowS = p.parseFloat(p.getData().getArgument("_1"));

    }

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

    }
}
