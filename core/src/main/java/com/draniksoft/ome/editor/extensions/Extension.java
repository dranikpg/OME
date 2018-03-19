package com.draniksoft.ome.editor.extensions;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;
import com.draniksoft.ome.editor.extensions.t.ExtensionState;
import com.draniksoft.ome.editor.extensions.t.ExtensionType;
import com.draniksoft.ome.editor.extensions.t.ReducedExtensionType;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;

public class Extension {

    private static final String tag = "Extension";

    public Extension(String ID) {
	  this.ID = ID;
	  t = ExtensionType.UNRESOLVED;
	  map = new ObjectMap<Class, SubExtension>();
    }

    protected World w;

    public final String ID;

    public ExtensionType t;
    public ExtensionState s = ExtensionState.NONE;

    public ExtensionDao dao;

    public ObjectMap<Class, SubExtension> map;

    /*
    	Basic load
     */

    public boolean loadable() {
	  return s == ExtensionState.NONE || t == ExtensionType.UNRESOLVED;
    }

    public void load(ExecutionProvider provider, ExtensionDao dao, World w) {
	  this.w = w;
	  this.dao = dao;

	  Gdx.app.debug(tag, "Loading " + ID);

	  t = dao.t.getT();
	  s = ExtensionState.LOADING;

	  for (SubExtensionDao sbd : dao.daos.iterator()) {
		sbd.inject(this);
	  }

	  Gdx.app.debug(tag, "Sub extension map: " + map.toString());

	  for (SubExtension sext : map.values().iterator()) {
		sext.extension = this;
		sext.load(provider, w);
	  }

    }

    public void endLoad() {
	  s = ExtensionState.WORKING;
	  Gdx.app.debug(tag, "Loaded " + ID);
    }

    public void unload(ExecutionProvider p) {

    }

    public void save(ExtensionExporter exp) {

	  Gdx.app.debug(tag, "Saving extension " + ID);

	  dao.t = ReducedExtensionType.to(t);
	  for (SubExtension ext : map.values()) {
		ext.export(exp);
	  }
	  dao.save(exp.mainIndex());

	  String jsonOut = exp.mainIndex().prettyPrint(JsonWriter.OutputType.json, 2);
	  FileHandle h = Gdx.files.absolute(FUtills.uriToPath(dao.URI + "/index.json"));
	  h.writeString(jsonOut, true);

    }

    /*
    	Basic other types
     */

    public void asUnresolved() {
	  t = ExtensionType.UNRESOLVED;
	  s = ExtensionState.WORKING;
    }

    public void asVirtual() {
	  t = ExtensionType.VIRTUAL;
	  s = ExtensionState.WORKING;
    }

    /*
    	Ut
     */

    public <T extends SubExtension> T getSub(Class<T> c) {
	  return (T) map.get(c);
    }




    /* TODO
    public void unload(){
	  s = ExtensionState.UNLOADING;
    }

    public void endUnload(){
        s = ExtensionState.NONE:
    }
    */





}
