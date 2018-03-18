package com.draniksoft.ome.utils.lang;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.SUtils;

public class I18NText implements Text {
    public String k;

    public I18NText(String k) {
	  this.k = k;
    }

    public I18NText() {
    }

    @Override
    public String get() {
	  return SUtils.getS(k);
    }

    @Override
    public JsonValue toJ() {
	  return JsonUtils.createStringV(":@" + k);
    }
}
