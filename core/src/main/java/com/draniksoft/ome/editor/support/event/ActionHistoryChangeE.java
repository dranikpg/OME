package com.draniksoft.ome.editor.support.event;

import net.mostlyoriginal.api.event.common.Event;

public class ActionHistoryChangeE implements Event {

    public static class ActionUndoEvent extends ActionHistoryChangeE implements Event {
    }

    public static class ActionDoneEvent extends ActionHistoryChangeE implements Event {
    }

}
