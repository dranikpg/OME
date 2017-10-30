package com.draniksoft.ome.support.load.observer;

import com.draniksoft.ome.utils.SUtils;

public class BundleLoadLogInfo implements LoadLogInfo {

    String name;
    String desc;

    public BundleLoadLogInfo(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String getName() {
        return SUtils.getS(name);
    }

    @Override
    public String getDesc() {
        return SUtils.getS(desc);
    }

    @Override
    public String toString() {
        return getName() + " :: " + getDesc();
    }
}
