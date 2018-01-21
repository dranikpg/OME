package com.draniksoft.ome.editor.res_mgmnt.t;

import com.badlogic.gdx.utils.Array;

public enum ResSubT {

    NULL,

    SIMPLE {
	  @Override
	  public boolean type(int t) {
		return true;
	  }

    },
    LINK {
	  @Override
	  public boolean type(int t) {
		return true;
	  }
    },

    __GROUP_BORDER_,


    STACK {
	  @Override
	  public boolean type(int t) {
		return true;
	  }
    },
    ANIM {
	  @Override
	  public boolean type(int t) {
		return true;
	  }
    },
    TIMED_SW {
	  @Override
	  public boolean type(int t) {
		return false;
	  }
    };

    public boolean group() {
	  return ordinal() > ResSubT.__GROUP_BORDER_.ordinal();
    }

    public boolean type(int t) {
	  return false;
    }

    public static Array<ResSubT> fetchAll(int t, boolean g) {
	  Array<ResSubT> ar = new Array<ResSubT>();
	  for (ResSubT rt : values()) {
		if (rt.group() == g && rt.type(t)) ar.add(rt);
	  }
	  return ar;
    }


}
