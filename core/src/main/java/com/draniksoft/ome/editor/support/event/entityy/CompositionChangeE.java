package com.draniksoft.ome.editor.support.event.entityy;

import net.mostlyoriginal.api.event.common.Event;

public class CompositionChangeE implements Event {

    public CompositionChangeE() {
    }

    public CompositionChangeE(int e) {
        this.e = e;
    }

    public int e;

}
