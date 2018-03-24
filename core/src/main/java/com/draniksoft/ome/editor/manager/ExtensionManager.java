package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.dependence.DependentExtensionLoader;
import com.draniksoft.ome.editor.extensions.map.MapExtensionDao;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDaoState;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.extensions.t.ExtensionState;
import com.draniksoft.ome.editor.extensions.t.ExtensionType;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.systems.support.ExecutionSystem;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.struct.CompoundIterator;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.Iterator;

public class ExtensionManager extends BaseSystem implements LoadSaveManager {
    private static final String tag = "ExtensionManager";

    ObjectMap<String, ExtensionDao> mapDaos;
    ObjectMap<String, ExtensionDao> internalDaos;

    ObjectMap<String, Extension> extensions;

    Array<ObjectMap<String, ExtensionDao>> daoArray;

    Extension mapExtension;

    @Wire(name = "engine_l")
    ExecutionProvider stpL;

    @Override
    protected void initialize() {
	  mapDaos = new ObjectMap<String, ExtensionDao>();
	  internalDaos = new ObjectMap<String, ExtensionDao>();

	  daoArray = new Array<ObjectMap<String, ExtensionDao>>(true, 3);
	  daoArray.addAll(
		    internalDaos,
		    AppDO.I.F().getDaos(),
		    mapDaos);

	  Gdx.app.debug(tag, daoArray.toString());

	  extensions = new ObjectMap<String, Extension>();
	  parseInternalDaos();


    }

    @Override
    protected void processSystem() {

    }


    /*
    	Load utils
     */

    public void loadExtensions(String... ids) {

	  loadExtensions(world.getSystem(ExecutionSystem.class), ids);

    }

    public void loadExtensions(ExecutionProvider pv, String... ar) {
	  loadExtensions(pv, new Array<String>(ar));
    }

    public void loadExtensions(ExecutionProvider pv, Array<String> ar) {

	  DependentExtensionLoader l = new DependentExtensionLoader(this);

	  l.load(pv, ar, new ResponseListener() {
		@Override
		public void onResponse(short code) {

		}
	  });

    }

    /*
    	Load
     */

    public Extension createExtension(String ID, ExtensionType t, ExtensionState s) {
	  Gdx.app.debug(tag, "Creating extension " + ID);
	  Extension ext = new Extension(ID, t, s);
	  ext.w = world;
	  extensions.put(ID, ext);
	  return ext;
    }

    public Extension rawLoadExtension(ExtensionDao dao, ExecutionProvider provider) {
	  Extension ext = extensions.get(dao.ID);
	  ext.load(provider, dao);
	  return ext;
    }

    public void endExtensionLoad(Extension e) {
	  e.endLoad();
	  ExtensionDao d;

	  for (ObjectMap<String, ExtensionDao> mp : daoArray) {
		d = mp.get(e.ID);
		if (d == null) continue;
		if (d == e.dao) d.state = ExtensionDaoState.APPLICATED;
		else d.state = ExtensionDaoState.SEMI_APPLICATED;
		Gdx.app.debug(tag, "#state" + d.state);
	  }

    }

    @Override
    public void save(ProjectSaver s) {

    }

    @Override
    public void load(ProjectLoader ld) {
	  parseMapDaos();

	  MapExtensionDao mapDao = fetchMapExtDao();

	  mapExtension = new Extension(null);
	  mapExtension.load(ld, mapDao);


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

	  Array<String> toL = new Array<String>();

	  for (FileHandle f : h.list()) {
		if (f.child("index.json").exists()) {
		    ExtensionDao d = new ExtensionDao();
		    try {
			  d.load(FUtills.r().parse(f.child("index.json").read()));
			  d.URI = FUtills.pathToUri("extensions/" + f.name(), FUtills.STORE_L_INT);
			  if (d.stpLoad) {
				toL.add(d.ID);
			  }
			  internalDaos.put(d.ID, d);
		    } catch (Exception e) {
			  e.printStackTrace();
		    }
		}
	  }

	  Gdx.app.debug(tag, "Found " + internalDaos.size + " internal extensions ");

	  Gdx.app.debug(tag, "Startup load " + toL.toString());

	  loadExtensions(stpL, toL);
    }
}
