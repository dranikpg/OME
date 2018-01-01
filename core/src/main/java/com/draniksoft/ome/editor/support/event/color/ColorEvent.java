package com.draniksoft.ome.editor.support.event.color;

import net.mostlyoriginal.api.event.common.Event;

public class ColorEvent implements Event {

    public int id;

    public ColorEvent() {
    }

    public ColorEvent(int id) {
	  this.id = id;
    }

    public static class ColorAddedEvent extends ColorEvent {

	  public ColorAddedEvent(int id) {
		super(id);
	  }
    }

    public static class ColorRemovedEvent extends ColorEvent {

	  public ColorRemovedEvent(int id) {
		super(id);
	  }
    }

    public static class ColorNameChangeE extends ColorEvent {

	  public ColorNameChangeE(int id) {
		super(id);
	  }
    }
}
