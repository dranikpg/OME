package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.load.IntelligentLoader;

import java.util.Locale;

public class LangManager extends AppDataManager {

    private static final String tag = "LangManager";

    I18NBundle b;

    boolean enL = true;

    @Override
    protected void startupLoad(IntelligentLoader l) {

        FileHandle baseFileHandle = Gdx.files.internal("lang/bundle");
        Locale locale = new Locale("en");
        b = I18NBundle.createBundle(baseFileHandle, locale);

        testLang();

    }

    private void testLang() {

        Gdx.app.log(tag, b.format("lang_tst", " [%Format::pass] "));

    }

    public String get(String v) {
        return b.get(v);
    }

    public String format(String k, Object... args) {
        return b.format(k, args);
    }

    public boolean isEnL() {
        return enL;
    }

    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {

    }
}
