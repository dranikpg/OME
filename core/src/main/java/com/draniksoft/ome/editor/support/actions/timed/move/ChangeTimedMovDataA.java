package com.draniksoft.ome.editor.support.actions.timed.move;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class ChangeTimedMovDataA implements Action {

    public Array<MoveDesc> ar;

    public int _e;

    private Array<MoveDesc> old;

    @Override
    public void _do(World w) {

        TimedMoveC c = w.getMapper(TimedMoveC.class).get(_e);

        old = c.a;
        c.a = ar;

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.TimedMoveValsChange(_e));


    }

    @Override
    public void _undo(World w) {


        TimedMoveC c = w.getMapper(TimedMoveC.class).get(_e);

        c.a = old;

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.TimedMoveValsChange(_e));

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
        return "Changed MOv vals";
    }

    @Override
    public void destruct() {

        ar = null;
        old = null;

    }
}
