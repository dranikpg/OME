package com.draniksoft.ome.editor.support.actions.loc;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.container.MoveDesc;

public class AddTimedMoveC implements Action {

    public int _e;

    public AddTimedMoveC(int _e) {
        this._e = _e;
    }

    public AddTimedMoveC() {

    }

    @Override
    public void _do(World w) {

        TimedMoveC c = w.getMapper(TimedMoveC.class).create(_e);
        c.a = new Array<MoveDesc>();

    }

    @Override
    public void _undo(World w) {

        w.getMapper(TimedMoveC.class).remove(_e);

    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }
}
