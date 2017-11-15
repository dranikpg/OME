package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.dao.AssetDDao;

import java.io.File;
import java.util.Iterator;

public class FileManager extends AppDataManager {

    private static String tag = "FileManager";

    String dir;

    File hdirF;
    FileHandle hdirFH;

    File tdir;

    ObjectMap<String, AssetDDao> localDs;

    @Override
    protected void startupLoad(IntelligentLoader l) {

	  dir = AppDO.I.getPrefs().getString(FUtills.PrefIdx.localFolder, "NULL");

	  if (dir == null) {
		l.getLoadLogO().submitCrit("c_err_no_local_dir_n", "c_err_no_local_dir_d");
	  }

	  Gdx.app.debug(tag, "Local dir on " + dir);

	  checkHomeDir();

	  checkTempFolder();

	  indexLocalDs();

	  Gdx.app.debug(tag, "Indexed " + localDs.size + " local daos");


    }

    private void indexLocalDs() {

	  localDs = new ObjectMap<String, AssetDDao>();

	  for (File f : hdirF.listFiles()) {

		if (new File(f.getAbsolutePath() + "/f.atlas").exists()) {

		    indexDao(f);

		}

	  }

    }

    private void indexDao(File f) {


	  JsonValue v = FUtills.r.parse(new FileHandle(new File(f.getAbsolutePath() + "/f.json")));

	  if (!v.has("id")) return;

	  AssetDDao d = new AssetDDao();

	  d.id = v.getString("id");

	  d.uri = FUtills.pathToUri(f.getAbsolutePath(), FUtills.STORE_L_LOC);

	  localDs.put(d.id, d);


    }

    public boolean hasLocalAss(String id) {
	  return localDs.containsKey(id);
    }

    public AssetDDao getLocalAss(String id) {
	  if (localDs.containsKey(id)) {
		return localDs.get(id);
	  }
	  return null;
    }


    public Iterator<AssetDDao> getLocalAssD() {
	  return localDs.values().iterator();
    }

    private void checkHomeDir() {

	  hdirF = new File(dir);

	  if (!hdirF.exists()) {
		Gdx.app.error(tag, "Creating non existing home folder");
		hdirF.mkdirs();
	  }

	  hdirFH = new FileHandle(hdirF);
    }

    private void checkTempFolder() {

	  tdir = new File(hdirF.getAbsolutePath() + "/" + FUtills.LocalFdrNames.tempF);
	  if (!tdir.exists()) {
            Gdx.app.debug(tag, "Creating home folder :: tmp dir ");
            tdir.mkdirs();
        }


    }

    public File getTmpDir() {
        return tdir;
    }

    public File getHomeDir() {
	  return hdirF;
    }

    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {

    }
}
