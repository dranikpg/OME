package com.draniksoft.ome.editor.support.actions.mapO;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.compositionObserver.PositionCO;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.utils.ESCUtils;

public class CreateNewMOA implements Action {

    private static final String tag = "CreateNewMOA";

    public boolean GFX_PRC = true;

    private World _w;

    // center
    public float x, y;

    public float w, h;

    public String dwbID;


    public int _e;

    public CreateNewMOA(float x, float y, float w, float h, String dwbID) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dwbID = dwbID;
    }

    public CreateNewMOA() {
    }

    @Override
    public void invoke(World _w) {
        this._w = _w;
	  //  _e = _w.getSystem(ArchTransmuterMgr.class).build(ArchTransmuterMgr.Codes.BASE_MO);

	  PosBoundsC psc = _w.getMapper(PosBoundsC.class).get(_e);
	  psc.x = (int) (x - (w / 2));
        psc.y = (int) (y - (h / 2));
	  // psc.h = (int) h;
	  //  psc.w = (int) w;

	  _w.getSystem(EditorSystem.class).getComOb(CompositionObserver.IDs.POSITION).execA(PositionCO.ACodes.SYNC_POS, _e, false);

        if (!GFX_PRC) return;

        /*DrawableC dwC = _w.getMapper(DrawableC.class).get(_e);
	  DrawableSrcC dwbSC = _w.getMapper(DrawableSrcC.class).get(_e);

	  DrawableLeafContructor ct = new DrawableLeafContructor();
	  Gdx.app.debug(tag, "Buildin dwb " + dwbID);

	  ct.type(ResSubT.SIMPLE);
        SimpleDwbTD hd = (SimpleDwbTD) ct.handler();

        hd.setUri(dwbID);

	  dwC.d = ct.build(MainBase.engine).self();
	  dwbSC.c = ct;

        */
	  Gdx.app.debug(tag, "Fully created MO with " + dwbID);
    }

    @Override
    public void undo(World w) {
        ESCUtils.removeSelectionBeforeRMV(_e, _w);
	  _w.delete(_e);
    }


    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }

    @Override
    public String getSimpleConcl() {
        return "Created MO";
    }

    @Override
    public void destruct() {

    }
}
