package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.support.actions.mapO.CreateNewMOA;
import com.draniksoft.ome.editor.support.actions.mapO.MoveMOA;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.render.def.SelectionRenderer;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.support.ui.util.CompObViewIds;

public class MOCompositionO extends SimpleCompositionObserver {

    public static class ActionCodes {
        // X;Y;DWB_ID;W;H
        public static final int CREATE = ActionDesc.BaseCodes.ACTION_CREATE;
        // X;Y;CENTER?
        public static final int MOVE = 10;
        //
        public static final int RESIZE = 11;
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
            Gdx.app.debug(tag, "NO MORE ACTIVE " + previousActivity);
            if (previousActivity) {
                Gdx.app.debug(tag, "Removing REnderer");
                rSys.removeRdr(r);
            }
        }
    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

    }

    @Override
    protected void _init() {

        desc = new IntMap<ActionDesc>();

        if (__ds == null) return;

        for (ActionDesc d : __ds) {
            desc.put(d.code, d);
        }

        rSys = _w.getSystem(OverlayRenderSys.class);
        r = new SelectionRenderer();
    }

    @Override
    public IntMap<ActionDesc> getDesc() {
        return desc;
    }

    @Override
    public boolean isAviab(int ac, int e) {

        if (ac == ActionCodes.CREATE) {
            return !matches(e);
        } else if (ac == ActionDesc.BaseCodes.ACTION_RESET || (ac >= ActionCodes.MOVE && ac <= ActionCodes.RESIZE)) {
            return matches(e);
        }

        return false;

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

        if (id == ActionCodes.CREATE) {

            CreateNewMOA a = new CreateNewMOA();
            a._e = _e;
            if (os.length > 0) a.x = (Integer) os[0];
            if (os.length > 1) a.y = (Integer) os[1];
            if (os.length > 2) a.dwbID = (String) os[2];
            if (os.length > 3) a.w = (Integer) os[3];
            if (os.length > 4) a.h = (Integer) os[4];

            if (aT) _w.getSystem(ActionSystem.class).exec(a);
            else a.invoke(_w);

        } else if (id == ActionCodes.MOVE) {

            MoveMOA a = new MoveMOA();
            a._e = _e;
            if (os.length > 0) a.x = (Integer) os[0];
            if (os.length > 1) a.y = (Integer) os[1];
            if (os.length > 2) a.center = (Boolean) os[2];

            if (aT) _w.getSystem(ActionSystem.class).exec(a);
            else a.invoke(_w);

        }
    }

    @Override
    public String getName() {
        return "Map Object";
    }

    @Override
    public boolean isViewAv(int id) {
	  return id == CompObViewIds.VIEW_INSPECTOR;
    }

    @Override
    public String getViewID(int id) {
        return "";
    }


    @Override
    protected Aspect.Builder getAspectB() {
        return Aspect.all(MObjectC.class, PosSizeC.class, DrawableC.class);
    }
}
