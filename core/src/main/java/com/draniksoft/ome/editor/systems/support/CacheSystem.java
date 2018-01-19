package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.DwbConstructor;
import com.draniksoft.ome.editor.components.cache.mo.MOCacheC;

public class CacheSystem extends BaseSystem {

    private static String tag = "CacheSystem";

    IntMap<DwbConstructor> dwbGlobCacheM;

    public static class PRIORITY {
	  public static int NULL = 0;
	  public static int SMALL = 5;
	  public static int MED = 10;
	  public static int HIGH = 15;
    }

    public enum RESOURCE {

	  DWB_CONST

    }

    ComponentMapper<MOCacheC> moCM;

    @Override
    protected void initialize() {
	  dwbGlobCacheM = new IntMap<DwbConstructor>();
    }

    @Override
    protected void processSystem() {
    }

    public void putEttyAttrib(RESOURCE type, int id, Object attrib) {
	  MOCacheC c = safeCCGet(id);
	  c.rootDwbCache = (DwbConstructor) attrib;
    }

    public void putGlobAttrib(RESOURCE type, int id, Object attrib) {
	  dwbGlobCacheM.put(id, (DwbConstructor) attrib);
    }

    public <TYPE> TYPE getEttyAttrib(RESOURCE type, int id) {
	  MOCacheC c = safeCCGet(id);
	  return (TYPE) c.rootDwbCache;
    }

    public <TYPE> TYPE getGlobAttrib(RESOURCE type, int id) {
	  return (TYPE) dwbGlobCacheM.get(id);
    }

    public boolean hasEttyAttrib(RESOURCE type, int id) {
	  if (ccCheck(id)) return false;
	  MOCacheC c = safeCCGet(id);
	  return c.rootDwbCache != null;
    }

    public boolean hasGlobAttrib(RESOURCE type, int id) {
	  return dwbGlobCacheM.containsKey(id);
    }

    private boolean ccCheck(int id) {
	  return !moCM.has(id);
    }

    private MOCacheC safeCCGet(int id) {
	  if (moCM.has(id)) return moCM.get(id);
	  return moCM.create(id);
    }


    public double getRuntimeRamUsg() {
	  return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1e6;
    }
}
