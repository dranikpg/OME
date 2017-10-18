package com.draniksoft.ome.editor.support.event.workflow;

import net.mostlyoriginal.api.event.common.Event;

public class ModeChangeE implements Event {

    public ModeChangeE(boolean SHOW_MODE) {
        this.SHOW_MODE = SHOW_MODE;
    }

    public boolean SHOW_MODE;

}
