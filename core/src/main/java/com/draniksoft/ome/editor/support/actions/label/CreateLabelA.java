package com.draniksoft.ome.editor.support.actions.label;

import com.artemis.World;
import com.draniksoft.ome.editor.components.label.LabelC;
import com.draniksoft.ome.editor.components.label.LabelRTC;
import com.draniksoft.ome.editor.manager.FontManager;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import org.jetbrains.annotations.NotNull;

public class CreateLabelA implements Action {

    public int _e;

    public String baseT;
    public String font = "i_basefff";

    @Override
    public void invoke(@NotNull World w) {

	  LabelC c = w.getMapper(LabelC.class).create(_e);
	  c.font = font;
	  c.text = baseT;

	  w.getSystem(OmeEventSystem.class).dispatch(new CompositionChangeE(_e));

	  w.getSystem(FontManager.class).rebuildCache(_e);
    }

    @Override
    public void undo(@NotNull World w) {
	  w.getMapper(LabelC.class).remove(_e);
	  w.getSystem(OmeEventSystem.class).dispatch(new CompositionChangeE(_e));

	  if (w.getMapper(LabelRTC.class).has(_e)) {
		w.getMapper(LabelRTC.class).remove(_e);
	  }

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
	  return "DONE";
    }

    @Override
    public void destruct() {

    }
}
