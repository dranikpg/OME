package com.draniksoft.ome.support.configs;

public class ConfigDao {

    String id;

    public final String getId() {
        return id;
    }

    ConfigValueType t;
    Class tc;
    Object v;


    public ConfigDao(String id, ConfigValueType t) {
        this.t = t;
        this.id = id;
        tc = t.getT();
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

    public ConfigValueType getVT() {
        return t;
    }

}
