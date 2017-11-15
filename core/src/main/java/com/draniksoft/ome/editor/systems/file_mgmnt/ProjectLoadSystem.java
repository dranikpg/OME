package com.draniksoft.ome.editor.systems.file_mgmnt;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class ProjectLoadSystem extends BaseSystem {

    public static String tag = "ProjectLoadSystem";

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


	  //load();


    }

    @Override
    protected void processSystem() {

        if (state == STATE_SAVING) saver.update();
        if (state == STATE_LOADING) loader.update();

    }


    private void load(MapLoadBundle bundle){


	  Gdx.app.debug(tag, "Loading");

        logLoad(bundle.fPath);

	  state = STATE_LOADING;

	  loader.setW(world);
	  loader.start(bundle, new ResponseListener() {
		@Override
		public void onResponse(short code) {
		    Gdx.app.debug(tag, "Load ready");

		    state = STATE_IDLE;

		    loader.dispose();
		}
	  });



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
