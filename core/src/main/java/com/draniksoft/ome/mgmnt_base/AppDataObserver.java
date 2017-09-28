package com.draniksoft.ome.mgmnt_base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import com.draniksoft.ome.utils.Const;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.ResponseListener;

import java.nio.IntBuffer;

public class AppDataObserver extends AppDataManager {

    public static final String tag = "AppDataObserver";
    public static boolean loaded = false;

    private static AppDataObserver i;

    Preferences prefs;

    LanguageManager langMgr;
    ConfigManager conifgM;
    AssetDManager assetDM;
    OpeningHistoryManager opngHisM;

    private AppDataObserver() {
    }

    public static AppDataObserver getI(){

        if(i == null){
            i = new AppDataObserver();
        }

        return i;

    }

    // Must be called to use AppData observer properly

    @Override
    public void init(final ResponseListener l, boolean t) {

        Runnable r = new Runnable() {
            @Override
            public void run() {

                prefs = Gdx.app.getPreferences(Const.prefsID);
                Gdx.app.debug(tag, "Fetched prefs :: size :: " + prefs.get().size());

                langMgr = new LanguageManager();
                conifgM = new ConfigManager();
                assetDM = new AssetDManager();
                opngHisM = new OpeningHistoryManager();


                initStatics();
                loaded = true;
                l.onResponse(Codes.READY);


            }
        };

        if (t) {
            new Thread(r).start();
        } else {
            r.run();
        }

    }

    @Override
    public void save(final ResponseListener l, boolean t) {

        langMgr.save(l, false);
        assetDM.save(l, false);
        opngHisM.save(l, false);

        Gdx.app.debug(tag, "Flushing preferences");

        prefs.flush();


    }



    private void initStatics() {

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {

                IntBuffer tsizeB = BufferUtils.newIntBuffer(16);
                Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, tsizeB);
                GUtils.maxTSize = tsizeB.get();

            }
        });
    }

    // shorter function for "nicer" language manager access
    public LanguageManager L() {
        return langMgr;
    }

    public AssetDManager getAssetDM() {
        return assetDM;
    }

    public ConfigManager getConifgM() {
        return conifgM;
    }

    public OpeningHistoryManager getOpngHisM() {
        return opngHisM;
    }

    public synchronized Preferences getPrefs() {
        return prefs;
    }


}
