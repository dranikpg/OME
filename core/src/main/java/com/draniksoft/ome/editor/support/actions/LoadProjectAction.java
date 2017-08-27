package com.draniksoft.ome.editor.support.actions;

import com.artemis.World;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;

public class LoadProjectAction implements Action {

    String path;

    public LoadProjectAction(String path) {
        this.path = path;
    }

    @Override
    public void _do(World w) {

        w.getSystem(ProjecetLoadSys.class).setBundle(new MapLoadBundle(path));
        w.getSystem(ProjecetLoadSys.class).load();

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
        return true;
    }
}
