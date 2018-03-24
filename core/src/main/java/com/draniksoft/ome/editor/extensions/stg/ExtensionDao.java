package com.draniksoft.ome.editor.extensions.stg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectSet;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;
import com.draniksoft.ome.editor.extensions.t.ReducedExtensionType;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.lang.Text;

public class ExtensionDao {

    private static final String tag = "ExtensionDao";

    public ExtensionDao() {
	  daos = new ObjectSet<SubExtensionDao>();
    }

    public ExtensionDaoState state = ExtensionDaoState.AVAILABLE;

    public ObjectSet<String> req;

    public String ID;
    public String URI;

    public boolean editAllowed = false; // allowed to edit

    public ReducedExtensionType t = ReducedExtensionType.BASIC;     // is it a system side extension
    public boolean stpLoad = false; // should I load it on startup


    //

    public Text name;
    public Text desc;
    //

    public ObjectSet<SubExtensionDao> daos;

    /*
    	Load
     */

    public void load(JsonValue jroot) throws Exception {

	  parseEssentials(jroot);

	  for (JsonValue jval : jroot) {
		if (SubExtensionDao.MAP.containsKey(jval.name)) {
		    SubExtensionDao d = SubExtensionDao.MAP.get(jval.name).newInstance();
		    Gdx.app.debug(tag, "Building " + d.getClass().getSimpleName());
		    d.parse(jval);
		    daos.add(d);
		}
	  }

    }

    public void parseEssentials(JsonValue jroot) {
	  ID = jroot.getString("id");

	  ID.intern();

	  name = JsonUtils.parseText(jroot.get("name"));
	  desc = JsonUtils.parseText(jroot.get("desc"), false);

	  t = ReducedExtensionType.valueOf(jroot.getString("type"));
	  stpLoad = jroot.has("stpload") && jroot.getBoolean("stpload");

	  editAllowed = jroot.has("edit") && jroot.getBoolean("edit");

	  req = new ObjectSet<String>();
	  if (jroot.hasChild("req")) {
		JsonValue reqar = jroot.get("req");
		req.addAll(reqar.asStringArray());
	  }

    }

    /*
    	Save
     */

    public void save(JsonValue jroot) {

	  for (SubExtensionDao d : daos) {
		d.export(jroot);
	  }

	  writeEssentials(jroot);

    }

    public void writeEssentials(JsonValue jroot) {

	  jroot.addChild(JsonUtils.createStringV("id", ID));

	  jroot.addChild("name", name.toJ());

	  if (desc != null)
		jroot.addChild("desc", desc.toJ());

	  jroot.addChild("type", JsonUtils.createStringV(t.name()));

	  if (editAllowed)
		jroot.addChild("edit", JsonUtils.createBoolV(editAllowed));

	  if (req.size > 0) {
		jroot.addChild("req", JsonUtils.createStringArrV(req));
	  }

        /*
	  	Tings with smz and stpload are usually not exported
         */

    }
}
