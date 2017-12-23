package com.draniksoft.ome.editor.support.event.workflow;

import com.draniksoft.ome.utils.iface.Awaitable;
import net.mostlyoriginal.api.event.common.Event;

public class ModeChangeE implements Event {

    public static class ShowEnterEvent extends ModeChangeE {

    }

    public static class ShowQuitEvent extends ModeChangeE {

    }

    public static class ShowRequestEvent extends ModeChangeE implements Awaitable {

	  volatile int c;

	  public void await() {
		c++;
	  }

	  public void ready() {
		c--;
	  }

	  public int getC() {
		return c;
	  }
    }

}
