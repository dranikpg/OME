package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.JsonUtils;

public class ProjectMgr extends Manager implements LoadSaveManager {

    static final String tag = "ProjectMgr";

    String mName = "";

    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {
        JsonValue jv = s.getIndexV().get("proj");

        JsonValue n = JsonUtils.createStringV(mName);

        if (jv == null) {
            jv = new JsonValue(JsonValue.ValueType.object);
            jv.setName("proj");
            s.getIndexV().addChild(jv);
        }
        jv.addChild("name", n);
    }
}
