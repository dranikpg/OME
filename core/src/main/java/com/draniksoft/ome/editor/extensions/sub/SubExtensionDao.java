package com.draniksoft.ome.editor.extensions.sub;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.struct.text_ext_test.TheTextExtDao;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtDao;

public abstract class SubExtensionDao {

    // tag:STATIC_MAP

    public static ObjectMap<String, Class<? extends SubExtensionDao>> MAP = new ObjectMap<String, Class<? extends SubExtensionDao>>();

    static {
	  MAP.put("assets", AssetSubExtDao.class);
	  MAP.put("text", TheTextExtDao.class);

	  System.out.println("SubExtDaoMap \n " + MAP.toString());
    }

    /*
    	Creates a new sub-extension of the given type and injects, possible copies(like restoring from unresolved state)
     */

    public abstract void inject(Extension ext);


    public abstract void parse(JsonValue jval);


    public abstract void export(JsonValue jroot);
}
