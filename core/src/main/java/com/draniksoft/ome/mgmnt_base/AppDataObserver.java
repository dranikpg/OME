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
    public void init(ResponseListener l, boolean t) {

        prefs = Gdx.app.getPreferences(Const.prefsID);
        Gdx.app.debug(tag,"Fetched prefs :: size :: " + prefs.get().size());

        langMgr = new LanguageManager();


        initStatics();

        loaded = true;
        l.onResponse(Codes.READY);

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


}
