package com.draniksoft.ome.editor.res.res_mgmnt_base.types;

import com.badlogic.gdx.utils.Array;

public enum ResSubT {

    NULL,

    SIMPLE {
	  @Override
	  public boolean type(ResTypes t) {
		return true;
	  }

    },
    LINK {
	  @Override
	  public boolean type(ResTypes t) {
		return true;
	  }
    },


    __GROUP_BORDER_,



    STACK {
	  @Override
	  public boolean type(ResTypes t) {
		return true;
	  }
    },
    ANIM {
	  @Override
	  public boolean type(ResTypes t) {
		return true;
	  }
    },
    TIMED_SW {
	  @Override
	  public boolean type(ResTypes t) {
		return false;
	  }
    },


    __EXTENSION_BORDER,


    TEST_EXT;


    public int group() {
	  if (ordinal() > __EXTENSION_BORDER.ordinal()) return ResSubGroups.DECORATOR;
	  else if (ordinal() > __GROUP_BORDER_.ordinal()) return ResSubGroups.GROUP;
	  else return ResSubGroups.SIMPLE;
    }

    public boolean type(ResTypes t) {
	  return false;
    }

    public static Array<ResSubT> fetchAll(ResTypes t, int g) {
	  Array<ResSubT> ar = new Array<ResSubT>();
	  for (ResSubT rt : values()) {
		if (rt.group() == g && rt.type(t)) ar.add(rt);
	  }
	  return ar;
    }


}
