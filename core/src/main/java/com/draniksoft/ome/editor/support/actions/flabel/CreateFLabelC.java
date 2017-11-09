package com.draniksoft.ome.editor.support.actions.flabel;

import com.artemis.World;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class CreateFLabelC implements Action {

    public String baseT;

    public int _e;

    @Override
    public void _do(World w) {

        w.getMapper(FLabelC.class).create(_e);

        if (baseT != null && baseT.length() > 0)
            w.getMapper(FLabelC.class).get(_e).txt = baseT;

        else w.getMapper(FLabelC.class).get(_e).txt = "";

        w.getSystem(EventSystem.class).dispatch(new CompositionChangeE(_e));

    }

    @Override
    public void _undo(World w) {

        w.getMapper(FLabelC.class).remove(_e);

        w.getSystem(EventSystem.class).dispatch(new CompositionChangeE(_e));

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
        return "Added label";
    }

    @Override
    public void destruct() {

    }
}
