package com.draniksoft.ome.utils.stringmatch;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.JsonUtils;

public class StartsWithMatcher implements Matcher {

    String mtc;

    @Override
    public boolean matches(String s) {
	  return s.startsWith(mtc);
    }

    @Override
    public void load(JsonValue v) {
	  mtc = v.getString("stsw");


    }

    @Override
    public JsonValue export() {
	  JsonValue v = new JsonValue(JsonValue.ValueType.object);

	  v.addChild("type", JsonUtils.createStringV("stsw"));

	  v.addChild("str", JsonUtils.createStringV(mtc));

	  return v;
    }
}
