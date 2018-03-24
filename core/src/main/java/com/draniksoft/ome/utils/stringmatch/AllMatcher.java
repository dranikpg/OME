package com.draniksoft.ome.utils.stringmatch;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.JsonUtils;

public class AllMatcher implements Matcher {
    @Override
    public boolean matches(String s) {
	  return true;
    }

    @Override
    public void load(JsonValue v) {

    }

    @Override
    public JsonValue export() {
	  JsonValue v = new JsonValue(JsonValue.ValueType.object);

	  v.addChild("type", JsonUtils.createStringV("all"));

	  return v;
    }
}
