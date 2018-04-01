package com.draniksoft.ome.editor.texmgmnt.ext.groups;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectSet;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.lang.Text;
import com.draniksoft.ome.utils.stringmatch.Matcher;

import java.util.Iterator;

public class AssetGroup {

    public Text name;

    // only valids are saved and etc...
    public boolean valid = true;

    public Matcher matcher;
    public ObjectSet<String> set;

    public AssetGroup() {
    }

    public void init() {
        set = new ObjectSet<String>();
    }

    public void clear() {
        set.clear();
    }

    public void consume(String id) {
        if (matcher == null) return;
        if (matcher.matches(id)) set.add(id);
    }

    public Iterator<String> getAll() {
        return set.iterator();
    }

    public boolean contains(String id) {
        return set.contains(id);
    }

    public int size() {
        return set.size;
    }

    public static AssetGroup parse(JsonValue v) {
        if (!(v.has("name") && v.has("match"))) return null;

        AssetGroup g = new AssetGroup();
        g.matcher = JsonUtils.parseMatcher(v.get("match"));
        g.name = JsonUtils.parseText(v.get("name"), true);
        g.valid = true;
        g.init();

        return g;
    }

    public static JsonValue save(AssetGroup g) {

        JsonValue v = new JsonValue(JsonValue.ValueType.object);

        v.addChild(g.matcher.export());

        v.addChild(g.name.toJ());

        return v;

    }




}
