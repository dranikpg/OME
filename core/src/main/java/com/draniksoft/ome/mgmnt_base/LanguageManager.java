package com.draniksoft.ome.mgmnt_base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.draniksoft.ome.utils.ResponseListener;

public class LanguageManager extends AppDataManager {

    public static final String tag = "LanguageManager";

    I18NBundle menuB;

    @Override
    public void init(final ResponseListener l, boolean t) {

        Runnable r = new Runnable() {
            @Override
            public void run() {

                try{

                    FileHandle baseFileHandle = Gdx.files.internal("menu/lang/bundle");
                    menuB = I18NBundle.createBundle(baseFileHandle);


                    runTest();

                    l.onResponse(Codes.READY);

                }catch (Exception e){

                    l.onResponse(Codes.ERROR);

                }
            }

        };

        if(t){
            new Thread(r).start();
        }else {
            r.run();
        }

    }

    private void runTest() {

        Gdx.app.debug(tag,format_m("lang_tst"));

    }

    //
    public String format_m(String key, Object... ags){
        return menuB.format(key,ags);
    }

    @Override
    public void save(ResponseListener l, boolean t) {

    }
}
