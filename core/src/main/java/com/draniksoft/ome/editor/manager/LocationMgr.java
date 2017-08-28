package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.map_load.ProjectLoader;
import com.draniksoft.ome.utils.struct.Pair;

public class LocationMgr extends Manager implements LoadSaveManager {


    @Override
    public String getNode() {
        return null;
    }

    @Override
    public void loadL(JsonValue val, ProjectLoader l) {

    }

    @Override
    public boolean loadG(ProjectLoader l) {
        return true;
    }

    @Override
    public Pair<String, JsonValue> save() {
        return null;
    }
}
