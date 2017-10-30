package com.draniksoft.ome.support.load.interfaces;

import com.draniksoft.ome.support.load.IntelligentLoader;

public interface IRunnable {

    int RUNNING = 1;
    int TERMINATED = 3;

    int HANGUP = 4;

    void run(IntelligentLoader l);

    byte getState();

}
