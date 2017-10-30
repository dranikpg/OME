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

    public ConfigDao(ConfigValueType t) {
        this.t = t;
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

    public void setV(Object nv) {
        if (t.canSet(nv)) {
            v = nv;
        }
    }

    public LinkedHashSet<String> getTags() {
        return tags;
    }
}
