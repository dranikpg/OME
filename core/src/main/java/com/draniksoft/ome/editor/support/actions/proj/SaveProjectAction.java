package com.draniksoft.ome.editor.support.actions.proj;

import com.artemis.World;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjectLoadSystem;
import org.jetbrains.annotations.NotNull;

public class SaveProjectAction implements Action {

    @Override
    public void invoke(@NotNull World w) {
	  w.getSystem(ProjectLoadSystem.class).save();
    }

    @Override
    public void undo(World _w) {

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
