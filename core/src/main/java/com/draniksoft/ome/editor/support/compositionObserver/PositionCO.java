package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.components.pos.PosC;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.EasierCompositionObserver;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.utils.cam.Target;

import static com.draniksoft.ome.editor.support.compositionObserver.PositionCO.ACodes.*;
import static com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc.BaseCodes.*;

public class PositionCO extends EasierCompositionObserver {

    public static class ACodes {

	  // Edit mode section 20-30

	  public static final int LAUNCH_MOVE_EM = 21;

	  // Some actions 30-40

	  // pos_size_c -> map_dimens
	  public static final int SYNC_POS = 31;

	  public static final int FOCUS_CAMERA = 32;

    }

    @Override
    protected void on_selchanged(boolean previousActivity, int previd) {
	  if (isSelActive()) {
		execA(FOCUS_CAMERA, _selE, false);
	  }

	  _w.getSystem(EditorSystem.class).getEditModeDesc(EditModeDesc.IDS.moveMO).available =
		    isSelActive();
    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

    }

    @Override
    protected Aspect.Builder getAspectB() {
	  return Aspect.all(PosC.class, PosBoundsC.class);
    }

    @Override
    public boolean isAviab(int ac, int e) {
	  if (matches(e)) {
		return ac != ACTION_CREATE && ac != ACTION_EDITVW_CREATE;
	  } else {
		return true;
	  }
    }

    @Override
    public void execA(int id, int _e, boolean aT, Object... os) {
	  switch (id) {
		case ACTION_CREATE:
		    break;

		case ACTION_RESET:
		    break;

		case ACTION_DELETE:
		    break;

		case LAUNCH_MOVE_EM:
		    _w.getSystem(EditorSystem.class).attachNewEditMode(EditModeDesc.IDS.moveMO);
		    break;

		case SYNC_POS:
		    _w.getSystem(PositionSystem.class).save(_e);
		    break;

		case FOCUS_CAMERA:
		    focusCam(_e);
		    break;
	  }
    }

    private void focusCam(int _e) {
	  Target.EntityPosTarget t = new Target.EntityPosTarget();
	  t._e = _e;
	  t.ps = _w.getSystem(PositionSystem.class);
	  _w.getSystem(CameraSys.class).setTarget(t);
    }

    @Override
    public String getName() {
	  return "PositionCO";
    }

    @Override
    public boolean isViewAv(short id) {
	  return false;
    }

    @Override
    public String getViewID(short id) {
	  return null;
    }
}
