package com.draniksoft.ome.support.configs;

import java.util.LinkedHashSet;

public abstract class ConfigDao {

    String id;

    public final String getId() {
        return id;
    }

    public abstract String getName();

    public abstract String getDesc();

    ConfigValueType t;
    Class tc;
    Object v;

    LinkedHashSet<String> tags;

    public ConfigDao(String id, ConfigValueType t) {
        this.t = t;
        this.id = id;
        tc = t.getT();
        tags = new LinkedHashSet<String>();
    }

    public <T> T getV(Class<T> c) {
        return (T) v;
    }

    public Class getT() {
        return tc;
    }

    public boolean canS(Object v) {
        return t.canSet(v);
    }

    public boolean setV(Object nv) {
        if (t.canSet(nv)) {
            v = nv;
            return true;
        }
        return false;
    }

    public LinkedHashSet<String> getTags() {
        return tags;
    }
}
