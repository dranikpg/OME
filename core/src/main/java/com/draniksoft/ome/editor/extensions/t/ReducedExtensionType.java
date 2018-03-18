package com.draniksoft.ome.editor.extensions.t;

import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;

public enum ReducedExtensionType {

    MAP {
	  @Override
	  public ExtensionType getT() {
		return ExtensionType.MAP;
	  }

	  @Override
	  public void setBasics(ExtensionDao d) {
		d.editAllowed = true;
	  }
    },

    SYSTEM {
	  @Override
	  public ExtensionType getT() {
		return ExtensionType.SYSTEM;
	  }

	  @Override
	  public void setBasics(ExtensionDao d) {
		d.editAllowed = false;
	  }
    },

    BASIC {
	  @Override
	  public ExtensionType getT() {
		return ExtensionType.BASIC;
	  }

	  @Override
	  public void setBasics(ExtensionDao d) {
		d.editAllowed = false;
	  }
    },

    VIRTUAL {
	  @Override
	  public ExtensionType getT() {
		return ExtensionType.VIRTUAL;
	  }

	  @Override
	  public void setBasics(ExtensionDao d) {
		d.editAllowed = true;
	  }
    };

    public ExtensionType getT() {
	  return ExtensionType.UNRESOLVED;
    }

    public void setBasics(ExtensionDao d) {

    }

    public static ReducedExtensionType to(ExtensionType t) {
	  switch (t) {
		case BASIC:
		    return BASIC;
		case SYSTEM:
		    return SYSTEM;
		case VIRTUAL:
		    return VIRTUAL;
		case MAP:
		    return MAP;
		default:
		    return null;
	  }
    }

}
