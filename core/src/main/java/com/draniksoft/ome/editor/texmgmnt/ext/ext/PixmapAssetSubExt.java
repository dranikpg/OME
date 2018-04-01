package com.draniksoft.ome.editor.texmgmnt.ext.ext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtension;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.sync.OneExecTask;
import com.draniksoft.ome.utils.FUtills;

import java.util.Iterator;
import java.util.concurrent.Callable;


public class PixmapAssetSubExt extends AssetSubExtension {

    private static final String tag = "PixmapAssetSubExt";

    ObjectMap<String, Pixmap> pixMap;

    public volatile boolean processing = false;
    // paths
    ObjectSet<String> pendingLoad;
    // ids
    ObjectSet<String> pendingUnload;

    TextureAtlas atlas;

    public PixmapAssetSubExt() {
	  pixMap = new ObjectMap<String, Pixmap>();
	  pendingLoad = new ObjectSet<String>();
	  pendingUnload = new ObjectSet<String>();

    }

    @Override
    public Iterator<TextureRAccesor> getAll() {
	  return map.values().iterator();
    }

    @Override
    public TextureRAccesor get(String uri) {
	  return map.get(uri);
    }

    @Override
    public ObjectMap<String, TextureRAccesor> getMap() {
	  return map;
    }

    @Override
    public void load(ExecutionProvider p) {

	  FileHandle fld = FUtills.uriToFile(extension.dao.URI + "/assets");

	  if (!fld.exists()) return;

	  for (FileHandle sub : fld.list()) {
		// if(!sub.extension().equals("png")) continue;
		pendingLoad.add(sub.path());
	  }

	  Gdx.app.debug(tag, "To load " + pendingLoad.toString());

	  indexPrev();

	  repack(p);

    }

    @Override
    public void export(ExtensionExporter exporter) {

    }

    public void addPending(String path) {
	  if (processing) return;
	  pendingLoad.add(path);
    }

    public void remove(String id) {
	  if (processing) return;
	  pendingUnload.add(id);
    }

    public void repack(ExecutionProvider provider) {

	  if (processing) return;

	  if (pendingUnload.size == 0 && pendingLoad.size == 0) return;

	  Gdx.app.debug(tag, "Re-packing");

	  ProcessData data = new ProcessData();
	  data.provider = provider;

	  provider.exec(new PixLoader(data));

    }

    private class ProcessData {

	  public ExecutionProvider provider;

	  public TextureAtlas oldAtlas;

	  public PixmapPacker packer;

    }

    private class PixLoader implements Callable<Void> {

	  ProcessData data;

	  public PixLoader(ProcessData data) {
		this.data = data;
	  }

	  @Override
	  public Void call() throws Exception {

		Gdx.app.debug(tag, "Processing");

		processing = true;

		for (String pt : pendingLoad) {
		    FileHandle hd = Gdx.files.absolute(pt);
		    if (!hd.exists()) continue;

		    try {
			  Pixmap pix = new Pixmap(Gdx.files.absolute(pt));
			  pixMap.put(findName(hd.nameWithoutExtension()), pix);
		    } catch (Exception e) {
			  Gdx.app.error(tag, "", e);
		    }

		}

		for (String unload : pendingUnload) {
		    Pixmap m = pixMap.get(unload);
		    m.dispose();

		    // TODO some better solution to :
		    map.remove(unload);
		}

		PixmapPacker packer = new PixmapPacker(500, 500, Pixmap.Format.RGBA4444, 2, false);

		for (ObjectMap.Entry<String, Pixmap> e : pixMap) {
		    packer.pack(e.key, e.value);
		}

		data.packer = packer;

		data.provider.addShd(new AtlasPacker(data));


		return null;
	  }

	  private String findName(String src) {

		src = src.replace("_", "@");

		if (!pixMap.containsKey(src)) return src;

		int c = 1;

		while (pixMap.containsKey(src + "@" + c)) c++;

		return src + "@" + c;

	  }
    }

    public class AtlasPacker extends OneExecTask {

	  ProcessData data;

	  public AtlasPacker(ProcessData data) {
		this.data = data;
	  }

	  @Override
	  protected void execute() {
		data.oldAtlas = atlas;
		atlas = data.packer.generateTextureAtlas(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, false);
		data.provider.exec(new Indexer(data));
	  }

    }

    public class Indexer implements Callable<Void> {

	  ProcessData data;

	  public Indexer(ProcessData data) {
		this.data = data;
	  }

	  @Override
	  public Void call() throws Exception {

		indexFromAtlas(atlas);

		indexGroups();

		data.provider.addShd(new Disposer(data));

		return null;
	  }

    }

    public class Disposer extends OneExecTask {

	  ProcessData data;

	  public Disposer(ProcessData data) {
		this.data = data;
	  }

	  @Override
	  protected void execute() {
		data.packer.dispose();
		if (data.oldAtlas != null) data.oldAtlas.dispose();

		pendingLoad.clear();
		pendingUnload.clear();

		processing = false;

		Gdx.app.debug(tag, "Disposed");

	  }
    }
}
