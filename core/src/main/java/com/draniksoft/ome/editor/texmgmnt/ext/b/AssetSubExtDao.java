package com.draniksoft.ome.editor.texmgmnt.ext.b;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;
import com.draniksoft.ome.editor.extensions.t.ExtensionType;
import com.draniksoft.ome.editor.texmgmnt.ext.i.PixmapAssetSubExt;

public class AssetSubExtDao extends SubExtensionDao {

    private static final String tag = "AssetSubExtDao";


    @Override
    public void inject(Extension ext) {
        AssetSubExtension se;
        if (ext.t == ExtensionType.VIRTUAL) {
            se = new PixmapAssetSubExt();
        } else {
            se = new PixmapAssetSubExt();
        }
        if (ext.hasSub(AssetSubExtension.class)) se.prev = ext.getSub(AssetSubExtension.class);
        ext.inject(AssetSubExtension.class, se);
    }

    @Override
    public void parse(JsonValue jval) {
    }

    @Override
    public void export(JsonValue jroot) {

    }
}
