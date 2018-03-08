package com.draniksoft.ome.support.execution_base.sync;

public abstract class OneExecTask implements SyncTask {

    int ph = 0;

    boolean active = true;

    public OneExecTask(int ph) {
	  this.ph = ph;
    }

    public OneExecTask() {
    }

    @Override
    public int getFQ() {
	  return 1;
    }

    @Override
    public int getPhase() {
	  return ph;
    }

    @Override
    public void run() {
	  execute();
	  active = false;
    }

    @Override
    public boolean active() {
	  return active;
    }


    protected abstract void execute();

}
