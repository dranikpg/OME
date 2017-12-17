package com.draniksoft.ome.editor.support.compositionObserver.abstr;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.BiLangActionDEsc;


public abstract class CompositionObserver {

    private final static String tag = "CompositionObserver";

    public static class IDs {
        public static final short MO_CO = 1;

	  public static final short PATH_CO = 2;
    }


    public int id;

    public abstract void init(World w);

    public abstract void setSelection(int e);

    public abstract void selectionCompChanged();

    public abstract boolean isSelActive();

    public abstract boolean matches(int e);

    public abstract IntMap<ActionDesc> getDesc();

    public abstract boolean isAviab(int ac, int e);

    public abstract boolean isAviab(int ac);

    public abstract ActionDesc getDesc(int ac);

    public abstract void execA(int id, int _e, boolean aT, Object... os);

    public abstract String getName();

    protected Array<ActionDesc> __ds;

    public void loadActionConfig(JsonValue rootv) {

        __ds = new Array<ActionDesc>();

        JsonValue ar = rootv.get("a");

        for (JsonValue v : ar) {

            BiLangActionDEsc d = new BiLangActionDEsc();

            d.code = v.getInt("c");
            d.name_en = v.getString("name_en");
		d.name_ru = v.has("name_ru") ? v.getString("name_ru") : d.name_en;
		d.desc_en = v.getString("desc_en");
		d.desc_ru = v.has("desc_ru") ? v.getString("desc_ru") : d.desc_en;

            __ds.add(d);

	  }

    }

    public abstract boolean isViewAv(short id);

    public abstract String getViewID(short id);


}
