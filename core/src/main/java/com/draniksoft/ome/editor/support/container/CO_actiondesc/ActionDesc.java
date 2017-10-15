package com.draniksoft.ome.editor.support.container.CO_actiondesc;

public abstract class ActionDesc {

    public int code;

    public abstract String getName();

    public abstract String getDesc();

    public boolean noargpsb;

    public static class BaseCodes {

        public static final short ACTION_CREATE = 1;
        public static final short ACTION_DELETE = 2;
        public static final short ACTION_RESET = 3;

    }
}
