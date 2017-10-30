package com.draniksoft.ome.support.load.interfaces;

import com.draniksoft.ome.utils.struct.ResponseListener;

public interface Initable {

    class Codes {

        // All okay
        public static final short READY = 1;

        // Some weird error
        public static final short ERROR = 2;

        // I need a restart
        public static final short NEED_RESTART = 3;



    }

    // l to send the respone to
    // t - tell is the process should update on a new Thread
    void init(ResponseListener l, boolean t);

}
