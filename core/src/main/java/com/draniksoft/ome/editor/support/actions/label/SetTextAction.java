package com.draniksoft.ome.editor.support.actions.label;

import com.artemis.World;
import com.draniksoft.ome.editor.components.label.LabelC;
import com.draniksoft.ome.editor.support.actions.Action;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class SetTextAction implements Action {

    public int _e;

    public String newT;

    String oldT;

    @Override
    public void invoke(@NotNull World w) {
	  LabelC c = w.getMapper(LabelC.class).get(_e);
	  oldT = c.text;
	  c.text = newT;
	  //w.getSystem(OmeEventSystem.class).dispatch();

    }

    @Override
    public void undo(@NotNull World w) {
	  LabelC c = w.getMapper(LabelC.class).get(_e);
	  c.text = oldT;
    }

    @Override
    public boolean isUndoable() {
	  return true;
    }

    @Override
    public boolean isCleaner() {
	  return false;
    }

    @NotNull
    @Override
    public String getSimpleConcl() {
	  return "Changed text";
    }

    @Override
    public void destruct() {

    }
}
