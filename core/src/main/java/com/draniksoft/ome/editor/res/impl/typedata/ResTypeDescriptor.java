package com.draniksoft.ome.editor.res.impl.typedata;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.lang.Text;

public class ResTypeDescriptor {

    public Text name;

    public boolean group;

    public boolean local;
    public boolean global;

    public Class c;

    public static ResTypeDescriptor read(JsonValue v) {
	  ResTypeDescriptor d = new ResTypeDescriptor();
	  d.name = JsonUtils.parseText(v.get("name"), true);
	  d.group = v.getBoolean("group", false);
	  d.local = v.getBoolean("local", true);
	  d.global = v.getBoolean("global", true);
	  d.c = JsonUtils.parseClass(v.get("c"));
	  return d;
    }

}
