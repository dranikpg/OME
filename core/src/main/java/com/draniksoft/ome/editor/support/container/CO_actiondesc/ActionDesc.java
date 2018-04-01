package com.draniksoft.ome.editor.support.container.CO_actiondesc;

import com.draniksoft.ome.utils.lang.Text;

public class ActionDesc {

    public int code;

    public Text name;

    public Text desc;


    public static final class BaseCodes {

        public static final short ACTION_CREATE = 1;
        public static final short ACTION_DELETE = 2;
        public static final short ACTION_RESET = 3;

	  // is available for creation at the editor view
	  public static final short ACTION_EDITVW_CREATE = 5;

    }
}
