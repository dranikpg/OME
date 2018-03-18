package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.map.MapExtensionDao;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.systems.support.ExecutionSystem;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.ut.StepLoader;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.struct.CompoundIterator;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.Iterator;

public class ExtensionManager extends BaseSystem implements LoadSaveManager {
    private static final String tag = "ExtensionManager";

    ObjectMap<String, ExtensionDao> mapDaos;
    ObjectMap<String, ExtensionDao> internalDaos;

    ObjectMap<String, Extension> extensions;

    Extension mapExtension;

    @Wire(name = "engine_l")
    ExecutionProvider stpL;

    @Override
    protected void initialize() {
	  mapDaos = new ObjectMap<String, ExtensionDao>();
	  internalDaos = new ObjectMap<String, ExtensionDao>();

	  extensions = new ObjectMap<String, Extension>();
	  parseInternalDaos();
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
	  if (id == null || id == "") return mapExtension;
	  return extensions.get(id);
    }

    public Extension mapExt() {
	  return mapExtension;
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

	  ExtensionDao d = findDao(ID);
	  if (d == null) return false;
	  return loadExtensionWParent(d, world.getSystem(ExecutionSystem.class));
    }


    public boolean loadExtensionWParent(final ExtensionDao d, ExecutionProvider parentExec) {

	  final StepLoader l = new StepLoader(parentExec);
	  l.setListener(new ResponseListener() {
		@Override
		public void onResponse(short code) {
		    Gdx.app.debug(tag, "Extension loaded " + d.ID);
		    l.dispose();

		    Extension ext = extensions.get(d.ID);
		    ext.endLoad();

		}
	  });

	  l.reset();
	  return loadExtension(d, l);
    }

    /*
    	Load :: closed
     */

    private Extension createExtension(String ID) {
	  Extension ext = new Extension(ID);
	  extensions.put(ID, ext);
	  return ext;
    }

    private boolean loadExtension(ExtensionDao dao, ExecutionProvider provider) {
	  if (extensions.containsKey(dao.ID) && !extensions.get(dao.ID).loadable()) {
		Gdx.app.debug(tag, "Already loaded " + dao.ID);
		return false;
	  }
	  if (!extensions.containsKey(dao.ID)) {
		createExtension(dao.ID);
	  }
	  Extension ext = extensions.get(dao.ID);
	  ext.load(provider, dao, world);
	  return true;
    }


    @Override
    public void save(ProjectSaver s) {

    }

    @Override
    public void load(ProjectLoader ld) {
	  parseMapDaos();

	  MapExtensionDao mapDao = fetchMapExtDao();

	  mapExtension = new Extension(null);
	  mapExtension.load(ld, mapDao, world);


    }

    public MapExtensionDao fetchMapExtDao() {

	  MapExtensionDao d = new MapExtensionDao();

	  FileHandle indexFH = Gdx.files.absolute(FUtills.uriToPath(FUtills.STORE_L_MAP, "mapext/index.json"));
	  if (!indexFH.exists()) return null;

	  String in = indexFH.readString();
	  JsonValue root = FUtills.r().parse(in);

	  d.URI = FUtills.pathToUri("mapext", FUtills.STORE_L_MAP);

	  try {
		d.load(root);
	  } catch (Exception e) {
		Gdx.app.debug(tag, "Map extension parse ", e);
	  }

	  Gdx.app.debug(tag, "Map extension dao " + d.daos.toString());

	  return d;

    }

    private void parseMapDaos() {

	  FileHandle fH = Gdx.files.absolute(FUtills.uriToPath(FUtills.STORE_L_MAP, "ext"));

	  if (!fH.exists()) return;

	  for (FileHandle fc : fH.list()) {
		if (fc.child("index.json").exists()) {
		    ExtensionDao d = new ExtensionDao();

		    String indexStr = fc.child("index.json").readString();
		    JsonValue jroot = FUtills.r().parse(indexStr);

		    d.URI = FUtills.pathToUri("ext/" + fc.name(), FUtills.STORE_L_MAP);

		    try {
			  d.load(jroot);
		    } catch (Exception e) {
			  e.printStackTrace();
		    }

		    mapDaos.put(d.ID, d);

		}
	  }

    }

    private void parseInternalDaos() {
	  FileHandle h = Gdx.files.internal("assets/extensions");
	  for (FileHandle f : h.list()) {
		if (f.child("index.json").exists()) {
		    ExtensionDao d = new ExtensionDao();
		    try {
			  d.load(FUtills.r.parse(f.child("index.json").read()));
			  d.URI = FUtills.pathToUri("extensions/" + f.name(), FUtills.STORE_L_INT);
			  if (d.stpLoad) {
				loadExtensionWParent(d, stpL);
			  }
			  internalDaos.put(d.ID, d);
		    } catch (Exception e) {
			  e.printStackTrace();
		    }
		}
	  }
	  Gdx.app.debug(tag, "Found " + internalDaos.size + " internal extensions ");
    }
}
