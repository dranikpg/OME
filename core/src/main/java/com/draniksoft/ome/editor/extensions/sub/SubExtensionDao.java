package com.draniksoft.ome.editor.extensions.sub;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.Extension;

public abstract class SubExtensionDao<SUBT extends SubExtension> {

    public static ObjectMap<String, Class<? extends SubExtensionDao>> MAP = new ObjectMap<String, Class<? extends SubExtensionDao>>();

    /*
    	Creates a new sub-extension of the given type and injects, possible copies(like restoring from unresolved state)
     */

    public abstract void inject(Extension ext);


    public abstract void parse(JsonValue jval);

}
