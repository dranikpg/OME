package com.draniksoft.ome.editor.support.container.EM_desc;

public abstract class EditModeDesc {

    public static class IDS {

        public static final int newMO = 1;
        public static final int moveMO = 2;

    }


    public int id;
    public String iconID;

    public boolean selRequired = false;

    public abstract String getName();

    // class to serialize
    public Class c;


}
