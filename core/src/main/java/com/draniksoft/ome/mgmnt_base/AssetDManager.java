package com.draniksoft.ome.mgmnt_base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.ResponseListener;
import com.draniksoft.ome.utils.dao.AssetDDao;

import java.io.File;
import java.io.FilenameFilter;

public class AssetDManager extends AppDataManager {

    final static String tag = "AssetDManager";

    String dirP;

    JsonReader jReader;

    private Array<AssetDDao> daos;

    @Override
    public void init(final ResponseListener l, boolean t) {

        Runnable r = new Runnable() {
            @Override
            public void run() {

                jReader = new JsonReader();
                daos = new Array<AssetDDao>();

                Preferences p = AppDataObserver.getI().getPrefs();
                dirP = p.getString(FUtills.PrefIdx.localFolder, "NULL");
                if (dirP.equals("NULL")) return;

                loadAssetD();


                l.onResponse(Codes.READY);

            }

        };

        if (t) {
            new Thread().start();
        } else {
            r.run();
        }

    }

    private void loadAssetD() {

        Gdx.app.debug(tag, "Collecting dependencies on " + dirP);

        daos.clear();
        File file = new File(dirP);
        String[] dirs = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (String locP : dirs) {

            fetchAssetD(locP);

        }

        Gdx.app.debug(tag, "Fetched up to " + daos.size + "  from  " + dirs.length + " assets packs");

    }

    private void fetchAssetD(String fdp) {

        FileHandle f = Gdx.files.absolute(dirP + "/" + fdp + "/" + FUtills.ConstFileN.assetAll + ".json");

        Gdx.app.debug(tag, "Looking for " + f.path() + " " + f.exists());

        if (!f.exists()) return;

        String jsonS = f.readString();

        JsonValue r = jReader.parse(jsonS);
        String name = r.getString("name");
        String id = r.getString("id");

        if (name == null || id == null) return;

        AssetDDao d = new AssetDDao();
        d.name = name;
        d.id = id;
        String resP = dirP + "/" + fdp + "/" + FUtills.ConstFileN.assetAll + ".atlas";
        d.uri = FUtills.toUIRPath(resP, 2);
        d.p = resP;


        daos.add(d);


    }

    public Array<AssetDDao> getDaos() {
        return daos;
    }

    public String getDirP() {
        return dirP;
    }


    @Override
    public void save(ResponseListener l, boolean t) {

        Preferences p = AppDataObserver.getI().getPrefs();

        p.putString(FUtills.PrefIdx.localFolder, dirP);

    }
}
