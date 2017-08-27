package com.draniksoft.ome.editor.map_load;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.Pair;

public interface LoadSaveManager {

    String getNode();

    void loadL(JsonValue val, ProjectLoader l);

    // Return true if ready
    boolean loadG(ProjectLoader l);

    Pair<String, JsonValue> save();


}
