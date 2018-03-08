package com.draniksoft.ome.support.execution_base.sync;

/*
	For running & sheuduling
 */
public interface SyncTask {

    int getFQ();

    int getPhase();

    void run();

    boolean active();

}
