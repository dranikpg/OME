package com.draniksoft.ome.mgmnt_base.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.draniksoft.ome.mgmnt_base.impl.ConfigManager;
import com.draniksoft.ome.mgmnt_base.impl.LangManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.Const;

import java.util.HashMap;

/*
Class for keeping track of other managers, loading and disposing them
 */
public class AppDO extends AppDataManager {

    public static final String tag = "AppDO";

    public static final AppDO I = new AppDO();

    volatile Preferences prefs;
    HashMap<Class, AppDataManager> m;
    Class<AppDataManager>[] defM = new Class[]{AppDO.class, LangManager.class, ConfigManager.class};

    @Override
    protected void startupLoad(IntelligentLoader l) {

        prefs = Gdx.app.getPreferences(Const.prefsID);

        m = new HashMap<Class, AppDataManager>();

        for (Class<AppDataManager> c : defM) {

            try {
                m.put(c, c.getConstructor().newInstance());
            } catch (Exception e) {
            }

        }

        Gdx.app.debug(tag, "Constructed " + m.size() + " observers");


    }

    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {

    }

    public synchronized Preferences getPrefs() {
        return prefs;
    }

    public <T extends AppDataManager> T getMgr(Class<T> t) {
        return (T) m.get(t);
    }

    public LangManager L() {
        return getMgr(LangManager.class);
    }


}
