package com.draniksoft.ome.editor.support.compositionObserver.abstr;

import com.artemis.Aspect;
import com.artemis.World;

public abstract class SimpleCompositionObserver extends CompositionObserver {

    public static final String tag = "SimpleCompositionObserver";

    boolean active;

    protected int _selE = -1;

    protected World _w;

    private Aspect.Builder aB;
    Aspect a;


    @Override
    public void init(World w) {
        this._w = w;

        _init();

        aB = getAspectB();

        if (aB != null) {
            a = aB.build(w);
        }

    }

    @Override
    public void setSelection(int e) {

        if (e == _selE) return;

        boolean bac = active;
        this._selE = e;
        active = matches(e);

        on_selchanged(bac);
    }

    protected abstract void on_selchanged(boolean previousActivity);

    @Override
    public void selectionCompChanged() {
        boolean bac = active;
        active = _selE > -1 && matches(_selE);
        on_selCompChanhed(bac);
    }

    protected abstract void on_selCompChanhed(boolean previousActivity);

    @Override
    public boolean isSelActive() {
        return active;
    }

    @Override
    public boolean matches(int e) {
        return e > -1 && a.isInterested(_w.getEntity(e));
    }


    protected abstract void _init();


    protected abstract Aspect.Builder getAspectB();

}
