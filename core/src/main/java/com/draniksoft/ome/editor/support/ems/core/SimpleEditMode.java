package com.draniksoft.ome.editor.support.ems.core;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class SimpleEditMode implements EditMode {

    protected World _w;

    @Override
    public final void attached(World _w) {
        this._w = _w;
        on_attached();
    }

    protected abstract void on_attached();

    protected void deflateUI(Table newt) {
        /*_w.getSystem(UiSystem.class).crateBackup();

        if (newt != null) {

            _w.getSystem(UiSystem.class).open(UiSystem.WinCodes.emSuppWin, null);
            ((EmSuppWin) _w.getSystem(UiSystem.class).getWin(UiSystem.WinCodes.emSuppWin)).pushT(newt);
        }

        */
    }

    protected void returnUIState() {

     /*   _w.getSystem(UiSystem.class).close(UiSystem.WinCodes.emSuppWin);
        _w.getSystem(UiSystem.class).loadBackup();

*/
    }


}
