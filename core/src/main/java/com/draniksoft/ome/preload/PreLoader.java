package com.draniksoft.ome.preload;

import com.draniksoft.ome.utils.ResponseListener;

public interface PreLoader {

    public static class Codes{

        // All okay -> show MenuScreen
        public static final short READY = 1;

        // Error happened
        public static final short ERROR = 2;

        // Dunno when this should happen
        public static final short NEED_RESTART = 3;

    }

    public void init(ResponseListener l);

    public void startLoading();

    public void stopLoading();


}
