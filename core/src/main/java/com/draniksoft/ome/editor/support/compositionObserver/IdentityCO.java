package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.draniksoft.ome.editor.components.base.IdentityC;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.EasierCompositionObserver;

public class IdentityCO extends EasierCompositionObserver {

    @Override
    protected void on_selchanged(boolean previousActivity, int previd) {

    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

    }

    @Override
    protected Aspect.Builder getAspectB() {
	  return Aspect.all(IdentityC.class);
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
