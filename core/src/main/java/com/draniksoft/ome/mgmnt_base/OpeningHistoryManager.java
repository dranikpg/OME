package com.draniksoft.ome.mgmnt_base;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.ResponseListener;

public class OpeningHistoryManager extends AppDataManager {

    private static final String tag = "OpeningHistoryManager";

    Array<String> lastOpAr;

    @Override
    public void init(ResponseListener l, boolean t) {

        Preferences p = AppDataObserver.getI().getPrefs();

        lastOpAr = new Array<String>(p.getString(FUtills.PrefIdx.lastOpenings).split(";"));


    }

    public void reportOpening(String p) {

        if (lastOpAr.contains(p, false)) return;

        lastOpAr.add(p);


    }

    public Array<String> getLastOpAr() {
        return lastOpAr;
    }

    @Override
    public void save(ResponseListener l, boolean t) {

        Preferences p = AppDataObserver.getI().getPrefs();

        p.putString(FUtills.PrefIdx.lastOpenings, getLastOpStr());

    }

    public String getLastOpStr() {

        String r = "";

        for (String s : lastOpAr) {

            r = r.concat(s).concat(";");

        }

        return r;
    }
}
