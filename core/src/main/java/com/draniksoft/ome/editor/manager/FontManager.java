package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.components.label.LabelC;
import com.draniksoft.ome.editor.components.label.LabelRTC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.file_b.ResourceLoadedEvent;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.dao.FontDao;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.struct.Pair;

import java.util.Iterator;

public class FontManager extends BaseSystem implements LoadSaveManager {

    private static final String tag = "FontManager";

    ObjectMap<String, BitmapFont> fonts;
    ObjectMap<String, FontDao> loaded;
    ObjectMap<String, FontDao> aviab;

    volatile DelayedRemovalArray<Pair<String, FontDao>> loadQQ;

    @Wire
    volatile AssetManager assM;

    @Wire(name = "engine_l")
    IntelligentLoader ll;


    @Override
    protected void initialize() {

	  ll.passRunnable(new Parser());

    }

    @Override
    protected void processSystem() {

	  if (loadQQ.size == 0) return;

	  Iterator<Pair<String, FontDao>> i = loadQQ.iterator();
	  Pair<String, FontDao> p;
	  while (i.hasNext()) {
		p = i.next();
		if (assM.isLoaded(p.K())) {
		    flushDao(p.K(), p.V());
		    i.remove();
		}
	  }

    }

    private void flushDao(String k, FontDao v) {
	  aviab.remove(v.id);

	  loaded.put(v.id, v);
	  BitmapFont f = assM.get(k, BitmapFont.class);

	  fonts.put(v.id, assM.get(k, BitmapFont.class));

	  Gdx.app.debug(tag, "Loaded " + k);
	  world.getSystem(OmeEventSystem.class).dispatch(new ResourceLoadedEvent(ResourceLoadedEvent.TYPE_FONT, v.id));
    }

    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {

    }

    @Override
    public void load(IntelligentLoader il, ProjectLoader ld) {

    }

    private class Parser implements IRunnable {

	  @Override
	  public void run(IntelligentLoader l) {
		fonts = new ObjectMap<String, BitmapFont>();
		loaded = new ObjectMap<String, FontDao>();
		aviab = new ObjectMap<String, FontDao>();

		loadQQ = new DelayedRemovalArray<Pair<String, FontDao>>();

		prepareAss();

		parseInternal();

		indexLocal();

		Gdx.app.debug(tag, "Inited with av::size " + aviab.size);

		ll.passGLRunnable(new LoadBlocker());
	  }

	  @Override
	  public byte getState() {
		return IRunnable.RUNNING;
	  }
    }

    private class LoadBlocker implements IGLRunnable {

	  @Override
	  public byte run() {

		processSystem();

		if (loadQQ.size == 0) {
		    return IGLRunnable.READY;
		} else {
		    return IGLRunnable.RUNNING;
		}

	  }
    }

    private void prepareAss() {

	  FileHandleResolver resolver = new InternalFileHandleResolver();
	  assM.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
	  assM.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

	  Gdx.app.debug(tag, "Injected Freetype ttf loader");
    }

    private void indexLocal() {

	  Iterator<FontDao> i = AppDO.I.F().getFontD();
	  FontDao d;

	  while (i.hasNext()) {
		d = i.next();
		aviab.put(d.id, d);
	  }

    }

    private void parseInternal() {

	  JsonValue root = FUtills.r.parse(Gdx.files.internal("_data/i_fonts.json"));

	  for (JsonValue v : root) {
		FontDao d = new FontDao();

		d.id = v.getString("ID");
		d.uri = FUtills.pathToUri(d.id, FUtills.STORE_L_INT);
		d.sysmz = v.has("sysmz") && v.getBoolean("sysmz");

		aviab.put(d.id, d);

		boolean fl = v.has("fl") && v.getBoolean("fl");

		if (fl) {
		    loadFont(d.id);
		}
	  }

    }

    private void reloadFont(String id) {

	  if (!aviab.containsKey(id)) return;
	  FontDao d = aviab.get(id);
	  String p = FUtills.uriToPath(d.uri) + "/f.ttf";

	  assM.unload(p);

	  FreetypeFontLoader.FreeTypeFontLoaderParameter loadp = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	  loadp.fontParameters = d.params;
	  loadp.fontFileName = p;

	  assM.load(p, BitmapFont.class, loadp);
	  loadQQ.add(Pair.P(p, d));

	  Gdx.app.debug(tag, "Reloading " + p);

    }

    private void loadFont(String id) {

	  if (!aviab.containsKey(id)) return;

	  FontDao d = aviab.get(id);

	  if (d.id == null) {
		Gdx.app.error(tag, d.uri);
	  }

	  String path = FUtills.uriToPath(d.uri) + "/f.ttf";

	  for (Pair<String, FontDao> pp : loadQQ) {
		if (pp.K().equals(path)) return;
	  }
	  if (d.params == null) {
		FreeTypeFontGenerator.FreeTypeFontParameter params = FUtills.parseTTFParameter(new JsonValue(JsonValue.ValueType.object));
		d.params = params;
	  }


	  FreetypeFontLoader.FreeTypeFontLoaderParameter loadp = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	  loadp.fontParameters = d.params;
	  loadp.fontFileName = path;


	  loadQQ.add(Pair.P(path, d));
	  assM.load(path, BitmapFont.class, loadp);

	  Gdx.app.debug(tag, "Loading " + path);

    }

    public Iterator<FontDao> getLoadedI() {
	  return loaded.values().iterator();
    }

    public Iterator<FontDao> getAvI() {
	  return aviab.values().iterator();
    }

    public boolean hasFont(String id) {
	  return fonts.containsKey(id);
    }

    public BitmapFont getFont(String id) {
	  return fonts.get(id);
    }

    ComponentMapper<LabelRTC> rm;
    ComponentMapper<LabelC> lm;


    // return true means error happened !
    public boolean rebuildCache(int _e) {

	  Gdx.app.debug(tag, "Updating glyph cache for " + _e);

	  if (!rm.has(_e)) rm.create(_e);
	  LabelRTC rc = rm.get(_e);
	  LabelC c = lm.get(_e);

	  if (!hasFont(c.font)) return true;

	  if (rc == null) {
		return true;
	  }
	  rc.c = new BitmapFontCache(getFont(c.font));
	  rc.c.setColor(c.c == null ? Color.BLACK : c.c);
	  rc.c.addText(c.text, 0, 0);

	  rc.w = 0; rc.h = 0;
	  for (GlyphLayout l : rc.c.getLayouts()) {
		rc.w += l.width;
		rc.h += l.height;
	  }

	  Gdx.app.debug(tag, "Cache ready");

	  return false;

    }
}
