package com.draniksoft.ome.editor.support.container.EM_desc;

public abstract class EditModeDesc {

    public static class IDS {
        public static final int newMO = 1;
        public static final int moveMO = 2;
	  public static final int pathEdit = 11;
    }


    public int id;
    public String iconID;

    public static final int AVAILABLE = 1;
    public static final int AV_HIDDEN = 2;
    public static final int DISABLED = 3;


    public int aviabT = 3;

    public boolean selRequired = false;

    public abstract String getName();

    // class to serialize
    public Class c;


}
