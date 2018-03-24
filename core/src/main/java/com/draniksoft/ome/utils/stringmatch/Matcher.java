package com.draniksoft.ome.utils.stringmatch;

import com.badlogic.gdx.utils.JsonValue;

public interface Matcher {

    boolean matches(String s);

    void load(JsonValue v);

    JsonValue export();


}
