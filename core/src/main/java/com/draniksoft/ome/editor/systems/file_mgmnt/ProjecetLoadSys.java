package com.draniksoft.ome.editor.systems.file_mgmnt;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class ProjecetLoadSys extends BaseSystem {

    public static String tag = "ProjecetLoadSys";

    public static final int STATE_IDLE = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_SAVING = 3;

    @Wire
    MapLoadBundle bundle;

    @Wire
    AssetManager assM;


    int state = 0;
    ProjectSaver saver;
    ProjectLoader loader;


    @Override
    protected void initialize() {

        saver = new ProjectSaver();
        loader = new ProjectLoader();


        load();


    }

    @Override
    protected void processSystem() {

        if (state == STATE_SAVING) saver.update();
        if (state == STATE_LOADING) loader.update();

    }


    private void load(MapLoadBundle bundle){

        logLoad(bundle.fPath);


    }


    private void save(MapLoadBundle bundle) {

        Gdx.app.debug(tag, "Saving");

        logLoad(bundle.fPath);

        state = STATE_SAVING;

        saver.setWorld(world);
        saver.start(bundle, new ResponseListener() {

            @Override
            public void onResponse(short code) {

                Gdx.app.debug(tag, "Save ready");

                state = STATE_IDLE;

                saver.dispose();

            }
        });

    }


    private void clearAll() {

        IntBag b = world.getAspectSubscriptionManager().get(Aspect.all()).getEntities();

        for (int i = 0; i < b.size(); i++) {

            //ESCUtils.removeSelectionBeforeRMV(b.get(i), world);

            world.delete(b.get(i));

        }

    }

    private void logLoad(String p) {

        AppDO.I.LH().reportOpening(p);

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
