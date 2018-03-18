package com.draniksoft.ome.utils.lang;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.JsonUtils;

public class PlainText implements Text {
    String t;

    public PlainText(String t) {
	  this.t = t;
    }

    public PlainText() {
    }

    @Override
    public String get() {
	  return t;
    }

    @Override
    public JsonValue toJ() {
	  return JsonUtils.createStringV(t);
    }
}
