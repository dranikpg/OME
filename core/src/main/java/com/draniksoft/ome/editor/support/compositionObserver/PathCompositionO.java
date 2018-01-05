package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRenderC;
import com.draniksoft.ome.editor.support.actions.path.CreatePathCA;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.editor.systems.time.ObjTimeCalcSys;
import com.draniksoft.ome.utils.struct.ResponseListener;

import static com.draniksoft.ome.editor.support.compositionObserver.PathCompositionO.ActIDs.*;

public class PathCompositionO extends SimpleCompositionObserver {

    public static class ActIDs {
	  public static final int CREATE = ActionDesc.BaseCodes.ACTION_CREATE;
	  public static final int REMOVE = ActionDesc.BaseCodes.ACTION_DELETE;

	  public static final int CREATE_VW = ActionDesc.BaseCodes.ACTION_EDITVW_CREATE;

	  public static final int EDIT_M = 11;

	  public static final int RECALC = 21;
	  public static final int RECALC_IDX = 22;

	  public static final int RECALC_ALL = 30;
    }

    IntMap<ActionDesc> desc;
    ComponentMapper<PathRenderC> rtM;


    @Override
    protected void on_selchanged(boolean previousActivity, int previd) {

	  if (previousActivity) rtM.remove(previd);
	  if (isSelActive()) rtM.create(_selE);

	  _w.getSystem(EditorSystem.class).getEditModeDesc(EditModeDesc.IDS.pathEdit).aviabT = isSelActive() ? EditModeDesc.AVAILABLE : EditModeDesc.AV_HIDDEN;
    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

	  if (previousActivity && !isSelActive()) {
		rtM.remove(_selE);
	  }
	  if (isSelActive() && !previousActivity) {
		rtM.create(_selE);
	  }

	  _w.getSystem(EditorSystem.class).getEditModeDesc(EditModeDesc.IDS.pathEdit).aviabT = isSelActive() ? EditModeDesc.AVAILABLE : EditModeDesc.AV_HIDDEN;
    }

    @Override
    protected void _init() {
	  desc = new IntMap<ActionDesc>();

	  rtM = _w.getMapper(PathRenderC.class);

	  if (__ds == null) return;
	  for (ActionDesc d : __ds) {
		desc.put(d.code, d);
	  }
    }

    @Override
    protected Aspect.Builder getAspectB() {
	  return Aspect.all(PathDescC.class);
    }

    @Override
    public IntMap<ActionDesc> getDesc() {
	  return desc;
    }

    @Override
    public boolean isAviab(int ac, int e) {
	  if (matches(e)) {
		return ac != CREATE_VW;
	  } else {
		return ac == CREATE || ac == CREATE_VW;
	  }
    }

    @Override
    public boolean isAviab(int ac) {
	  return isAviab(ac, _selE);
    }

    @Override
    public ActionDesc getDesc(int ac) {
	  return desc.get(ac);
    }

    @Override
    public void execA(int id, int _e, boolean aT, Object... os) {
	  switch (id) {
		case CREATE:

		    Gdx.app.debug(tag, "Creating path C");

		    CreatePathCA a = new CreatePathCA();
		    a._e = _e;
		    if (aT) {
			  _w.getSystem(ActionSystem.class).exec(a);
		    } else {
			  a.invoke(_w);
		    }
		    break;
		case EDIT_M:
		    break;
		case RECALC:
		    recalcEtty(_e);
		    break;
		case RECALC_IDX:
		    recalcEtty(_e, (Integer) os[0]);
		    break;
	  }
    }

    private void recalcEtty(int e, int p) {
	  _w.getSystem(ObjTimeCalcSys.class).processEntityPath(e, p);
    }

    private void recalcEtty(int e) {
	  _w.getSystem(ObjTimeCalcSys.class).processEntityPathC(new ResponseListener() {
		@Override
		public void onResponse(short code) {
		}
	  }, e);
    }

    @Override
    public String getName() {
	  return "PATH COM OB";
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
