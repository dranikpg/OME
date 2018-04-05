package com.draniksoft.ome.editor.extensions.sub;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.res.impl.ext_mgmnt.ResSubExtDao;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtDao;
import com.draniksoft.ome.editor.texmgmnt.ext.gp_ext.AssetGroupSubExtDao;

public abstract class SubExtensionDao {

    // tag:STATIC_MAP

    public static ObjectMap<String, Class<? extends SubExtensionDao>> MAP = new ObjectMap<String, Class<? extends SubExtensionDao>>();

    static {

	  // assets
	  MAP.put("assets", AssetSubExtDao.class);
	  MAP.put("asset_group", AssetGroupSubExtDao.class);

	  // res
	  MAP.put("res", ResSubExtDao.class);

	  System.out.println("SubExtDaoMap \n " + MAP.toString());
    }

    /*
    	Creates a new sub-extension of the given type and injects, possible copies(like restoring from unresolved state)
     */

    public abstract void inject(Extension ext);


    public abstract void parse(JsonValue jval);


    public abstract void export(JsonValue jroot);
}
