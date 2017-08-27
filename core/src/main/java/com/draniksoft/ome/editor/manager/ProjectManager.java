package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.draniksoft.ome.editor.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.map_load.ProjectLoader;
import com.draniksoft.ome.utils.struct.Pair;

public class ProjectManager extends Manager implements LoadSaveManager {

    static final String tag = "ProjectManager";

    String mName;

    @Override
    public void loadL(JsonValue val, ProjectLoader l) {

        mName = val.get("name").asString();

    }

    @Override
    public boolean loadG(ProjectLoader l) {
        return true;
    }

    @Override
    public Pair<String, JsonValue> save() {

        JsonValue v = new JsonValue(JsonValue.ValueType.object);

        JsonValue mval = new JsonValue(JsonValue.ValueType.stringValue);
        mval.set(mName);
        mval.setName("name");

        v.addChild(mval);

        Gdx.app.debug(tag, "output json is " + v.prettyPrint(JsonWriter.OutputType.json, 1));

        return Pair.createPair("info", v);
    }

    @Override
    public String getNode() {
        return "info";
    }

    public String getName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
