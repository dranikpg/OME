package com.draniksoft.ome.editor.base_gfx.drawable_contructor.t;

public enum DwbGroupTypes {

    STACK {
	  @Override
	  public String toString() {
		return name();
	  }

	  @Override
	  public int id() {
		return 11;
	  }
    },
    ANIMATION {
	  @Override
	  public String toString() {
		return name();
	  }

	  @Override
	  public int id() {
		return 12;
	  }
    },
    TIMED {
	  @Override
	  public String toString() {
		return name();
	  }

	  @Override
	  public int id() {
		return 13;
	  }
    };

    String getBundleID() {
	  return null;
    }

    public int id() {
	  return 10;
    }

    public static DwbGroupTypes tfor(int id) {
	  if (id == STACK.id()) {
		return STACK;
	  } else if (id == ANIMATION.id()) {
		return ANIMATION;
	  } else if (id == TIMED.id()) {
		return TIMED;
	  } return null;
    }

}
