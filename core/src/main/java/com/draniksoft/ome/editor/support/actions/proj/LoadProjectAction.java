package com.draniksoft.ome.editor.support.actions.proj;

import com.artemis.World;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjectLoadSystem;
import org.jetbrains.annotations.NotNull;

public class LoadProjectAction implements Action {

    String path;

    public LoadProjectAction(String path) {
        this.path = path;
    }

    @Override
    public void invoke(@NotNull World w) {

	  w.getSystem(ProjectLoadSystem.class).setBundle(new MapLoadBundle(path));
	  w.getSystem(ProjectLoadSystem.class).load();

    }

    @Override
    public void undo(@NotNull World w) {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public boolean isCleaner() {
        return true;
    }

    @Override
    public String getSimpleConcl() {
	  return "Loaded proj";
    }

    @Override
    public void destruct() {

    }


}
