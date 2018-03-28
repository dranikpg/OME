package com.draniksoft.ome.editor.support.event.projectVals;

import com.draniksoft.ome.editor.res.impl.types.ResTypes;
import net.mostlyoriginal.api.event.common.Event;

public class ProjectValEvent implements Event {

    public ResTypes t;
    public int id;

    public ProjectValEvent(ResTypes t, int id) {
	  this.t = t;
	  this.id = id;
    }

    public static class Create extends ProjectValEvent {
	  public Create(ResTypes t, int id) {
		super(t, id);
	  }
    }

    public static class Delete extends ProjectValEvent {
	  public Delete(ResTypes t, int id) {
		super(t, id);
	  }
    }

    public static class Rename extends ProjectValEvent {
	  public Rename(ResTypes t, int id) {
		super(t, id);
	  }
    }

}
