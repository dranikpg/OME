package com.draniksoft.ome.editor.support.event;

import com.draniksoft.ome.editor.support.ems.core.EditMode;
import net.mostlyoriginal.api.event.common.Event;

public class EditModeChangeE implements Event {

    public EditMode prevEM;
    public EditMode newEM;

}
