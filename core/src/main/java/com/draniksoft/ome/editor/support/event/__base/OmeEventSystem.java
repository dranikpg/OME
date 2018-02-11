package com.draniksoft.ome.editor.support.event.__base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventSystem;

import static com.draniksoft.ome.editor.support.event.__base.OmeEventSystem.DYNAMIC_TYPE.*;

/*
	Dynamic listener workaround due to possible absence of listener removing.
	Dynamic listeners are objects which can be finalized, so the default strategy of artemis-odb-contribs would fail in deleting their references
 */
public class OmeEventSystem extends EventSystem {

    private static final String tag = "EventSystem";

    public static class DYNAMIC_TYPE {

	  public static final int UI = 1;

	  public static final int EDIT_MODE = 2;

	  public static final int MISC = 3;

    }

    IntMap<Array<EventListener>> dynamicLs;

    @Override
    protected void initialize() {
	  super.initialize();
	  dynamicLs = new IntMap<Array<EventListener>>();
	  dynamicLs.put(UI, new Array<EventListener>(false, 8));
	  dynamicLs.put(EDIT_MODE, new Array<EventListener>(false, 8));
	  dynamicLs.put(MISC, new Array<EventListener>(false, 8));
    }

    public void addDynamicL(EventListener l, int usage) {
	  dynamicLs.get(usage).add(l);
    }

    public void removeDynamicL(EventListener l, int usage) {
	  dynamicLs.get(usage).removeValue(l, true);
    }

    @Override
    public void dispatch(Event event) {
	  super.dispatch(event);

	  if (!(event instanceof ResizeEvent)) Gdx.app.debug(tag, event.toString());
    }

}
