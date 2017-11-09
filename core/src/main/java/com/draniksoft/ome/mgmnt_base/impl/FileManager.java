package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;

import java.io.File;

public class FileManager extends AppDataManager {

    private static String tag = "FileManager";

    String dir;

    File dirF;
    FileHandle dirFH;

    File tdir;

    @Override
    protected void startupLoad(IntelligentLoader l) {

        dir = AppDO.I.getPrefs().getString(FUtills.PrefIdx.localFolder, "NULL");

        if (dir == null) {
            l.getLoadLogO().submitCrit("c_err_no_local_dir_n", "c_err_no_local_dir_d");
        }

        Gdx.app.debug(tag, "Local dir on " + dir);

        checkHomeDir();

        checkTempFolder();


    }

    private void checkHomeDir() {

        dirF = new File(dir);

        if (!dirF.exists()) {
            Gdx.app.error(tag, "Creating non existing home folder");
            dirF.mkdirs();
        }

        dirFH = new FileHandle(dirF);
    }

    private void checkTempFolder() {

        tdir = new File(dirF.getAbsolutePath() + "/" + FUtills.LocalFdrNames.tempF);
        if (!tdir.exists()) {
            Gdx.app.debug(tag, "Creating home folder :: tmp dir ");
            tdir.mkdirs();
        }


    }

    public File getTmpDir() {
        return tdir;
    }

    public File getHomeDir() {
        return dirF;
    }

    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {

    }
}
