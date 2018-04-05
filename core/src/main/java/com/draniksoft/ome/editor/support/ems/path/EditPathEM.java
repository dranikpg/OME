package com.draniksoft.ome.editor.support.ems.path;

import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.struct.path.runtime.Path;
import com.draniksoft.ome.editor.struct.path.srz.PathSzr;
import com.draniksoft.ome.editor.struct.path.transform.PathTransformer;
import com.draniksoft.ome.editor.support.actions.path.emsupp.AddPathEMSPA;
import com.draniksoft.ome.editor.support.actions.path.emsupp.EditPathEMSPA;
import com.draniksoft.ome.editor.support.actions.path.emsupp.PathEMSupportiveAction;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.ems.core.SimpleEditMode;
import com.draniksoft.ome.editor.support.event.__base.EventListener;
import com.draniksoft.ome.editor.support.input.back.StebIC;
import com.draniksoft.ome.editor.support.input.path.EditPathIC;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.path.EditPathRender;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import net.mostlyoriginal.api.event.common.Event;

public class EditPathEM extends SimpleEditMode implements EventListener {

    private static final String tag = "EditPathEM";

    public enum ACTION {
	  SAVE, EXIT, DISCARD, NEW_PATH, EDIT_SEL,
    }

    private EditPathIC ic;
    private EditPathRender rd;

    private int selection = -1;
    private boolean editing;


    private PathDescC descC;
    private PathRunTimeC rtC;

    PathEMSupportiveAction act;

    PathTransformer t;

    int _e;

    @Override
    protected void on_attached() {
	  Gdx.app.debug(tag, "Attachin");

	  _e = _w.getSystem(EditorSystem.class).sel;

	  ic = new EditPathIC();
	  ic.em = this;

	  rd = new EditPathRender();
	  rd.em = this;

	  t = new PathTransformer();

	  defalteEnv();
	  _w.getSystem(OverlayRenderSys.class).removeRdrByPlaceBK(new int[]{}, new int[]{OverlayPlaces.PATH});
	  _w.getSystem(OverlayRenderSys.class).addRdr(rd);

	  _w.getSystem(InputSys.class).setDefIC(new StebIC());
	  _w.getSystem(InputSys.class).setMainIC(ic);

	  _w.getSystem(UiSystem.class).closeWin();

	  descC = _w.getMapper(PathDescC.class).get(_e);
	  rtC = _w.getMapper(PathRunTimeC.class).get(_e);

    }

    public void setSelection(int id) {
	  if (editing) return;
	  this.selection = id;
	  rd.updateSelection();
	  ic.updateSelection();
    }

    public void deselect() {
	  setSelection(-1);
    }

    public int getSelection() {
	  return selection;
    }

    public boolean editing() {
	  return editing;
    }

    // action should be already preprocessed
    private void edit(int id) {
	  if (id != selection) setSelection(id);
	  editing = true;
	  t.init(descC.ar.get(selection).pts);
	  Gdx.app.debug(tag, "Editing on " + id);
	  updateMode();
    }


    private void edit() {
	  if (selection < 0) return;
	  EditPathEMSPA a = new EditPathEMSPA();
	  a.ifor(_e);
	  a.i = selection;
	  a.prepocess(_w, descC, rtC);
	  act = a;
	  edit(selection);
    }


    private void createNew() {
	  AddPathEMSPA a = new AddPathEMSPA();
	  a.ifor(_e);
	  a.prepocess(_w, descC, rtC);
	  act = a;
	  edit(descC.ar.size - 1);
    }

    private void stopEdit(boolean save) {
	  if (!editing) return;
	  if (save) {
		_w.getSystem(ActionSystem.class).exec(act.self());
	  } else {
		act.discard(_w, descC, rtC);
	  }
	  act = null;
	  editing = false;
	  updateMode();
    }

    public void updateEditing() {
	  if (!editing || selection < 0) return;
	  t.calc(rtC.ar.get(selection).pts, rtC.ar.get(selection).tb);
    }

    public void handle(ACTION a) {
	  Gdx.app.debug(tag, "Handling " + a.name());
	  if (a == ACTION.EXIT) {
		if (editing) handle(ACTION.DISCARD);
		destroySelf();
	  } else if (a == ACTION.DISCARD) {
		stopEdit(false);
	  } else if (a == ACTION.SAVE) {
		stopEdit(true);
	  } else if (a == ACTION.NEW_PATH) {
		createNew();
	  } else if (a == ACTION.EDIT_SEL) {
		edit();
	  }
    }

    @Override
    public void event(Event e) {
	  // TODO
    }


    @Override
    public void update() {

    }

    @Override
    protected void on_detached() {
	  returnEnv();
	  _w.getSystem(OverlayRenderSys.class).restoreBK();
	  _w.getSystem(OverlayRenderSys.class).removeRdr(rd);

	  _w.getSystem(InputSys.class).clearMainIC();
    }

    @Override
    public int ID() {
	  return EditModeDesc.IDS.pathEdit;
    }

    public PathDescC getDescC() {
	  return descC;
    }

    public PathRunTimeC getRtC() {
	  return rtC;
    }

    public Path getSelp() {
	  if (selection < 0) return null;
	  return rtC.ar.get(selection);
    }

    public PathSzr getSelSrz() {
	  if (selection < 0) return null;
	  return descC.ar.get(selection);
    }

    private void updateMode() {
	  ic.updateMode();
	  rd.updateMode();
    }
}
