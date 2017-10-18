package com.draniksoft.ome.editor.support.actions.timed.move;

import com.artemis.World;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.container.MoveDesc;

public class AddTimedMoveA implements Action {

    public int _e;
    public int sx, sy, ex, ey;
    public int s, e;

    @Override
    public void _do(World w) {

        TimedMoveC c = w.getMapper(TimedMoveC.class).get(_e);
        MoveDesc d = new MoveDesc();
        d.start_x = sx;
        d.start_y = sy;
        d.end_x = ex;
        d.end_y = ey;
        d.time_s = s;
        d.time_e = e;

        c.a.add(d);

    }

    @Override
    public void _undo(World w) {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }

    @Override
    public String getSimpleConcl() {
        return null;
    }

    @Override
    public void destruct() {

    }
}
