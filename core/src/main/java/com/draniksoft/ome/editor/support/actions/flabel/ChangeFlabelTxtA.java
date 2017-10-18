package com.draniksoft.ome.editor.support.actions.flabel;

import com.artemis.World;
import com.draniksoft.ome.editor.components.selection.FLabelC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class ChangeFlabelTxtA implements Action {

    public int _e;

    public String newT;
    String oldT;

    @Override
    public void _do(World w) {

        FLabelC c = w.getMapper(FLabelC.class).get(_e);
        oldT = c.txt;
        c.txt = newT;


        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.FlabelTextChangeE(_e));

    }

    @Override
    public void _undo(World w) {

        w.getMapper(FLabelC.class).get(_e).txt = oldT;

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.FlabelTextChangeE(_e));

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
        return "Changed label text";
    }

    @Override
    public void destruct() {

    }
}
