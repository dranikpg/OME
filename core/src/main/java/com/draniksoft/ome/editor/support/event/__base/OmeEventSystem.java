package com.draniksoft.ome.editor.support.event.__base;

import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventSystem;

public class OmeEventSystem extends EventSystem {

    private static final String tag = "EventSystem";

    @Override
    public void dispatch(Event event) {
	  super.dispatch(event);
	  Gdx.app.debug(tag, event.toString());


    }

}
