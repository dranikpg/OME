package com.draniksoft.ome.editor.support.actions.color;

import com.artemis.World;
import com.draniksoft.ome.editor.support.actions.Action;
import org.jetbrains.annotations.NotNull;

public class AddColorA implements Action {
    @Override
    public void invoke(@NotNull World w) {

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
	  return false;
    }

    @NotNull
    @Override
    public String getSimpleConcl() {
	  return null;
    }

    @Override
    public void destruct() {

    }
}
