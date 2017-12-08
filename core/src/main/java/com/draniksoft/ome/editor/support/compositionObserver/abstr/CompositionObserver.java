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

    public abstract void execA(int id, int _e, Object... os);

    public abstract String getName();

    protected Array<ActionDesc> __ds;

    public void loadActionConfig(JsonValue rootv) {

        __ds = new Array<ActionDesc>();

        JsonValue ar = rootv.get("a");

        for (JsonValue v : ar) {

            BiLangActionDEsc d = new BiLangActionDEsc();
            d.code = v.getInt("c");
            d.noargpsb = v.getBoolean("noargpsb");
            d.name_en = v.getString("name_en");
            d.name_ru = v.getString("name_ru");
            d.desc_en = v.getString("desc_en");
            d.desc_ru = v.getString("desc_ru");

            __ds.add(d);
        }

    }

    public abstract boolean isViewAv(int id);

    public abstract String getViewID();



    /**
     * Returns the settings Table, should return null if not present
     *
     * @return
     */


}
