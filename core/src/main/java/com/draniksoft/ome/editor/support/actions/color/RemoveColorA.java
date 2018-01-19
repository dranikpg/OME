package com.draniksoft.ome.editor.support.actions.color;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.base_gfx.color.simple.LinkColor;
import com.draniksoft.ome.editor.manager.ProjValsManager;
import com.draniksoft.ome.editor.support.actions.Action;
import org.jetbrains.annotations.NotNull;

public class RemoveColorA implements Action {

    public int id;

    Array<LinkColor> car;

    Color cval;
    String name;

    ProjValsManager mgr;

    @Override
    public void invoke(@NotNull World w) {

	 /* mgr = w.getSystem(ProjValsManager.class);

	  cval = mgr.getColor(id);
	  name = mgr.getColorName(id);

	  car = mgr.deleteColor(id);

	  w.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorRemovedEvent(id));*/

    }

    @Override
    public void undo(@NotNull World w) {

	  /*int id = mgr.createColor(name, cval);

	  for (LinkColor c : car) {
		mgr.registerColor(c, id);
	  }

	  w.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorAddedEvent(id));*/

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
