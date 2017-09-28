package com.draniksoft.ome.editor.systems.file_mgmnt;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.map_load.ProjectLoader;
import com.draniksoft.ome.editor.map_load.ProjectLoaderImpl;
import com.draniksoft.ome.mgmnt_base.AppDataObserver;
import com.draniksoft.ome.utils.ResponseListener;

public class ProjecetLoadSys extends BaseSystem {

    public static String tag = "ProjecetLoadSys";

    @Wire
    MapLoadBundle bundle;

    @Wire
    AssetManager assets;

    ProjectLoader loader;


    ResponseListener myL = new ResponseListener() {
        @Override
        public void onResponse(short code) {


            Gdx.app.debug(tag, "Got code " + code);

            if (code == ProjectLoader.Codes.READY) {
                loader = null;
            }
        }
    };

    @Override
    protected void initialize() {
        load();
    }

    @Override
    protected void processSystem() {

        if(loader != null){
            loader.tick();
        }

    }


    private void load(MapLoadBundle bundle){

        if (bundle.fPath == null) {
            Gdx.app.debug(tag, "Rejecting map load, hence folder path is null");
            return;
        }

        logLoad(bundle.fPath);

        loader = new ProjectLoaderImpl();
        loader.setBundle(bundle);
        loader.setAssetManager(assets);
        loader.setWorld(world);
        Gdx.app.debug(tag, "Starting new map load " + bundle.fPath);

        loader.load(myL);

    }

    private void logLoad(String p) {

        AppDataObserver.getI().getOpngHisM().reportOpening(p);

    }


    private void save(MapLoadBundle bundle) {
        if (bundle.fPath == null) {
            Gdx.app.debug(tag, "Rejecting map load, hence folder path is null");
            return;
        }

        loader = new ProjectLoaderImpl();
        loader.setBundle(bundle);
        loader.setAssetManager(assets);
        loader.setWorld(world);
        Gdx.app.debug(tag, "Starting new save load " + bundle.fPath);

        loader.save(myL);
    }

    public void setBundle(MapLoadBundle bundle) {
        this.bundle = bundle;
    }

    public MapLoadBundle getBundle() {
        return bundle;
    }

    public void load(){if(bundle!=null)load(bundle);}

    public void save() {
        if (bundle != null) save(bundle);
    }

}
