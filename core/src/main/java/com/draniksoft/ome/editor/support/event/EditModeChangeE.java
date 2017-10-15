package com.draniksoft.ome.editor.support.event;

import com.draniksoft.ome.editor.support.workflow.EditMode;
import net.mostlyoriginal.api.event.common.Event;

public class EditModeChangeE implements Event {

    public EditMode prevEM;
    public EditMode newEM;

}
