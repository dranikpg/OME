package com.draniksoft.ome.editor.texmgmnt.ext.i;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtension;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.Iterator;
import java.util.concurrent.Callable;

public class FinAssetSubExt extends AssetSubExtension {

    private static final String tag = "FinAssetSubExt";

    TextureAtlas atlas;

    public FinAssetSubExt() {
    }

    @Override
    public Iterator<TextureRAccesor> getAll() {
	  return null;
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
    public void load(final ExecutionProvider p) {

	  Gdx.app.debug(tag, "Loading ... ");

	  final String P = FUtills.uriToPath(extension.dao.URI + "/f.atlas");
	  p.getAssets().load(P, TextureAtlas.class);
	  p.awaitAsset(P,
		    new ResponseListener() {
			  @Override
			  public void onResponse(short code) {
				Gdx.app.debug(tag, "Atlas loaded ");

				atlas = p.getAssets().get(P);
				p.exec(new Loader());
			  }
		    });

    }

    @Override
    public void export(ExtensionExporter exporter) {

    }

    private class Loader implements Callable<Void> {


	  @Override
	  public Void call() throws Exception {

		try {

		    indexPrev();

		    indexFromAtlas(atlas);

		    Gdx.app.debug(tag, "Fetched " + map.size + " texAC");

		} catch (Exception e) {
		    Gdx.app.error(tag, "", e);
		}

		return null;
	  }
    }
}
