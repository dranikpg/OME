package com.draniksoft.ome.editor.support.actions.color;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.manager.ColorManager;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.color.ColorEvent;
import com.draniksoft.ome.utils.struct.EColor;
import org.jetbrains.annotations.NotNull;

public class RemoveColorA implements Action {

    public int id;

    Array<EColor> car;

    Color cval;
    String name;

    ColorManager mgr;

    @Override
    public void invoke(@NotNull World w) {

	  mgr = w.getSystem(ColorManager.class);

	  cval = mgr.get(id);
	  name = mgr.getName(id);

	  car = mgr.delete(id);

	  w.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorRemovedEvent(id));

    }

    @Override
    public void undo(@NotNull World w) {

	  int id = mgr.create(name, cval);

	  for (EColor c : car) {
		mgr.reg(c, id);
	  }

	  w.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorAddedEvent(id));

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
	  return null;
    }

    @Override
    public void destruct() {

    }
}
