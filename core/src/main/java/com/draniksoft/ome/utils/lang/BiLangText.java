package com.draniksoft.ome.utils.lang;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.JsonUtils;

public class BiLangText implements Text {

    public String ru, en;

    public BiLangText(String en, String ru) {
	  this.ru = ru;
	  this.en = en;
    }

    public BiLangText() {

    }

    @Override
    public String get() {
	  if (AppDO.I.L().isEnL()) {
		return en;
	  } else {
		return ru;
	  }
    }

    @Override
    public JsonValue toJ() {
	  JsonValue r = new JsonValue(JsonValue.ValueType.object);
	  r.addChild(JsonUtils.createStringV("en", en));
	  r.addChild(JsonUtils.createStringV("ru", ru));
	  return r;
    }

}
