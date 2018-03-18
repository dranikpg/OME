package com.draniksoft.ome.utils.lang;

import com.badlogic.gdx.utils.JsonValue;

public class MissingText implements Text {
    @Override
    public String get() {
	  return "ERROR";
    }

    @Override
    public JsonValue toJ() {
	  return null;
    }
}
