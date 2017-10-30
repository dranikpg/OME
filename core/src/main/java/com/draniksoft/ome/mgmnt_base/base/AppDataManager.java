package com.draniksoft.ome.mgmnt_base.base;

import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IRunnable;

public abstract class AppDataManager implements IRunnable {

    protected static byte NULL_PTR = 100;
    public static byte STARTUP_LOAD = 101;
    public static byte ENGINE_LOAD = 102;
    public static byte TERMINATE_RUN = 103;

    protected byte state = NULL_PTR;
    protected byte load_s = IRunnable.RUNNING;

    public void setLoadState(byte s) {
        if (s < NULL_PTR) {
            state = (byte) (s + NULL_PTR);
        }
        state = s;
    }

    @Override
    public final void run(IntelligentLoader l) {

        if (state == STARTUP_LOAD) {

            startupLoad(l);

        } else if (state == ENGINE_LOAD) {

            engineLoad(l);

        } else if (state == TERMINATE_RUN) {

            terminateLoad();

        }

    }

    @Override
    public byte getState() {
        return load_s;
    }

    protected abstract void startupLoad(IntelligentLoader l);

    protected abstract void engineLoad(IntelligentLoader l);

    protected abstract void terminateLoad();

}
