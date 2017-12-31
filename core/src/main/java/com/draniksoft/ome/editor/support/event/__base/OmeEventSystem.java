package com.draniksoft.ome.editor.support.event.__base;

import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventSystem;

public class OmeEventSystem extends EventSystem {

    private static final String tag = "EventSystem";

    @Override
    public void dispatch(Event event) {
	  super.dispatch(event);

	  if (!(event instanceof ResizeEvent)) Gdx.app.debug(tag, event.toString());


    }

}
