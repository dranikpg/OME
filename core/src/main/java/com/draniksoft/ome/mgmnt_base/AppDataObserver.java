package com.draniksoft.ome.mgmnt_base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.draniksoft.ome.utils.Const;
import com.draniksoft.ome.utils.ResponseListener;
import com.draniksoft.ome.utils.preload.Initable;

public class AppDataObserver implements Initable{

    public static final String tag = "AppDataObserver";
    public static boolean loaded = false;

    private static AppDataObserver i;

    Preferences prefs;
    LanguageManager langMgr;

    private AppDataObserver(){};

    public static AppDataObserver getI(){

        if(i == null){
            i = new AppDataObserver();
        }

        return i;

    }

    // Must be called to use AppData observer properly

    @Override
    public void init(ResponseListener l, boolean t) {

        prefs = Gdx.app.getPreferences(Const.prefsID);
        Gdx.app.debug(tag,"Fetched prefs :: size :: " + prefs.get().size());

        langMgr = new LanguageManager();

        loaded = true;
        l.onResponse(Codes.READY);

    }

    public LanguageManager getLangMgr() {
        return langMgr;
    }

    // shorter function for "nicer" language manager access
    public LanguageManager L() {
        return langMgr;
    }


}
