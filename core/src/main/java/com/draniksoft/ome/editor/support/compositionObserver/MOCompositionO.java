package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.support.actions.mapO.CreateNewMOA;
import com.draniksoft.ome.editor.support.actions.mapO.MoveMOA;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.render.def.SelectionRenderer;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.support.ui.util.CompObViewIds;

import static com.draniksoft.ome.editor.support.compositionObserver.MOCompositionO.ActionCodes.*;

public class MOCompositionO extends SimpleCompositionObserver {

    public static class ActionCodes {
        // X;Y;DWB_ID;W;H
        public static final int CREATE = ActionDesc.BaseCodes.ACTION_CREATE;
        public static final int RESET = ActionDesc.BaseCodes.ACTION_RESET;

        // 10 -> 20 setter

        // X;Y;CENTER?
        public static final int MOVE = 10;
        //
        public static final int RESIZE = 11;

	  // M_R - > map from runtime, R_M -> runtime from map (i.e A << B)

	  public static final int SYNC_M_R = 12;
	  public static final int SYNC_R_M = 13;


        // 20 -> 30 Quick EM launcher

        public static final int MOVE_QEM = 20;



    }

    IntMap<ActionDesc> desc;

    SelectionRenderer r;
    OverlayRenderSys rSys;

    @Override
    protected void on_selchanged(boolean previousActivity, int prevID) {
        if (isSelActive()) {
            if (!previousActivity) {
                rSys.addRdr(r);
            }
            r.e = _selE;
        } else {
            if (previousActivity) {
                rSys.removeRdr(r);
            }
        }
        _w.getSystem(EditorSystem.class).getEditModeDesc(EditModeDesc.IDS.moveMO).aviabT = isSelActive() ? EditModeDesc.AVAILABLE : EditModeDesc.AV_HIDDEN;
    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

    }

    @Override
    protected void _init() {

        desc = new IntMap<ActionDesc>();

	  rSys = _w.getSystem(OverlayRenderSys.class);
	  r = new SelectionRenderer();

        if (__ds == null) return;
        for (ActionDesc d : __ds) {
            desc.put(d.code, d);
        }


    }

    @Override
    public IntMap<ActionDesc> getDesc() {
        return desc;
    }

    @Override
    public boolean isAviab(int ac, int e) {

        if (ac == CREATE) {
            return !matches(e);
	  } else {
		return matches(e);
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

        if (id == CREATE) {

            CreateNewMOA a = new CreateNewMOA();
            a._e = _e;
            if (os.length > 0) a.x = (Integer) os[0];
            if (os.length > 1) a.y = (Integer) os[1];
            if (os.length > 2) a.dwbID = (String) os[2];
            if (os.length > 3) a.w = (Integer) os[3];
            if (os.length > 4) a.h = (Integer) os[4];

            if (aT) _w.getSystem(ActionSystem.class).exec(a);
            else a.invoke(_w);

        } else if (id == MOVE) {

            MoveMOA a = new MoveMOA();
            a._e = _e;
            if (os.length > 0) a.x = (Integer) os[0];
            if (os.length > 1) a.y = (Integer) os[1];
            if (os.length > 2) a.center = (Boolean) os[2];


	  } else if (id == MOVE_QEM) {

		_w.getSystem(EditorSystem.class).attachNewEditMode(EditModeDesc.IDS.moveMO);

	  } else if (id == SYNC_M_R) {

		MoveMOA a = new MoveMOA();
		PosSizeC c = _w.getMapper(PosSizeC.class).get(_e);
		a._e = _e;
		a.x = c.x;
		a.y = c.y;

		if (aT) _w.getSystem(ActionSystem.class).exec(a);
		else a.invoke(_w);

	  } else if (id == SYNC_R_M) {

		_w.getSystem(PositionSystem.class).resetPos(_e);

	  }
    }

    @Override
    public String getName() {
        return "Map Object";
    }

    @Override
    public boolean isViewAv(short id) {
	  return id == CompObViewIds.VIEW_INSPECTOR;
    }

    @Override
    public String getViewID(short id) {
	  return "";
    }


    @Override
    protected Aspect.Builder getAspectB() {
        return Aspect.all(MObjectC.class, PosSizeC.class, DrawableC.class);
    }
}
