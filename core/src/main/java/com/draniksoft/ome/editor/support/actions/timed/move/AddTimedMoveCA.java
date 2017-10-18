package com.draniksoft.ome.editor.support.actions.timed.move;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class AddTimedMoveCA implements Action {

    public int _e;

    public AddTimedMoveCA(int _e) {
        this._e = _e;
    }

    public AddTimedMoveCA() {

    }

    @Override
    public void _do(World w) {

        TimedMoveC c = w.getMapper(TimedMoveC.class).create(_e);
        c.a = new Array<MoveDesc>();

        w.getSystem(EventSystem.class).dispatch(new CompositionChangeE(_e));

    }

    @Override
    public void _undo(World w) {

        w.getMapper(TimedMoveC.class).remove(_e);


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
        return "Added timed move C";
    }

    @Override
    public void destruct() {

    }
}
