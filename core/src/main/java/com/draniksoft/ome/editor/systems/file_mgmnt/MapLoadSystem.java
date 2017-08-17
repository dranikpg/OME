package com.draniksoft.ome.editor.systems.file_mgmnt;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.map_load.MapLoader;
import com.draniksoft.ome.editor.map_load.MapLoaderImpl;
import com.draniksoft.ome.utils.ResponseListener;

public class MapLoadSystem extends BaseSystem{

    public static String tag = "MapLoadSystem";

    @Wire
    MapLoadBundle bundle;

    @Wire
    AssetManager assets;

    MapLoader loader;


    ResponseListener myL = new ResponseListener() {
        @Override
        public void onResponse(short code) {

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

        if(bundle.indexP == null){
            Gdx.app.debug(tag,"Rejecting map load, hence indexP is null");
            return;
        }

        loader = new MapLoaderImpl();
        loader.setBundle(bundle);
        loader.setManager(assets);
        loader.setWorld(world);
        Gdx.app.debug(tag,"Starting new map load " + bundle.indexP);

        loader.load(myL);

    }

    public void setBundle(MapLoadBundle bundle) {
        this.bundle = bundle;
    }

    public void load(){if(bundle!=null)load(bundle);}


}
