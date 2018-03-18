package com.draniksoft.ome.editor.texmgmnt.ext;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;

public class AssetSubExtDao extends SubExtensionDao {

    private static final String tag = "AssetSubExtDao";

    @Override
    public void inject(Extension ext) {
	  BasicAssetSubExt assetE = new BasicAssetSubExt();
	  ext.map.put(AssetSubExtension.class, assetE);
	  assetE.atlasUri = ext.dao.URI + "/f.atlas";
    }

    @Override
    public void parse(JsonValue jval) {
    }

    @Override
    public void export(JsonValue jroot) {

    }
}
