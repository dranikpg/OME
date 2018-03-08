package com.draniksoft.ome.support.execution_base.sync;

public abstract class SimpleSyncTask implements SyncTask {

    int fq = 1;
    int ph = 0;

    protected boolean active = true;

    public SimpleSyncTask(int fq, int ph) {
	  this.fq = fq;
	  this.ph = ph;
    }

    public SimpleSyncTask(int fq) {
	  this.fq = fq;
    }

    @Override
    public int getFQ() {
	  return fq;
    }

    @Override
    public int getPhase() {
	  return ph;
    }

    @Override
    public boolean active() {
	  return active;
    }

    public void terminate() {
	  active = false;
    }
}
