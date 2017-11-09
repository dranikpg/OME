package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;

public class LoadHistoryMgr extends AppDataManager {

    private static final String tag = "LoadHistoryMgr";

    Array<String> last;

    String splitS = "$%$";

    @Override
    protected void startupLoad(IntelligentLoader l) {

        last = new Array<String>();

        Preferences p = AppDO.I.getPrefs();

        String o = p.getString(FUtills.PrefIdx.lastOpenings, "");

        for (String sub : o.split(splitS)) {
            last.add(sub);
        }


        Gdx.app.debug(tag, "Fetched  " + last.size + " loads");
    }

    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {

    }

    public void reportOpening(String o) {

        if (!last.contains(o, false))
            last.insert(0, o);

        if (last.size > 10) last.removeRange(10, last.size - 1);

    }

    public Array<String> getLast() {
        return last;
    }
}
