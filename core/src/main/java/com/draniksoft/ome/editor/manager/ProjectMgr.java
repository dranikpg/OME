package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.utils.JsonUtils;

public class ProjectMgr extends Manager implements LoadSaveManager {

    static final String tag = "ProjectMgr";

    String pName = "";

    @Override
    public void save(ProjectSaver s) {
	  JsonValue jv = s.getIndexV().get("proj");
	  JsonValue n = JsonUtils.createStringV(pName);
	  if (jv == null) {
            jv = new JsonValue(JsonValue.ValueType.object);
            jv.setName("proj");
            s.getIndexV().addChild(jv);
        }
        jv.addChild("name", n);
    }

    @Override
    public void load(ProjectLoader ld) {
	  JsonValue root = ld.getIndexV().get("proj");
	  pName = root.getString("name");
    }
}
