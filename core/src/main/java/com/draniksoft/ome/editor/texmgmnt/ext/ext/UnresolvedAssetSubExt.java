package com.draniksoft.ome.editor.texmgmnt.ext.ext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.manager.uslac.TextureRManager;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtension;
import com.draniksoft.ome.editor.texmgmnt.ext.gp_ext.AssetGroupSubExt;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;

import java.util.Iterator;

public class UnresolvedAssetSubExt extends AssetSubExtension {

    private static final String tag = "UnresolvedAssetSubExt";

    TextureRAccesor rd;

    public UnresolvedAssetSubExt() {
	  extension.getSub(AssetGroupSubExt.class);
    }


    @Override
    public Iterator<TextureRAccesor> getAll() {
	  return map.values().iterator();
    }

    @Override
    public TextureRAccesor get(String uri) {
	  if (map.containsKey(uri)) {
		return map.get(uri);
	  }

	  Gdx.app.debug(tag, "Adding " + uri);

	  TextureRAccesor ac = new TextureRAccesor(null);
	  extension.w.getSystem(TextureRManager.class).redirectTo(ac, rd);
	  map.put(uri, ac);
	  return ac;
    }

    @Override
    public ObjectMap<String, TextureRAccesor> getMap() {
	  return map;
    }

    @Override
    public void load(ExecutionProvider p) {
	  Gdx.app.debug(tag, "WHY am I loadinnnn ");
    }

    @Override
    public void export(ExtensionExporter exporter) {

    }
}
