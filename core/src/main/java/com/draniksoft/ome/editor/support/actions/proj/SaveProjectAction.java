package com.draniksoft.ome.editor.support.actions.proj;

import com.artemis.World;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjectLoadSystem;

public class SaveProjectAction implements Action {
    @Override
    public void _do(World w) {
	  w.getSystem(ProjectLoadSystem.class).save();
    }

    @Override
    public void _undo(World _w) {

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
        return "Saved Project";
    }

    @Override
    public void destruct() {

    }
}
