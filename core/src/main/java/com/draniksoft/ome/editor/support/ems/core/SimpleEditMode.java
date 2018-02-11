package com.draniksoft.ome.editor.support.ems.core;

import com.artemis.World;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleEditMode implements EditMode {

    protected World _w;

    @Override
    public final void attached(@NotNull World _w) {
	  this._w = _w;
        on_attached();
    }

    @Override
    public void detached() {
	  on_detached();
    }

    protected abstract void on_attached();

    protected abstract void on_detached();

    protected void defalteEnv() {
	  _w.getSystem(CameraSys.class).createBK();
	  _w.getSystem(UiSystem.class).createBK();
	  _w.getSystem(InputSys.class).createDefBK();

    }

    protected void returnEnv() {
	  _w.getSystem(CameraSys.class).inflateBK();
	  _w.getSystem(UiSystem.class).inflateBK();
	  _w.getSystem(InputSys.class).restoreBK();

    }

    protected void destroySelf() {
	  _w.getSystem(EditorSystem.class).detachEditMode();
    }

}
