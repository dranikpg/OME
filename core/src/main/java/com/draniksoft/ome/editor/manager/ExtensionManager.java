package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.systems.support.ExecutionSystem;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.ut.StepLoader;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.struct.CompoundIterator;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.Iterator;

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

    /*
    	Global getter
     */

    public Iterator<ExtensionDao> getAllDaos() {
	  return new CompoundIterator<ExtensionDao>(mapDaos.values().iterator(),
		    AppDO.I.F().getDaos().values().iterator(),
		    internalDaos.values().iterator());
    }

    public Iterator<Extension> getAll() {
	  return extensions.values().iterator();
    }

    public ObjectMap<String, Extension> getExtensions() {
	  return extensions;
    }

    public boolean hasExtension(String id) {
	  return extensions.containsKey(id);
    }

    public Extension getExt(String id) {
	  return extensions.get(id);
    }

    public ExtensionDao findDao(String id) {
	  if (mapDaos.containsKey(id)) return mapDaos.get(id);
	  if (AppDO.I.F().getDaos().containsKey(id))
		return AppDO.I.F().getDaos().get(id);
	  if (internalDaos.containsKey(id)) return internalDaos.get(id);
	  return null;
    }

    public <T extends SubExtension> T getSub(String id, Class<T> c) {
	  Extension e = getExt(id);
	  if (e == null) return null;
	  return (T) e.map.get(c);
    }

    /*
    	Load
     */

    public boolean loadExtension(final String ID) {
	  if (extensions.containsKey(ID) && !extensions.get(ID).loadable()) {
		Gdx.app.debug(tag, "Already loaded " + ID);
		return false;
	  }

	  final StepLoader l = new StepLoader(world.getSystem(ExecutionSystem.class));
	  l.setListener(new ResponseListener() {
		@Override
		public void onResponse(short code) {
		    Gdx.app.debug(tag, "Extension loaded " + ID);
		    l.dispose();

		    Extension ext = extensions.get(ID);
		    ext.endLoad();

		}
	  });

	  l.reset();
	  return loadExtension(ID, l);
    }

    /*
    	Method for sequences to use as everything goes trough their provider
    	Dont forget to fire some events after loading
     */

    public void loadExtensions(Array<String> IDs, ExecutionProvider provider) {
	  for (String id : IDs) {
		loadExtension(id, provider);
	  }
    }

    /*
    	Load :: closed
     */

    private Extension createExtension(String ID) {
	  Extension ext = new Extension(ID);
	  extensions.put(ID, ext);
	  return ext;
    }


    private boolean loadExtension(String ID, ExecutionProvider provider) {
	  ExtensionDao d = findDao(ID);
	  if (d == null) return false;
	  if (extensions.containsKey(ID) && !extensions.get(ID).loadable()) {
		Gdx.app.debug(tag, "Already loaded " + ID);
		return false;
	  }
	  if (!extensions.containsKey(ID)) {
		createExtension(ID);
	  }
	  Extension ext = extensions.get(ID);
	  ext.load(provider, d, world);
	  return true;
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
