package com.draniksoft.ome.editor.extensions;

import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.extensions.t.ExtensionState;
import com.draniksoft.ome.editor.extensions.t.ExtensionType;

public class Extension {

    public Extension(String ID) {
	  this.ID = ID;
	  map = new ObjectMap<Class, SubExtension>();
    }

    public final String ID;

    public ExtensionType t;
    public ExtensionState s;

    public ExtensionDao dao;

    public ObjectMap<Class, ? extends SubExtension> map;

}
