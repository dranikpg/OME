package com.draniksoft.ome.editor.support.event.extension;

import com.draniksoft.ome.editor.extensions.Extension;
import net.mostlyoriginal.api.event.common.Event;

public class ExtensionEvent implements Event {

    Extension e;

    public ExtensionEvent(Extension e) {
	  this.e = e;
    }

    public static class ExtensionAddedE extends ExtensionEvent {
	  public ExtensionAddedE(Extension e) {
		super(e);
	  }
    }

    public static class ExtensionRemovedE extends ExtensionEvent {
	  public ExtensionRemovedE(Extension e) {
		super(e);
	  }
    }

    public static class ExtensionLoadedE extends ExtensionEvent {
	  public ExtensionLoadedE(Extension e) {
		super(e);
	  }
    }

}
