package com.draniksoft.ome.editor.support.event.base_gfx;

import net.mostlyoriginal.api.event.common.Event;

public class ResizeEvent implements Event {

    public int w, h;

    public ResizeEvent(int w, int h) {
        this.w = w;
        this.h = h;
    }
}
