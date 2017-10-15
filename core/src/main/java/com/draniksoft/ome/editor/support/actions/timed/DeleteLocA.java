package com.draniksoft.ome.editor.support.actions.timed;

import com.artemis.World;
import com.draniksoft.ome.editor.support.actions.Action;

public class DeleteLocA implements Action {

    public int _e;

    public DeleteLocA(int _e) {
        this._e = _e;
    }

    public DeleteLocA() {
    }

    @Override
    public void _do(World _w) {

        _w.delete(_e);
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
        return "Deleted location";
    }

    @Override
    public void destruct() {

    }
}
