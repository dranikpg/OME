package com.draniksoft.ome.editor.load;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.draniksoft.ome.editor.manager.*;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ProjectSaver {

    private static final String tag = "ProjectSaver";



    IntelligentLoader l;

    MapLoadBundle b;
    ResponseListener lst;


    volatile JsonValue indexV;


    World w;


    public enum State {
        IDLE, INDEX_JSON, MGR_RUN, JSON_FLUSH, END_PTR
    }


    State s = State.IDLE;

    public void setWorld(World w) {
        this.w = w;
    }

    public void start(MapLoadBundle bundle, ResponseListener responseListener) {

        if (w == null) notifyFail();

        l = new IntelligentLoader();

        this.b = bundle;
        this.lst = responseListener;

        l.setCompletionListener(new ResponseListener() {
            @Override
            public void onResponse(short code) {
                if (code != IntelligentLoader.LOAD_FAILED) {
                    updateState();
                } else {
                    notifyFail();
                }
            }
        });


        updateState();

        l.start();

    }

    private void updateState() {
        s = State.values()[s.ordinal() + 1];

        Gdx.app.debug(tag, s.toString());

        if (s == State.INDEX_JSON) {
            l.passRunnable(new JsonL());
        } else if (s == State.MGR_RUN) {
            l.passRunnable(new SaveT(w.getSystem(ProjectMgr.class)));
            l.passRunnable(new SaveT(w.getSystem(MapMgr.class)));
            l.passRunnable(new SaveT(w.getSystem(DrawableMgr.class)));
            l.passRunnable(new SaveT(w.getSystem(TimeMgr.class)));
            l.passRunnable(new SaveT(w.getSystem(EntitySrzMgr.class)));
        } else if (s == State.JSON_FLUSH) {
            l.passRunnable(new JsonFlusher());
        }
        if (s == State.END_PTR) {
            notifyEnd();
        }
    }

    public JsonValue getIndexV() {
        return indexV;
    }

    private void notifyFail() {
    }


    private void notifyEnd() {

        lst.onResponse((short) ResponseCode.SUCCESSFUL);

    }

    public void update() {
        l.update();
    }

    private class JsonFlusher implements IRunnable {

        @Override
        public void run(IntelligentLoader l) {

            String js = indexV.prettyPrint(JsonWriter.OutputType.json, 2);

            FileHandle h = Gdx.files.absolute(AppDO.I.F().getTmpDir().getAbsolutePath() + "/index.json");
            if (!h.exists()) try {
                h.file().createNewFile();
                OutputStream s = h.write(false);
                s.write(js.getBytes());
                s.close();
            } catch (Exception e) {
                Gdx.app.error(tag, "", e);
            }


        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

    private class SaveT implements IRunnable {


        public SaveT(LoadSaveManager mgr) {
            this.mgr = mgr;
        }

        LoadSaveManager mgr;


        @Override
        public void run(IntelligentLoader l) {

            mgr.save(l, ProjectSaver.this);

        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

    private class JsonL implements IRunnable {

        JsonReader r;

        @Override
        public void run(IntelligentLoader l) {

            Gdx.app.debug(tag, "Json index saver");

            r = new JsonReader();

            File indexF = new File(AppDO.I.F().getTmpDir().getAbsolutePath() + "/index.json");

            if (!indexF.exists()) {
                Gdx.app.debug(tag, "Creating missing index.json file");
                try {
                    indexF.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            indexV = new JsonValue(JsonValue.ValueType.object);

        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

    public MapLoadBundle getB() {
        return b;
    }


    public void dispose() {

        l.terminate();

        l = null;
        indexV = null;
    }
}
