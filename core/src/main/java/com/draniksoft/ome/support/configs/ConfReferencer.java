package com.draniksoft.ome.support.configs;

import com.badlogic.gdx.utils.ObjectMap;

public class ConfReferencer<K, T> extends ObjectMap<K, T> {


    T elseVal;

    public boolean hasOption() {
	  return elseVal != null;
    }

    public T getOptional() {
	  return elseVal;
    }

    public boolean hasConf(K k) {
	  return this.containsKey(k);
    }

    public Object obtainVal(K k) {
	  return this.get(k);
    }

    public void insert(K kV, T vV) {
	  if (kV instanceof String) {
		String k = (String) kV;
		if (k.equals("$ELSE$")) {
		    elseVal = vV;
		    return;
		}
	  }
	  put(kV, vV);
    }
}
