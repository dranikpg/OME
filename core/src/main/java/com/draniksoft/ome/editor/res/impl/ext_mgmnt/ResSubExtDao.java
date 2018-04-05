package com.draniksoft.ome.editor.res.impl.ext_mgmnt;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;

public class ResSubExtDao extends SubExtensionDao {
    @Override
    public void inject(Extension ext) {
	  ext.inject(ResSubExt.class, new ResSubExt());
    }

    @Override
    public void parse(JsonValue jval) {

    }

    @Override
    public void export(JsonValue jroot) {

    }
}
