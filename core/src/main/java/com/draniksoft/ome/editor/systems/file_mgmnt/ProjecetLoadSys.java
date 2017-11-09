package com.draniksoft.ome.editor.systems.file_mgmnt;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.editor.load.MapLoadBundle;

public class ProjecetLoadSys extends BaseSystem {

    public static String tag = "ProjecetLoadSys";

    @Wire
    MapLoadBundle bundle;

    @Wire
    AssetManager assets;



    @Override
    protected void initialize() {
        load();
    }

    @Override
    protected void processSystem() {

    }


    private void load(MapLoadBundle bundle){


    }

    private void clearAll() {

        IntBag b = world.getAspectSubscriptionManager().get(Aspect.all()).getEntities();

        for (int i = 0; i < b.size(); i++) {

            //ESCUtils.removeSelectionBeforeRMV(b.get(i), world);

            world.delete(b.get(i));

        }

    }

    private void logLoad(String p) {


    }


    private void save(MapLoadBundle bundle) {

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
