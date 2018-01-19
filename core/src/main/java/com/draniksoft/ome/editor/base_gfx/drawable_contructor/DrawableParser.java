package com.draniksoft.ome.editor.base_gfx.drawable_contructor;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.t.DwbGroupTypes;
import com.draniksoft.ome.editor.systems.support.CacheSystem;
import com.draniksoft.ome.utils.FUtills;

public class DrawableParser {

    private static String tag = "DrawableParser";

    static DrawableParser i;

    DwbConstructor rootC;

    World w;

    public synchronized static DrawableParser I() {
	  if (i == null) i = new DrawableParser();
	  return i;
    }

    public void setW(World w) {
	  this.w = w;
    }

    private void parseFrom(GroupConstructor parent, JsonValue v) {
	  parse(null, parent, v);
    }

    private void parse(DwbConstructor c, GroupConstructor parent, JsonValue v) {

	  if (c == null) {

		c = fetchConstFromJSon(v);

	  }

	  if (parent != null) parent.add(c);
	  else rootC = c;

    }

    private DwbConstructor fetchConstFromJSon(JsonValue v) {

	  int t = v.getInt("t");

	  if (t >= DwbGroupTypes.values()[0].id()) {

		GroupConstructor c = new GroupConstructor();

		c.setType(DwbGroupTypes.tfor(t));

		c.fetchData(v);

		c.updateSources();

		for (JsonValue chv : v.get("a")) {

		    parse(null, c, chv);

		}

		return c;

	  } else {

		LeafConstructor c = new LeafConstructor();

		c.fetchData(v);

		return c;
	  }

    }

    private JsonValue startSrz(DwbConstructor c) {

	  JsonValue root = new JsonValue(JsonValue.ValueType.object);

	  c.putData(root);

	  if (c instanceof GroupConstructor) {

		JsonValue jar = new JsonValue(JsonValue.ValueType.array);

		for (DwbConstructor chc : ((GroupConstructor) c).getChildren()) {
		    srz(chc, jar);
		}

		root.addChild("a", jar);

	  }

	  return root;

    }

    private void srz(DwbConstructor c, JsonValue pa) {

	  if (c instanceof GroupConstructor) {

		JsonValue v = new JsonValue(JsonValue.ValueType.object);

		c.putData(v);

		JsonValue jar = new JsonValue(JsonValue.ValueType.array);

		for (DwbConstructor chc : ((GroupConstructor) c).getChildren()) {
		    srz(chc, jar);
		}

		v.addChild("a", jar);

		pa.addChild(v);

	  } else {

		JsonValue v = new JsonValue(JsonValue.ValueType.object);

		c.putData(v);

		pa.addChild(v);

	  }

    }

     /*
    PUBLIC SPACE
     */

    public JsonValue fullSrz(DwbConstructor c) {
	  return startSrz(c);
    }

    public void cacheDwb(boolean e, int id, DwbConstructor c) {
	  if (e) w.getSystem(CacheSystem.class).putEttyAttrib(CacheSystem.RESOURCE.DWB_CONST, id, c);
	  else w.getSystem(CacheSystem.class).putGlobAttrib(CacheSystem.RESOURCE.DWB_CONST, id, c);
    }

    public DwbConstructor parse(String st) {

	  Gdx.app.debug(tag, "Parsing " + st);

	  JsonValue root = FUtills.r.parse(st);

	  try {
		parseFrom(null, root);
	  } catch (Exception e) {

	  }
	  if (rootC == null) {
		Gdx.app.error(tag, "Precache failed to store result");
		return null;
	  }

	  return rootC;

    }

}
