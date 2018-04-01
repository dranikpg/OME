package com.draniksoft.ome.editor.extensions;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.sub.DefaultSubExtensionFktory;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;
import com.draniksoft.ome.editor.extensions.t.ExtensionState;
import com.draniksoft.ome.editor.extensions.t.ExtensionType;
import com.draniksoft.ome.editor.extensions.t.ReducedExtensionType;
import com.draniksoft.ome.editor.support.track.ReferenceTracker;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;

public class Extension implements ReferenceTracker {

    private static final String tag = "Extension";

    public Extension(String ID, ExtensionType t, ExtensionState s) {
	  this.ID = ID;
	  this.t = t;
	  this.s = s;
	  map = new ObjectMap<Class, SubExtension>();
    }

    public Extension(String ID) {
	  this(ID, ExtensionType.UNRESOLVED, ExtensionState.WORKING);
    }

    /*
		  References
	   */

    public World w;

    /*

    */
    public final String ID;
    public ExtensionDao dao;

    /*
    	States
     */

    public ExtensionType t;
    public ExtensionState s = ExtensionState.NONE;

    /*
    	Other
     */

    public ObjectMap<Class, SubExtension> map;
    int references = 0;


    /*
    	Components
     */

    public boolean hasSub(Class c) {
	  return map.containsKey(c);
    }

    public <T extends SubExtension> T getSub(Class<T> c) {
	  SubExtension e = map.get(c);
	  if (e != null) return (T) e;

	  if (isAdditive()) {
		if (DefaultSubExtensionFktory.MAP.containsKey(c)) {
		    try {
			  map.put(c, DefaultSubExtensionFktory.MAP.get(c).getConstructor().newInstance());
			  return getSub(c);
		    } catch (Exception exc) {
			  Gdx.app.error(tag, "", exc);
			  return null;
		    }
		}
	  }
	  return null;
    }

    public void inject(Class c, SubExtension e) {
	  map.put(c, e);
    }

    /*
    	Basic load & unload
     */

    public boolean loadable() {
	  return s == ExtensionState.NONE || t == ExtensionType.UNRESOLVED;
    }

    public void load(ExecutionProvider provider, ExtensionDao dao) {
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
		sext.load(provider);
	  }

    }

    public void endLoad() {
	  s = ExtensionState.WORKING;
	  Gdx.app.debug(tag, "Loaded " + ID);
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
    	reference
     */


    @Override
    public int references() {
	  return references;
    }

    @Override
    public int reference(int delta) {
	  references += delta;
	  return 0;
    }

    /*
    	Helper
     */

    //
    public boolean isAdditive() {
	  return t == ExtensionType.UNRESOLVED || s == ExtensionState.LOADING;
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
