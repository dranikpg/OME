package com.draniksoft.ome.editor.extensions;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;
import com.draniksoft.ome.editor.extensions.t.ExtensionState;
import com.draniksoft.ome.editor.extensions.t.ExtensionType;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;

public class Extension {

    private static final String tag = "Extension";

    public Extension(String ID) {
	  this.ID = ID;
	  map = new ObjectMap<Class, SubExtension>();
    }

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
	  this.dao = dao;

	  Gdx.app.debug(tag, "Loading " + ID);

	  t = dao.smz ? ExtensionType.SYSTEM : ExtensionType.BASIC;
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
