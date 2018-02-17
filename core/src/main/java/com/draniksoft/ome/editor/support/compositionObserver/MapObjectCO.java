package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.draniksoft.ome.editor.components.tps.MapObjectC;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.EasierCompositionObserver;
import com.draniksoft.ome.editor.support.render.def.SelectionRenderer;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;


public class MapObjectCO extends EasierCompositionObserver {

    SelectionRenderer rd;

    public static class ACodes {

    }


    @Override
    protected void _init() {
	  super._init();
	  rd = new SelectionRenderer();
    }

    @Override
    protected void on_selchanged(boolean previousActivity, int previd) {
	  _w.getSystem(OverlayRenderSys.class).removeRdr(rd);
	  if (isSelActive()) {
		rd.e = _selE;
		_w.getSystem(OverlayRenderSys.class).addRdr(rd);
	  }
    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

    }

    @Override
    protected Aspect.Builder getAspectB() {
	  return Aspect.all(MapObjectC.class);
    }

    @Override
    public boolean isAviab(int ac, int e) {
	  return false;
    }

    @Override
    public void execA(int id, int _e, boolean aT, Object... os) {

    }

    @Override
    public String getName() {
	  return null;
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
