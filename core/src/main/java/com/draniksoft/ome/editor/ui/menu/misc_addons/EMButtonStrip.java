package com.draniksoft.ome.editor.ui.menu.misc_addons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.event.workflow.EditModeChangeE;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.kotcrab.vis.ui.widget.Tooltip;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTable;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.Iterator;

public class EMButtonStrip extends BaseView {

    private static final String tag = "EMButtonStrip";

    @LmlActor("root")
    VisTable t;

    VisTable local;
    ScrollPane pane;

    float s;

    ObjectMap<EditModeDesc, VisImageButton> bts;

    public void rebuild() {
	  bts.clear();
	  local.clearChildren();

	  final Iterator<EditModeDesc> i = _w.getSystem(EditorSystem.class).getEmDesc();
	  EditModeDesc d;

	  while (i.hasNext()) {
		d = i.next();
		if (d.available) {

		    final VisImageButton btn = new VisImageButton(d.iconID);

		    final EditModeDesc finalD = d;
		    bts.put(finalD, btn);


		    btn.setProgrammaticChangeEvents(false);
		    btn.addListener(new ChangeListener() {
			  @Override
			  public void changed(ChangeEvent event, Actor actor) {
				if (btn.isChecked())
				    _w.getSystem(EditorSystem.class).attachNewEditMode(finalD);
				else _w.getSystem(EditorSystem.class).detachEditMode();

			  }
		    });
		    new Tooltip.Builder(d.name.get()).target(btn).build();
		    local.add(btn).left().expandX().padLeft(20f).fillY().size(s);
		}

	  }
    }

    @Override
    public Actor get() {
	  return t;
    }

    @Override
    public void preinit() {
	  bts = new ObjectMap<EditModeDesc, VisImageButton>();
    }

    @Subscribe
    public void selChange(SelectionChangeE ev) {
	  rebuild();
    }


    @Subscribe
    public void compChangeEv(CompositionChangeE ev) {
	  if (ev.e != _w.getSystem(EditorSystem.class).sel) return;
	  rebuild();
    }

    @Subscribe
    public void emChange(EditModeChangeE ev) {

	  EditModeDesc d = null;
	  if (ev.newEM != null) d = _w.getSystem(EditorSystem.class).getEditModeDesc(ev.newEM.ID());
	  EditModeDesc d2 = null;
	  if (ev.prevEM != null) d2 = _w.getSystem(EditorSystem.class).getEditModeDesc(ev.prevEM.ID());

	  VisImageButton b = null;
	  if (d != null) b = bts.containsKey(d) ? bts.get(d) : null;
	  VisImageButton b2 = null;
	  if (d2 != null) b2 = bts.containsKey(d2) ? bts.get(d2) : null;

	  if (b != null) {
		b.setChecked(true);
	  }

	  if (b2 != null) {
		b2.setChecked(false);
	  }
    }

    @Override
    public void clearParser(LmlParser p) {
	  super.clearParser(p);
	  s = p.parseFloat(p.getData().getArgument("bar_icon_w"));
    }


    @Override
    public void postinit() {
	  _w.getSystem(OmeEventSystem.class).registerEvents(this);
	  local = new VisTable();
	  pane = new ScrollPane(local);
	  t.add(pane).expand().fill().minWidth(300f);
    }
}
