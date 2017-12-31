package com.draniksoft.ome.editor.manager.drawable;

import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.file_b.ResourceLoadedEvent;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.dao.AssetDDao;
import com.draniksoft.ome.utils.struct.Pair;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SimpleDrawableMgr extends DrawableMgr {

    static final String tag = "SimpleDrawableMgr";

    @Wire
    AssetManager assM;

    @Wire(name = "engine_l")
    IntelligentLoader l;

    volatile ObjectMap<String, TextureAtlas> atlasM;
    volatile ObjectMap<String, AssetDDao> loadedDaoM;

    volatile ObjectMap<String, AssetDDao> avDaoM;

    volatile ObjectMap<String, String> redirect;

    DelayedRemovalArray<Pair<String, AssetDDao>> loadW;

    @Override
    protected void initialize() {

	  l.passRunnable(new Parser());

    }

    @Override
    protected void processSystem() {
	  if (loadW.size > 0) {
		Iterator<Pair<String, AssetDDao>> i = loadW.iterator();
		Pair<String, AssetDDao> p;
		while (i.hasNext()) {
		    p = i.next();
		    if (assM.isLoaded(p.K())) {
			  flushDao(p.K(), p.V());
			  i.remove();
		    }
		}
	  }

    }

    private void flushDao(String p, AssetDDao dao) {

	  Gdx.app.debug(tag, "Loaded " + p);

	  avDaoM.remove(dao.id);

	  loadedDaoM.put(dao.id, dao);

	  atlasM.put(dao.id, assM.get(p, TextureAtlas.class));

	  if (getRedirect(dao.id) != null) {
		putRedirect(dao.id, dao.id);
	  }

	  world.getSystem(OmeEventSystem.class).dispatch(new ResourceLoadedEvent(ResourceLoadedEvent.TYPE_DWB, dao.id));

    }

    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {

	  JsonValue ri = s.getIndexV();

	  JsonValue ass = new JsonValue(JsonValue.ValueType.array);
	  ri.addChild("assets", ass);

	  Iterator<AssetDDao> ds = getLoadedDaoI();
	  AssetDDao d;
	  while (ds.hasNext()) {
		d = ds.next();
		if (!d.sysmz) {
		    ass.addChild(JsonUtils.createStringV(d.id));
		}
	  }

    }

    @Override
    public void load(IntelligentLoader il, ProjectLoader ld) {
	  JsonValue ri = ld.getIndexV();
	  final Set<String> ls = new HashSet<String>();
	  for (JsonValue vi : ri.get("assets")) {
		String id = vi.asString();
		ls.add(id);
	  }


	  Gdx.app.postRunnable(new Runnable() {
		@Override
		public void run() {
		    Iterator<AssetDDao> it = getLoadedDaoI();

		    AssetDDao d;
		    while (it.hasNext()) {
			  d = it.next();
			  if (!ls.contains(d.id) && !d.sysmz) {
				unloadAsset(d.id);
			  }
		    }
		}
	  });

	  for (String id : ls) {
		AssetDDao avD;
		if (avDaoM.containsKey(id)) {
		    avD = avDaoM.get(id);
		    loadDao(avD);
		}
	  }

	  while (loadW.size > 0) {

		try {
		    Thread.sleep(90);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		Thread.yield();

	  }

	  Gdx.app.debug(tag, "Index finished");
    }

    @Override
    public void unloadAsset(String id) {

	  final AssetDDao d = loadedDaoM.get(id);

	  loadedDaoM.remove(id);

	  assM.unload(FUtills.uriToPath(d.uri) + "/f.atlas");


	  Gdx.app.debug(tag, "Unloaded " + id);
    }


    @Override
    public void loadDao(AssetDDao dao) {

	  if (hasAtlas(dao.id, false)) return;

	  String p = FUtills.uriToPath(dao.uri) + "/f.atlas";
	  for (Pair<String, AssetDDao> ds : loadW) {
		if (ds.K().equals(p)) return;
	  }

	  Gdx.app.debug(tag, "loading " + p);
	  loadW.add(Pair.P(p, dao));

	  assM.load(p, TextureAtlas.class);
    }

    private class LoadBlocker implements IGLRunnable {

	  private boolean selfM = true;

	  public LoadBlocker(boolean runAss) {
		this.selfM = runAss;
	  }

	  public LoadBlocker() {
		this(true);
	  }

	  @Override
	  public byte run() {
		if (selfM) processSystem();

		if (loadW.size > 0) {
		    return IGLRunnable.RUNNING;
		}
		return IGLRunnable.READY;
	  }

    }

    private class Parser implements IRunnable {
	  @Override
	  public void run(IntelligentLoader l) {

		atlasM = new ObjectMap<String, TextureAtlas>();
		loadedDaoM = new ObjectMap<String, AssetDDao>();
		avDaoM = new ObjectMap<String, AssetDDao>();
		redirect = new ObjectMap<String, String>();
		loadW = new DelayedRemovalArray<Pair<String, AssetDDao>>();

		Gdx.app.debug(tag, "Internal inited");

		parseInternal();

		updateLocal();

		l.passGLRunnable(new LoadBlocker());

	  }

	  @Override
	  public byte getState() {
		return IRunnable.RUNNING;
	  }
    }

    private void updateLocal() {

	  Iterator<AssetDDao> i = AppDO.I.F().getLocalAssD();

	  AssetDDao d;
	  while (i.hasNext()) {
		d = i.next();
		avDaoM.put(d.id, d);
	  }

    }

    private void parseInternal() {

	  JsonReader r = FUtills.r;
	  JsonValue root = r.parse(Gdx.files.internal("_data/i_dwbs.json"));

	  for (JsonValue v : root) {
		String id = v.getString("id");

		boolean smz = false;
		if (v.has("sysmz")) smz = v.getBoolean("sysmz");

		boolean fl = false;
		if (v.has("fl")) fl = v.getBoolean("fl");

		String rd = null;
		if (v.has("rd")) rd = v.getString("rd");


		AssetDDao d = new AssetDDao();
		d.id = id;
		d.uri = FUtills.pathToUri(id, FUtills.STORE_L_INT);
		d.sysmz = smz;

		if (rd != null) {
		    redirect.put(rd, id);
		}

		avDaoM.put(id, d);

		if (fl) {
		    loadDao(d);
		}
	  }

	  Gdx.app.debug(tag, "Parsed internal assets");
    }

    @Override
    public Iterator<AssetDDao> getLoadedDaoI() {
	  return loadedDaoM.values().iterator();
    }

    @Override
    public Iterator<AssetDDao> getAviabDaoI() {
	  return avDaoM.values().iterator();
    }

    @Override
    public Iterator<ObjectMap.Entry<String, String>> getRedirectsI() {
	  return redirect.iterator();
    }

    private String processId(String id) {
	  if (redirect.containsKey(id)) return redirect.get(id);
	  return id;
    }


    @Override
    public boolean hasAtlas(String id, boolean useRds) {

	  if (useRds) return hasAtlas(processId(id), false);

	  return atlasM.containsKey(id);
    }

    @Override
    public AssetDDao getLoadedDao(String id) {
	  return loadedDaoM.get(id);
    }

    @Override
    public AssetDDao getAviabDao(String id) {
	  if (avDaoM.containsKey(id)) return avDaoM.get(id);
	  return null;
    }

    @Override
    public String getRedirect(String id) {
	  if (!redirect.containsKey(id)) return null;
	  return redirect.get(id);
    }

    @Override
    public void putRedirect(String src, String dest) {
	  if (src.equals(dest)) {
		if (redirect.containsKey(src)) {
		    redirect.remove(src); return;
		}
	  } else {
		redirect.put(src, dest);
	  }
    }

    @Override
    public TextureAtlas.AtlasRegion getRegion(String atlas, String name, int idx) {
	  if (idx < 0) return atlasM.get(processId(atlas)).findRegion(name);
	  return atlasM.get(processId(atlas)).findRegion(name, idx);
    }

    @Override
    public TextureAtlas.AtlasRegion getRegion(String id) {
	  String[] idps = id.split("@");
	  if (idps.length < 2) return null;
	  if (idps.length == 2) return getRegion(idps[0], idps[1], -1);
	  return getRegion(idps[0], idps[1], Integer.parseInt(idps[2]));
    }

    @Override
    public TextureAtlas getAtlas(String id) {
	  return atlasM.get(processId(id));
    }

    @Override
    public void resetSystemRedirects() {

    }
}
