package com.draniksoft.ome.editor.extensions.map;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.t.ReducedExtensionType;
import com.draniksoft.ome.utils.lang.PlainText;

public class MapExtensionDao extends ExtensionDao {

    @Override
    public void parseEssentials(JsonValue jroot) {

	  ID = "";
	  name = new PlainText("MAP");

	  t = ReducedExtensionType.MAP;
    }

    @Override
    public void writeEssentials(JsonValue jroot) {

    }
}
