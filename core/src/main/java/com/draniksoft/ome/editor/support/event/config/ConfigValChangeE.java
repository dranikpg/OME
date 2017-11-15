package com.draniksoft.ome.editor.support.event.config;

import net.mostlyoriginal.api.event.common.Event;

public class ConfigValChangeE implements Event {

    String id;

    public ConfigValChangeE(String id) {
	  this.id = id;
    }

    public String getId() {
	  return id;
    }
}
