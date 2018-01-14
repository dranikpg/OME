package com.draniksoft.ome.editor.support.event.file_b;

import net.mostlyoriginal.api.event.common.Event;

public class ResourceLoadedEvent implements Event {

    public ResourceLoadedEvent(int t, String id) {
	  this.t = t;
	  this.id = id;
    }

    public int t = -1;
    public String id;


    public static int TYPE_ASSEt = 1;
    public static int TYPE_FONT = 2;

}
