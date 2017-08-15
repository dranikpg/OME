package com.draniksoft.ome.utils.preload;

import com.draniksoft.ome.utils.ResponseListener;

public interface Initable {

    public static class Codes{

        // All okay
        public static final short READY = 1;

        // Some weird error
        public static final short ERROR = 2;

        // I need a restart
        public static final short NEED_RESTART = 3;



    }

    // l to send the respone to
    // t - tell is the process should run on a new Thread
    public void init(ResponseListener l, boolean t);

}
