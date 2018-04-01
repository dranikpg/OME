package com.draniksoft.ome.editor.texmgmnt.ext.gp_ext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtension;
import com.draniksoft.ome.editor.texmgmnt.ext.groups.AssetGroup;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.stringmatch.AllMatcher;

import java.util.Iterator;
import java.util.concurrent.Callable;

public class AssetGroupSubExt extends SubExtension {

    private static final String tag = "AssetGroupSubExt";


    Array<AssetGroup> groups = new Array<AssetGroup>(false, 4);

    private volatile boolean indexReq = false;
    private volatile boolean loading = false;

    public AssetGroupSubExt() {

    }

    public void index(AssetSubExtension ext) {
	  Gdx.app.debug(tag, "Indexing");
	  indexReq = false;

	  removeInvalid(groups);
	  for (AssetGroup g : groups) g.clear();

	  checkEmpty();

	  for (Iterator<String> it = ext.getAllKeys(); it.hasNext(); ) {
		String s = it.next();
		for (AssetGroup g : groups) {
		    g.consume(s);
		}
	  }

    }

    public void requestIndex() {
	  Gdx.app.debug(tag, "Index request");
	  if (loading) indexReq = true;
	  else index(extension.getSub(AssetSubExtension.class));
    }


    public Array<AssetGroup> getGroups() {
	  return groups;
    }

    @Override
    public void load(ExecutionProvider p) {
	  p.exec(new Loader());
    }

    @Override
    public void export(ExtensionExporter exporter) {

	  exporter.execp().exec(new Saver(exporter.getFileRoot()));

    }


    /* utils*/
    private void checkEmpty() {
	  if (groups.size == 0) {
		Gdx.app.debug(tag, "Creating default asset group ");

		AssetGroup g = new AssetGroup();
		g.valid = false;
		g.name = extension.dao.name;
		g.matcher = new AllMatcher();
		g.init();
	  }
    }

    private void removeInvalid(Array<AssetGroup> ar) {

	  IntArray ia = new IntArray();

	  for (int i = ar.size - 1; i >= 0; i--) {
		if (!ar.get(i).valid) ia.addAll(i);
	  }

	  for (int i = 0; i < ia.size; i++) {
		ar.removeIndex(ia.get(i));
	  }

    }

    private class Saver implements Callable<Void> {

	  FileHandle fileR;

	  public Saver(FileHandle fileR) {
		this.fileR = fileR;
	  }

	  @Override
	  public Void call() throws Exception {


		Array<AssetGroup> groupcp = new Array<AssetGroup>(groups);
		removeInvalid(groupcp);

		JsonValue v = new JsonValue(JsonValue.ValueType.array);

		for (AssetGroup g : groupcp) {
		    v.addChild(AssetGroup.save(g));
		}

		String out = v.toJson(JsonWriter.OutputType.json);

		FileHandle file = fileR.child("asset_group.json");
		file.mkdirs();
		file.writeString(out, false);

		return null;
	  }
    }


    private class Loader implements Callable<Void> {

	  @Override
	  public Void call() throws Exception {

		loading = true;

		/* jff for late index tests
		try{
		    Thread.sleep(5000);
		}catch (Exception e){
		    Gdx.app.error(tag,"",e);
		}
		*/


		FileHandle file = FUtills.uriToFile(extension.dao.URI + "/asset_group.json");

		if (!file.exists()) return _return();

		JsonValue jr = FUtills.r().parse(file.readString());

		for (JsonValue v : jr) {
		    AssetGroup g = AssetGroup.parse(v);
		    if (g != null) groups.add(g);
		}

		Gdx.app.debug(tag, "Fetched " + groups.size + " asset groups");

		return _return();
	  }

	  private Void _return() {
		Gdx.app.debug(tag, "-END");
		loading = false;
		if (indexReq) index(extension.getSub(AssetSubExtension.class));
		return null;
	  }
    }


}
