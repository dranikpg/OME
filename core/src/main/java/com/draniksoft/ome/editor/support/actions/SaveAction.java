package com.draniksoft.ome.editor.support.actions;

import com.artemis.World;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;

public class SaveAction implements Action {
    @Override
    public void _do(World w) {
        w.getSystem(ProjecetLoadSys.class).save();
    }

    @Override
    public void _undo() {

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
