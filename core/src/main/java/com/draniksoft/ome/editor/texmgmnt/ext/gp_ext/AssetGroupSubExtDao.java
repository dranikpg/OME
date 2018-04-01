package com.draniksoft.ome.editor.texmgmnt.ext.gp_ext;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;

public class AssetGroupSubExtDao extends SubExtensionDao {

    @Override
    public void inject(Extension ext) {
	  ext.inject(AssetGroupSubExt.class, new AssetGroupSubExt());
    }

    @Override
    public void parse(JsonValue jval) {
	  // I dont care
    }

    @Override
    public void export(JsonValue jroot) {

	  JsonValue v = new JsonValue(JsonValue.ValueType.object);

	  jroot.addChild("asset_group", v);

    }
}
