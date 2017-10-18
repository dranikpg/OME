package com.draniksoft.ome.editor.support.actions.timed._base_;

import com.artemis.World;
import com.draniksoft.ome.editor.components.time.TimedC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class AddTimeCA implements Action {

    public AddTimeCA() {
    }

    public AddTimeCA(int _e, int se, int ee) {
        this._e = _e;
        this.se = se;
        this.ee = ee;
    }

    public int _e;
    public int se;
    public int ee;

    public boolean DISPATCH_EVENTS = true;


    @Override
    public void _do(World w) {

        TimedC tc = w.getMapper(TimedC.class).create(_e);
        tc.s = se;
        tc.e = ee;

        if (DISPATCH_EVENTS)
            w.getSystem(EventSystem.class).dispatch(new CompositionChangeE(_e));

    }

    @Override
    public void _undo(World w) {

        w.getMapper(TimedC.class).remove(_e);

        if (DISPATCH_EVENTS)
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
        return "Added time component";
    }

    @Override
    public void destruct() {

    }
}
