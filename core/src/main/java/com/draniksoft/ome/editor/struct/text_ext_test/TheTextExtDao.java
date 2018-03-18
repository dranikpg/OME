package com.draniksoft.ome.editor.struct.text_ext_test;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.sub.SubExtensionDao;
import com.draniksoft.ome.utils.JsonUtils;

public class TheTextExtDao extends SubExtensionDao {

    String name;

    @Override
    public void inject(Extension ext) {

	  TheTextSubExt sub = new TheTextSubExt();

	  sub.name = name;

	  ext.map.put(TheTextSubExt.class, sub);

    }

    @Override
    public void parse(JsonValue jval) {
	  name = jval.getString("name");
    }

    @Override
    public void export(JsonValue jroot) {
	  JsonValue jval = new JsonValue(JsonValue.ValueType.object);
	  jval.addChild("name", JsonUtils.createStringV(name));
	  jroot.addChild("text", jval);
    }

}
