package com.draniksoft.ome.editor.support.event.projectVals;

import net.mostlyoriginal.api.event.common.Event;

public class DrawableEvent implements Event {

    public int id;

    private DrawableEvent(int id) {
	  this.id = id;
    }

    public static class DrawableAddedE extends DrawableEvent {
	  public DrawableAddedE(int id) {
		super(id);
	  }
    }

    public static class DrawableRemovedE extends DrawableEvent {
	  private DrawableRemovedE(int id) {
		super(id);
	  }
    }

    public static class DrawableNameChangedE extends DrawableEvent {

	  public DrawableNameChangedE(int id) {
		super(id);
	  }
    }
}
