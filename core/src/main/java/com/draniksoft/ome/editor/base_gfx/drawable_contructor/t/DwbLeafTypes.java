package com.draniksoft.ome.editor.base_gfx.drawable_contructor.t;

public enum DwbLeafTypes {

    SIMPLE {
	  @Override
	  int id() {
		return 1;
	  }
    },
    LINK {
	  @Override
	  int id() {
		return 2;
	  }
    };

    int id() {
	  return 0;
    }
}
