package com.draniksoft.ome.editor.load;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.draniksoft.ome.editor.manager.MapMgr;
import com.draniksoft.ome.editor.manager.ProjectMgr;
import com.draniksoft.ome.editor.manager.SerializationManager;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.systems.support.ExecutionSystem;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.ut.StepLoader;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;

public class ProjectSaver {

    private static final String tag = "ProjectSaver";


    StepLoader l;
    ExecutionProvider pvd;


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

        this.b = bundle;
        this.lst = responseListener;

	  if (w == null) notifyFail();

	  l = new StepLoader(w.getSystem(ExecutionSystem.class),
		    new ResponseListener() {
			  @Override
			  public void onResponse(short code) {
				if (code != ResponseCode.FAILED) {
				    updateState();
				} else {
				    notifyFail();
				}
			  }
		    });

        updateState();
    }

    private void updateState() {
        s = State.values()[s.ordinal() + 1];

	  l.reset();


	  Gdx.app.debug(tag, s.toString());

        if (s == State.INDEX_JSON) {
		l.exec(new JsonL());
	  } else if (s == State.MGR_RUN) {
		l.exec(new SaveT(w.getSystem(ProjectMgr.class)));
		l.exec(new SaveT(w.getSystem(MapMgr.class)));
		l.exec(new SaveT(w.getSystem(TimeMgr.class)));
		l.exec(new SaveT(w.getSystem(SerializationManager.class)));
	  } else if (s == State.JSON_FLUSH) {
		l.exec(new JsonFlusher());
	  }
        if (s == State.END_PTR) {
            notifyEnd();
        }
    }

    public JsonValue getIndexV() {
        return indexV;
    }

    private void notifyFail() {

	  Gdx.app.error(tag, "Save ERROR RESPONSE on<" + s.toString());

	  dispose();

    }


    private void notifyEnd() {

	  lst.onResponse(ResponseCode.SUCCESSFUL);

    }

    public void update() {
    }

    private class JsonFlusher implements Callable<Void> {

	  @Override
	  public Void call() throws Exception {
		Gdx.app.debug(tag, "Flushing indexed JSON");

		String js = "";
		if (Env.PRETTY_JSON) {
		    js = indexV.prettyPrint(JsonWriter.OutputType.json, 5);
		} else {
		    js = indexV.prettyPrint(JsonWriter.OutputType.minimal, 5);
		}

		if (Env.B64D_JS) {
		    js = Base64Coder.encodeString(js, true);
		}

		Gdx.app.debug(tag, js);

		FileHandle h = Gdx.files.absolute(AppDO.I.F().getTmpDir().path() + "/index.json");
		if (!h.exists()) try {
		    h.file().createNewFile();
		} catch (Exception e) {
		    Gdx.app.error(tag, "", e);
		}

		try {
		    OutputStream s = h.write(false);
		    s.write(js.getBytes());
		    s.close();
		} catch (Exception e) {
		    Gdx.app.error(tag, "", e);
		}
		return null;
	  }
    }

    private class SaveT implements Callable<Void> {


        public SaveT(LoadSaveManager mgr) {
            this.mgr = mgr;
        }

        LoadSaveManager mgr;

	  @Override
	  public Void call() throws Exception {

		mgr.save(ProjectSaver.this);
		return null;
	  }
    }

    private class JsonL implements Callable<Void> {

        JsonReader r;

	  @Override
	  public Void call() throws Exception {
		Gdx.app.debug(tag, "Json index saver");

		r = FUtills.r;

		File indexF = new File(AppDO.I.F().getTmpDir().path() + "/index.json");

		if (!indexF.exists()) {
		    Gdx.app.debug(tag, "Creating missing index.json file");
		    try {
			  indexF.createNewFile();
		    } catch (IOException e) {
			  e.printStackTrace();
		    }
		}

		indexV = new JsonValue(JsonValue.ValueType.object);
		return null;
	  }
    }

    public MapLoadBundle getB() {
        return b;
    }


    public void dispose() {
	  l.dispose();
	  l = null;
        indexV = null;
    }
}
