package com.draniksoft.ome.editor.extensions.ut;

public enum ExtensionLoadPurpose {

    BASIC,

    BASIC_DEPENDENCY,

    MAP,

    MAP_DEPENDENCY;

    public ExtensionLoadPurpose dep() {
	  return values()[ordinal() + (1 - (ordinal() % 2))];
    }

    public boolean isDdy() {
	  return ordinal() % 2 != 0;
    }

    public boolean isMLD() {
	  return ordinal() >= 2;
    }


}
