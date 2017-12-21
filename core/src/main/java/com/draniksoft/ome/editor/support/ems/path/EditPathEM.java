package com.draniksoft.ome.editor.support.ems.path;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.support.actions.path.CommitPathAddA;
import com.draniksoft.ome.editor.support.actions.path.CommitPathChangeA;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.container.path.PathDesc;
import com.draniksoft.ome.editor.support.container.path.PathRTDesc;
import com.draniksoft.ome.editor.support.container.path.PathSDesc;
import com.draniksoft.ome.editor.support.ems.core.SimpleEditMode;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.support.input.back.StebIC;
import com.draniksoft.ome.editor.support.input.path.PathEditIC;
import com.draniksoft.ome.editor.support.render.path.PathEditR;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.editor.systems.time.ObjTimeCalcSys;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.struct.Points;
import net.mostlyoriginal.api.event.common.Subscribe;

public class EditPathEM extends SimpleEditMode {

    private static final String tag = "EditPathEM";

    int _e;

    PathEditR r;
    PathEditIC ic;

    PathRunTimeC rtC;
    PathDescC dC;

    int curIDX = -1;
    boolean newM = false;

    int dragIDX = -1;

    boolean revert = false;

    @Override
    protected void on_attached() {

	  _e = _w.getSystem(EditorSystem.class).sel;
	  rtC = _w.getMapper(PathRunTimeC.class).get(_e);
	  dC = _w.getMapper(PathDescC.class).get(_e);

	  if (dC.ar == null) dC.ar = new Array<PathSDesc>();
	  if (rtC.p == null) rtC.p = new Array<PathRTDesc>();

	  setUpRIC();
    }

    private void setUpRIC() {
	  r = new PathEditR();
	  ic = new PathEditIC();

	  r._e = _e;
	  ic._e = _e;

	  r.em = this;
	  ic.em = this;

	  _w.getSystem(OverlayRenderSys.class).addRdr(r);

	  _w.getSystem(InputSys.class).setMainIC(ic);
	  _w.getSystem(InputSys.class).setDefIC(new StebIC());

	  _w.getSystem(UiSystem.class).createBK();
	  _w.getSystem(UiSystem.class).openWin("edit_path_em");

    }

    private void removeRIC() {
	  _w.getSystem(OverlayRenderSys.class).removeRdr(r);
	  _w.getSystem(InputSys.class).clearMainIC();
	  _w.getSystem(InputSys.class).restoreDef();

	  _w.getSystem(UiSystem.class).inflateBK();
    }

    public void recompute(boolean pv) {
	  if (curIDX > -1) {
		float f = 1f;
		if (pv) {
		    f = AppDO.I.C().getConfVal_I("path_preview_factor") / 5f;
		}
		_w.getSystem(ObjTimeCalcSys.class).processEntityPath(_e, curIDX, f);
	  }
    }

    public void recompute() {
	  recompute(true);
    }

    public void recompute(int id) {
	  if (id > -1) {
		_w.getSystem(ObjTimeCalcSys.class).processEntityPath(_e, id);
	  }
    }

    public void commit() {
	  if (curIDX < 0) return;
	  recompute(false);
	  if (newM) {
		CommitPathAddA a = new CommitPathAddA();
		a.ar = getdC().ar;
		a._e = _e;
		a.d = getPathDesc();
		if (revert) a.undo(_w);
		else _w.getSystem(ActionSystem.class).exec(a);
	  } else {
		CommitPathChangeA a = new CommitPathChangeA();
		a.dsc = getPathDesc();
		a._e = _e;
		a.orig = getOrig();
		if (revert) a.undo(_w);
		else _w.getSystem(ActionSystem.class).exec(a);
	  }

	  revert = false;

    }

    public void newPath(int idx, boolean cpyFirst) {

	  Gdx.app.debug(tag, "New path");

	  clearOldSel();
	  curIDX = idx;
	  newM = true;

	  if (idx < dC.ar.size) {
		rtC.p.insert(idx, null);
		dC.ar.insert(idx, new PathSDesc());
	  } else {
		rtC.p.add(null);
		dC.ar.add(new PathSDesc());
	  }

	  if (cpyFirst && idx > 0) {
		PathDesc prev = dC.ar.get(idx - 1);
		dC.ar.get(idx).ar.add(prev.ar.get(prev.ar.size - 1));
	  } else if (cpyFirst) {
		Vector2 p = _w.getSystem(PositionSystem.class).getCenterV(_e);
		dC.ar.get(idx).ar.add(p);
	  }

	  notifySel();

    }

    @Override
    public void update() {

    }

    @Override
    public void detached() {
	  removeRIC();
    }


    public void newPath(boolean cpyFirst) {
	  newPath(dC.ar.size, cpyFirst);
    }

    public void newPath() {
	  newPath(true);
    }

    public void setDragIDX(int dragIDX) {
	  this.dragIDX = dragIDX;
    }

    public int getDragIDX() {
	  return dragIDX;
    }

    public void removeIdx(int idx) {
	  dC.ar.removeIndex(idx);
	  rtC.p.removeIndex(idx);
	  if (idx == curIDX) setSel(-1);
    }

    Points orig;
    Vector2 nexS;

    private void notifySel() {
	  r.newSel();
	  ic.newSel();
	  if (curIDX > -1) {
		orig = new Points(getPathDesc().ar);
		if (dC.ar.size > curIDX + 1 && dC.ar.get(curIDX + 1).alignToPrev) {
		    nexS = new Vector2(dC.ar.get(curIDX + 1).ar.get(0));
		} else {
		    nexS = null;
		}
	  }
    }

    public Points getOrig() {
	  return orig;
    }

    public void revertOrig() {
	  if (curIDX < 0) return;
	  if (newM) {
		removeIdx(curIDX);
	  } else {
		getPathDesc().ar = orig;
		recompute();
	  }
    }

    public void setRevert(boolean revert) {
	  this.revert = revert;
    }

    private void clearOldSel() {
	  commit();
    }

    public void setSel(int i) {
	  Gdx.app.debug(tag, "New sel");
	  clearOldSel();
	  curIDX = i;
	  newM = false;
	  notifySel();
    }


    public PathDescC getdC() {
	  return dC;
    }

    public PathRunTimeC getRtC() {
	  return rtC;
    }


    public int getCurIDX() {
	  return curIDX;
    }

    public boolean isNewM() {
	  return newM;
    }

    public PathSDesc getPathDesc() {
	  if (curIDX == -1) return null;
	  return dC.ar.get(curIDX);
    }

    public void selfDestroy() {
	  _w.getSystem(EditorSystem.class).detachEditMode();
    }

    @Subscribe
    public void pathCChange(EntityDataChangeE.PathCountChangeE ev) {
	  setSel(-1);
    }

    @Override
    protected void finalize() throws Throwable {
	  Gdx.app.debug(tag, "Finalized");
    }

    @Override
    public int ID() {
	  return EditModeDesc.IDS.pathEdit;
    }
}
