package com.draniksoft.ome.editor.support.event.showM;

import com.draniksoft.ome.utils.iface.Awaitable;
import net.mostlyoriginal.api.event.common.Event;

public class ShowMTimeEvent implements Event {


    public static class TimePauseEvent extends ShowMTimeEvent {

    }

    public static class TimeResumeEvent extends ShowMTimeEvent {

    }

    public static class TimeChangeRequestEvent extends ShowMTimeEvent implements Awaitable {

	  public int time;

	  int c;

	  public void await() {
		c++;
	  }

	  @Override
	  public void ready() {
		c--;
	  }

	  public int getC() {
		return c;
	  }
    }

    public static class TimeChangeEvent extends ShowMTimeEvent {

	  public int time;
	  public boolean mainS;


    }
}
