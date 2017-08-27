package com.draniksoft.ome.editor.map_load;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.*;
import com.draniksoft.ome.editor.manager.MapManager;
import com.draniksoft.ome.editor.manager.ProjectManager;
import com.draniksoft.ome.utils.ResponseListener;
import com.draniksoft.ome.utils.struct.Pair;

import java.io.IOException;
import java.io.OutputStream;

public class ProjectLoaderImpl extends ProjectLoader {

    public static final String tag = ProjectLoaderImpl.class.getSimpleName();

    ProjectLoader _this;

    /**
     * Managers
     */

    LoadSaveManager mgrs[];

    /**
     *
     *
     */

    Json json;
    JsonReader jsonR;

    JsonValue rootNode;

    public ProjectLoaderImpl() {
        _this = this;
    }

    /**
     *
     *
     *
     */



    Thread myT;
    public class LoadR implements Runnable {
        /**
         * Function should return true if the runnable should stop
         *
         */
        @Override
        public void run() {

            errors = new Array<ErrorInfo>();
            json = new Json();
            jsonR = new JsonReader();

            initMgrs();

            rootNode = jsonR.parse(Gdx.files.absolute(bundle.fPath + "/index.json").readString());

            Gdx.app.debug(tag, "Fetched index file to root node");

            for (LoadSaveManager mgr : mgrs) {

                Gdx.app.debug(tag, "Processing " + mgr.getClass().getSimpleName());

                mgr.loadL(rootNode.get(mgr.getNode()), _this);

                if (crashed) return;

            }

            Gdx.app.debug(tag, "Ready for asset manager callback");

            mgrR = true;


        }

    }

    public class SaveR implements Runnable {
        @Override
        public void run() {

            errors = new Array<ErrorInfo>();
            json = new Json();

            initMgrs();

            JsonValue r = new JsonValue(JsonValue.ValueType.object);

            for (int i = 0; i < mgrs.length; i++) {

                Pair<String, JsonValue> v = mgrs[i].save();

                if (v != null) {

                    r.addChild(v.getElement0(), v.getElement1());

                    Gdx.app.debug(tag, "Adding " + v.getElement1().prettyPrint(JsonWriter.OutputType.json, 2) + "   for " + mgrs[i].getClass().getSimpleName());

                }


            }


            OutputStream s = Gdx.files.absolute(bundle.fPath + "/index.json").write(false);
            try {
                s.write(r.prettyPrint(JsonWriter.OutputType.json, 2).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gdx.app.debug(tag, "Index json " + r.prettyPrint(JsonWriter.OutputType.json, 2));

            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            rootL.onResponse(Codes.READY);


        }
    }

    private void initMgrs() {

        mgrs = new LoadSaveManager[]{
                world.getSystem(ProjectManager.class),
                world.getSystem(MapManager.class)
        };

        Gdx.app.debug(tag, "Collected managers");

    }

    /**
     * Inner tags
     */

    boolean mgrR = false;
    boolean gfxProcessing = false;

    int i = 0;
    boolean loadMode;

    public void tick() {

        if (!loadMode) {
            return;
        }

        if (gfxProcessing) {

            if (i < mgrs.length) {

                if (mgrs[i].loadG(_this)) i++;

            } else {

                rootL.onResponse(Codes.READY);

            }

        } else if (assetManager.update() && mgrR) {
            gfxProcessing = true;
            mgrR = false;
        }

    }

    @Override
    public boolean load(ResponseListener rootL) {

        if (bundle.fPath == null) {
            rootL.onResponse(Codes.CANCELLED);
            return false;
        }

        this.rootL = rootL;

        myT = new Thread(new LoadR());
        myT.setName("MapLoadThread");
        myT.start();

        loadMode = true;

        return true;
    }

    @Override
    public boolean save(ResponseListener l) {

        if (bundle.fPath == null) {
            rootL.onResponse(Codes.CANCELLED);
            return false;
        }

        this.rootL = l;

        myT = new Thread(new SaveR());
        myT.setName("MapLoadThread");
        myT.start();

        loadMode = false;

        return true;
    }


}
