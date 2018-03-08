package com.draniksoft.ome.editor.extensions.stg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectSet;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.lang.Text;

public class ExtensionDao {

    private static final String tag = "ExtensionDao";

    ExtensionDaoState state = ExtensionDaoState.AVAILABLE;

    public String ID;
    public String URI;

    boolean smz = false;     // is it a system side extension
    boolean stpLoad = false; // should I load it on startup
    //

    public Text name;
    public Text desc;
    //

    public ObjectSet<SubExtensionDao> daos;

    // Parse
    public void load(JsonValue jroot) throws Exception {
	  name = JsonUtils.parseText(jroot.get("name"));
	  desc = JsonUtils.parseText(jroot.get("desc"), false);
	  smz = jroot.has("smz") && jroot.getBoolean("smz");
	  stpLoad = jroot.has("stpload") && jroot.getBoolean("stpload");
	  JsonValue components = jroot.get("c");
	  if (components != null) {
		for (JsonValue jval : components) {
		    if (SubExtensionDao.MAP.containsKey(jval.name)) {
			  SubExtensionDao d = SubExtensionDao.MAP.get(jval.name).newInstance();
			  Gdx.app.debug(tag, "Building " + d.getClass().getSimpleName());
			  d.parse(jval);
			  daos.add(d);
		    }
		}
	  }
    }
}
