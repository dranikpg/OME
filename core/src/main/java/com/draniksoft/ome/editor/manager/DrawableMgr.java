package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.map_load.ProjectLoader;
import com.draniksoft.ome.utils.struct.Pair;

public class DrawableMgr extends Manager implements LoadSaveManager {

    static final String tag = "DrawableMgr";

    public Texture t;

    @Override
    public String getNode() {
        return null;
    }

    @Override
    public void loadL(JsonValue val, ProjectLoader l) {

        l.getAssetManager().load("editor/defC/c.png", Texture.class);

        Gdx.app.debug(tag, "AssMan " + l.getAssetManager().getDiagnostics());

    }

    @Override
    public boolean loadG(ProjectLoader l) {

        t = l.getAssetManager().get("editor/defC/c.png", Texture.class);

        return true;
    }

    @Override
    public Pair<String, JsonValue> save() {
        return null;
    }
}
