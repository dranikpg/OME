package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.utils.FUtills;

public class ExtensionManager extends BaseSystem {

    private static final String tag = "ExtensionManager";

    ObjectMap<String, ExtensionDao> mapDaos;
    ObjectMap<String, ExtensionDao> internalDaos;

    ObjectMap<String, Extension> extensions;

    @Override
    protected void initialize() {
	  mapDaos = new ObjectMap<String, ExtensionDao>();
	  internalDaos = new ObjectMap<String, ExtensionDao>();

	  extensions = new ObjectMap<String, Extension>();
	  parseInternals();
    }

    @Override
    protected void processSystem() {

    }


    private void parseInternals() {
	  FileHandle h = Gdx.files.local("extensions");
	  for (FileHandle f : h.list()) {
		if (f.child("index.json").exists()) {
		    ExtensionDao d = new ExtensionDao();
		    try {
			  d.load(FUtills.r.parse(f.child("index.json").read()));
			  d.URI = FUtills.pathToUri("extensions/" + f.name(), FUtills.STORE_L_INT);
			  internalDaos.put(d.ID, d);
		    } catch (Exception e) {
			  e.printStackTrace();
		    }
		}
	  }
	  Gdx.app.debug(tag, "Found " + internalDaos.size + " internal extensions ");


    }
}
