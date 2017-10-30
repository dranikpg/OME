package com.draniksoft.ome.support.load.interfaces;

public interface IGLRunnable {

    int RUNNING = 1;
    int READY = 2;
    int TERMINATED = 3;

    int HANGUP = 4;


    byte run();

}
