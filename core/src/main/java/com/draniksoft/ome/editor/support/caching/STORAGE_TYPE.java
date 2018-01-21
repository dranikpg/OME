package com.draniksoft.ome.editor.support.caching;

import com.artemis.World;
import com.draniksoft.ome.editor.systems.support.CacheSystem;

public enum STORAGE_TYPE {

    PROJ_VAL {
	  @Override
	  public boolean has(RESOURCE r, int id, World w) {
		return w.getSystem(CacheSystem.class)._mapHas(r, id);
	  }

	  @Override
	  public void put(RESOURCE r, int id, Object v, World w) {
		w.getSystem(CacheSystem.class)._mapPut(r, id, v);
	  }

	  @Override
	  public <T> T get(RESOURCE r, int id, World w) {
		return w.getSystem(CacheSystem.class)._mapGet(r, id);
	  }

    },

    ETTY_BASE {


    };

    public void put(RESOURCE r, int id, Object v, World w) {

    }

    public boolean has(RESOURCE r, int id, World w) {
	  return false;
    }

    public <T> T get(RESOURCE r, int id, World w) {
	  return null;
    }


}
