package com.draniksoft.ome.editor.res.pv_res_wrapper;

import com.draniksoft.ome.utils.SUtils;

public class BasePVResWrapper extends PVResWrapper {

    /* IDS OF DEFAULT WRAPPERS


    */

    public static short DEFAULT = 1;
    public static short FALLBACK = 2;

    public static short THRESHOLD = 5;


    String key;
    boolean edt;

    public void init(String key, boolean edt) {
	  this.key = key;
	  this.edt = edt;
    }


    @Override
    public boolean allowed(MODIFIC m) {
	  if (m == MODIFIC.CHANGE) {
		return edt;
	  } else {
		return false;
	  }
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
	  return SUtils.getS(key);
    }

    @Override
    public boolean isBase() {
	  return true;
    }
}
