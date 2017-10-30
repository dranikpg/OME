package com.draniksoft.ome.editor.support.map_load;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.*;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.manager.EntitySrzMgr;
import com.draniksoft.ome.editor.manager.MapMgr;
import com.draniksoft.ome.editor.manager.ProjectMgr;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.struct.ResponseListener;
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

            String instr = Gdx.files.absolute(bundle.fPath + "/index.json").readString();

            if (!(instr.startsWith("[") || instr.startsWith("{"))) {
                Gdx.app.debug(tag, "Decoding Istream :: _64?");
                instr = Base64Coder.decodeString(instr);
            }

            rootNode = jsonR.parse(instr);

            Gdx.app.debug(tag, "Fetched index file to root node");

            for (LoadSaveManager mgr : mgrs) {

                Gdx.app.debug(tag, "Processing " + mgr.getClass().getSimpleName());

                if (mgr.getNode() != null) {

                    mgr.loadL(rootNode.get(mgr.getNode()), _this);

                } else {

                    mgr.loadL(null, _this);

                }

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


                }


            }

            OutputStream s = Gdx.files.absolute(bundle.fPath + "/index.json").write(false);
            try {
                String outS;
                if (Env.DEBUG)
                    outS = r.prettyPrint(JsonWriter.OutputType.json, 2);
                else
                    outS = r.toJson(JsonWriter.OutputType.minimal);

                if (Env.B64D)
                    outS = Base64Coder.encodeString(outS);


                s.write(outS.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                world.getSystem(ProjectMgr.class),
                world.getSystem(MapMgr.class),
                world.getSystem(DrawableMgr.class),
                world.getSystem(EntitySrzMgr.class)
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
