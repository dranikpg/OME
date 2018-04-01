package com.draniksoft.ome.editor.support.compositionObserver.abstr;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.utils.JsonUtils;
/*
    Multi purpose class for managing various tings
 */

public abstract class CompositionObserver {

    private final static String tag = "CompositionObserver";

    public static class IDs {

	  public static final short IDENTITY = 1;

	  public static final short POSITION = 3;

	  public static final short DRAWABLE = 4;

    }

    public int PRIORITY = 1;

    public int ID;

    public abstract void init(World w);

    public abstract void setSelection(int e);

    public abstract void selectionCompChanged();

    public abstract boolean isSelActive();

    public abstract boolean matches(int e);

    // Desc

    public abstract IntMap<ActionDesc> getDesc();

    public abstract boolean isAviab(int ac, int e);

    public abstract boolean isAviab(int ac);

    public abstract ActionDesc getDesc(int ac);

    //

    public abstract void execA(int id, int _e, boolean aT, Object... os);

    public abstract String getName();

    /*
	  Array of parsed action configs, hidden with double __
     */
    protected Array<ActionDesc> __ds;

    public void loadActionConfig(JsonValue rootv) {

        __ds = new Array<ActionDesc>();

	  for (JsonValue v : rootv) {
		ActionDesc d = new ActionDesc();
		d.code = v.getInt("c");

		d.name = JsonUtils.parseText(v.get("name"), true);
		d.desc = JsonUtils.parseText(v.get("desc"), false);

		__ds.add(d);
	  }

    }

    public abstract boolean isViewAv(short id);

    public abstract String getViewID(short id);


}
