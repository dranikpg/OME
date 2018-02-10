package com.draniksoft.ome.editor.support.caching;

import com.artemis.World;

public enum STORAGE_TYPE {

    PROJ_VAL {


    },

    ETTY_BASE {


    };

    public void put(RESOURCE r, Object v, World w, int... id) {

    }

    public boolean has(RESOURCE r, World w, int... id) {
	  return false;
    }

    public <T> T get(RESOURCE r, World w, int... id) {
	  return null;
    }


}
