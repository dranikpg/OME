package com.draniksoft.ome.editor.support.ems.core;

import com.artemis.World;
import com.draniksoft.ome.editor.systems.gui.UiSystem;

public abstract class SimpleEditMode implements EditMode {

    protected World _w;

    @Override
    public final void attached(World _w) {
        this._w = _w;
        on_attached();
    }

    protected abstract void on_attached();

    protected void deflateUI() {
	  _w.getSystem(UiSystem.class).createBK();

    }

    protected void returnUIState() {

	  _w.getSystem(UiSystem.class).inflateBK();

    }


}
