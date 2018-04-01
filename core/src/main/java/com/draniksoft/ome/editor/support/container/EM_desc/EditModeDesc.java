package com.draniksoft.ome.editor.support.container.EM_desc;

import com.draniksoft.ome.editor.support.ems.core.EditMode;
import com.draniksoft.ome.utils.lang.Text;

import java.lang.ref.Reference;

public class EditModeDesc {

    public static class IDS {
        public static final int newMO = 1;
        public static final int moveMO = 2;
	  public static final int pathEdit = 11;
    }

    public Reference<EditMode> ref;

    public Class c;
    public int id;

    public boolean available;
    public boolean selRequired = false;

    public Text name;
    public String iconID;


}
