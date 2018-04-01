package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;

import java.io.File;

public class FileManager extends AppDataManager {

    private static String tag = "FileManager";

    String dir;
    FileHandle homeDirFH;

    FileHandle tmpDirFH;
    FileHandle extDirFH;

    ObjectMap<String, ExtensionDao> daos;

    @Override
    protected void startupLoad(IntelligentLoader l) {
	  dir = AppDO.I.getPrefs().getString(FUtills.PrefIdx.localFolder, "NULL");
	  if (dir == null) {
		l.getLoadLogO().submitCrit("c_err_no_local_dir_n", "c_err_no_local_dir_d");
	  }

	  Gdx.app.debug(tag, "Local dir on " + dir);

	  checkHomeDir();

	  checkSubDirs();

	  indexLocalExt();
    }

    private void indexLocalExt() {
	  daos = new ObjectMap<String, ExtensionDao>();
	  for (FileHandle f : extDirFH.list()) {
		if (f.child("index.json").exists()) {
		    ExtensionDao d = new ExtensionDao();
		    try {
			  d.load(FUtills.r.parse(f.child("index.json").read()));
			  d.URI = FUtills.pathToUri("extensions/" + f.name(), FUtills.STORE_L_LOC);
			  daos.put(d.ID, d);
		    } catch (Exception e) {
			  e.printStackTrace();
		    }
		}
	  }
	  Gdx.app.debug(tag, "Collected " + daos.size + " extensions");
    }

    public ObjectMap<String, ExtensionDao> getDaos() {
	  return daos;
    }

    private void checkHomeDir() {
	  File homeDir = new File(dir);
	  if (!homeDir.exists()) {
		Gdx.app.error(tag, "Creating non existing home folder");
		homeDir.mkdirs();
	  }
	  homeDirFH = new FileHandle(homeDir);
    }

    private void checkSubDirs() {

	  tmpDirFH = homeDirFH.child(FUtills.LocalFdrNames.tempF);
	  if (!tmpDirFH.exists()) tmpDirFH.mkdirs();

	  extDirFH = homeDirFH.child("extensions");
	  if (!extDirFH.exists()) extDirFH.mkdirs();

    }

    public FileHandle getTmpDir() {
	  return tmpDirFH;
    }

    public FileHandle getHomeDir() {
	  return homeDirFH;
    }

    public FileHandle getExtDir() {
	  return extDirFH;
    }

    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {

    }
}
