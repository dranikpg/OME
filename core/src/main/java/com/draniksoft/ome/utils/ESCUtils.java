package com.draniksoft.ome.utils;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.selection.SelectionC;
import com.draniksoft.ome.editor.components.state.InactiveC;
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

    public static final Class<? extends Component>[] ACTIVE_REQ = new Class[]{InactiveC.class};

    public static void clearSelected(World w) {

	  Gdx.app.debug(tag, "Clearing selected");

        IntBag b = w.getAspectSubscriptionManager().get(Aspect.all(SelectionC.class)).getEntities();
        ComponentMapper<SelectionC> mapper = w.getMapper(SelectionC.class);

        for (int i = 0; i < b.size(); i++) {

            int e = b.get(i);

            mapper.remove(e);

        }

        SelectionChangeE e = new SelectionChangeE();
        if (b.size() > 0)
            e.old = b.get(0);
	  w.getSystem(OmeEventSystem.class).dispatch(e);


    }

    public static int getFirstSel(World w) {
        IntBag b = w.getAspectSubscriptionManager().get(Aspect.all(SelectionC.class)).getEntities();

        if (b.size() > 0) return b.get(0);

        return -1;
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
