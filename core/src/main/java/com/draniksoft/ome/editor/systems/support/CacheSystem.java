package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;
import com.draniksoft.ome.editor.support.caching.RESOURCE;
import com.draniksoft.ome.editor.support.caching.STORAGE_TYPE;

public class CacheSystem extends BaseSystem {

    private static String tag = "CacheSystem";


    IntMap<IntMap<Object>> sMap;

    IntMap<IntSet> sSet;

    @Override
    protected void initialize() {

	  sMap = new IntMap<IntMap<Object>>();
	  sSet = new IntMap<IntSet>();

    }

    @Override
    protected void processSystem() {

    }

    public boolean has(RESOURCE r, STORAGE_TYPE t, int id) {
	  return t.has(r, id, world);
    }

    public <T> void put(RESOURCE r, STORAGE_TYPE t, int id, T val) {
	  t.put(r, id, val, world);
    }

    public <T> T get(Class<T> c, RESOURCE r, STORAGE_TYPE t, int id) {
	  return t.get(r, id, world);
    }

    public void _mapPut(RESOURCE r, int id, Object o) {
	  if (!sMap.containsKey(r.ordinal())) sMap.put(r.ordinal(), new IntMap<Object>());
	  sMap.get(r.ordinal()).put(id, o);
    }

    public <T> T _mapGet(RESOURCE r, int id) {
	  return (T) sMap.get(r.ordinal()).get(id);
    }

    public boolean _mapHas(RESOURCE r, int id) {
	  if (!sMap.containsKey(r.ordinal())) return false;
	  return sMap.get(r.ordinal()).containsKey(id);
    }


    public double getRuntimeRamUsg() {
	  return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1e6;
    }
}
