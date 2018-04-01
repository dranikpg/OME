package com.draniksoft.ome.utils;

import com.artemis.World;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;


public class ESCUtils {

    private static final String tag = "ESCUtils";

    public static final int EVENT_MAX_PRIORITY = 100;
    public static final int EVENT_HIGH_PRIORITY = 80;
    public static final int EVENT_MID_PRIORITY = 50;
    public static final int EVENT_DEF_PRIORITY = 0;
    public static final int EVENT_LOW_PRIORITY = -20;
    public static final int EVENT_MIN_PRIORITY = -50;
    public static final int EVENT_LAST = -100;


    public static void clearSelected(World w) {

	  Gdx.app.debug(tag, "Clearing selected");


    }


    public static void removeSelectionBeforeRMV(int _e, World w) {

	  Gdx.app.debug(tag, "Removing sel before rmv");

	  if (w.getSystem(EditorSystem.class).sel == _e) {
		SelectionChangeE e = new SelectionChangeE();
            e.old = _e;
            e.n = -1;
		w.getSystem(OmeEventSystem.class).dispatch(e);
	  }

    }


    public static void registerJSrz(final JsonArtemisSerializer serializer) {


    }

    public static void registerBSrz(KryoArtemisSerializer serializer) {


    }
}
