package com.draniksoft.ome.support.load.observer;

import com.badlogic.gdx.utils.Array;

public class LoadLogObserver {

    Array<LoadLogInfo> warnings;

    LoadLogInfo crit;

    public LoadLogObserver() {
        warnings = new Array<LoadLogInfo>();
    }

    public void submitWarning(LoadLogInfo i) {
        warnings.add(i);
    }

    public void submitWarning(String kname, String kdesc) {
        warnings.add(new BundleLoadLogInfo(kname, kdesc));
    }

    public void submitCrit(String kname, String kdesc) {
        crit = new BundleLoadLogInfo(kname, kdesc);
    }

    public boolean critState() {
        return crit != null;
    }


    public LoadLogInfo getCrit() {
        return crit;
    }

    public Array<LoadLogInfo> getWarnings() {
        return warnings;
    }
}
