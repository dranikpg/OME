package com.draniksoft.ome.support.load;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.support.load.observer.LoadLogObserver;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class IntelligentLoader {

    public static final String tag = "IntelligentLoader";

    public static final int LOAD_FAILED = 2;
    public static final int LOAD_SUCCESS = 3;

    private ObserverThread observerThread;

    private volatile long startMs;

    private volatile LoadThread[] ts;
    private volatile byte[] states;
    private int maxTs = 5;
    private int prefTs = 3;
    private int inited_threads = 0;
    private int free_ts = 0;
    private int hg_ts = 0;

    int maxMS = 12;

    int sleepSt = 100;

    private volatile Deque<IRunnable> deq;
    private int log_t = 0;
    private volatile DelayedRemovalArray<IGLRunnable> rs;
    private int gfx_t = 0;

    private ResponseListener l;


    private LoadLogObserver llo;

    private boolean comptSbmt = false;

    public void start() {

        prefTs = Math.min(prefTs, maxTs);

        ts = new LoadThread[maxTs];
        states = new byte[maxTs];

        llo = new LoadLogObserver();

        observerThread = new ObserverThread(this);
        observerThread.start();

        startMs = System.currentTimeMillis();

        Gdx.app.debug(tag, "Started load " + deq.size() + " & " + rs.size);

    }

    /*
    Should be called with GL context
     */

    public void update() {

        if (rs == null) return;

        Iterator<IGLRunnable> i = rs.iterator();

        long ms = System.currentTimeMillis();

        short code;
        while (i.hasNext()) {
            code = i.next().run();
            if (code == IGLRunnable.READY || code == IGLRunnable.TERMINATED) {
                i.remove();
            }

            if (System.currentTimeMillis() - maxMS > 10) {
                return;
            }

        }

    }


    public void terminate() {

        Gdx.app.debug(tag, "Finished " + log_t + " (log_t) :  " + gfx_t + "(gfx_t)  in " + (System.currentTimeMillis() - startMs) + "ms");
        Gdx.app.debug(tag, "Assembled " + inited_threads + " thread[s]");


        for (int i = 0; i < inited_threads; i++) {
            ts[i].terminate();
        }

        observerThread.terminate();

    }

    private void checkCmpt() {

        if (llo.critState()) {

            terminate();

            l.onResponse((short) LOAD_FAILED);

            comptSbmt = true;

        }

        if (rs.size == 0 && deq.size() == 0) {

            for (int i = 0; i < inited_threads; i++) {
                if (states[i] != IRunnable.TERMINATED) return;
            }

            if (!comptSbmt) {
                comptSbmt = true;
                if (l != null) l.onResponse((short) LOAD_SUCCESS);
            }

        }


    }


    private class ObserverThread extends Thread {

        IntelligentLoader l;

        public ObserverThread(IntelligentLoader l) {
            this.l = l;
        }

        boolean terminateR = false;
        boolean GC_RUN = Env.GC_SCHEDULE_FQ;

        @Override
        public void run() {

            while (!terminateR) {

                hg_ts = 0;
                free_ts = 0;

                short firstF = (short) (inited_threads + 1);
                short firstH = (short) (inited_threads + 1);

                byte s;
                for (short i = 0; i < inited_threads; i++) {
                    s = ts[i].get_IR_State();
                    states[i] = s;
                    if (s == IRunnable.HANGUP) {
                        hg_ts++;
                        firstH = (short) Math.min(i, firstH);
                    }
                    if (s == IRunnable.TERMINATED) {
                        free_ts++;
                        firstF = (short) Math.min(i, firstF);
                    }
                }

                checkCmpt();


                if (deq.isEmpty()) {
                    try {
                        Thread.sleep(sleepSt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread.yield();
                    continue;
                }

                log_t++;

                if (free_ts > 0 && inited_threads - free_ts < prefTs) {

                    ts[firstF].passR(deq.poll());

                } else if (inited_threads < prefTs) {

                    ts[inited_threads] = new LoadThread(l);
                    inited_threads++;

                    ts[inited_threads - 1].passR(deq.poll());
                    ts[inited_threads - 1].start();

                } else {

                    log_t--;

                }

                try {
                    Thread.sleep(sleepSt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread.yield();

            }

            if (GC_RUN) {
                Gdx.app.debug(tag, "Invoking GC");
                System.gc();
            }

            System.out.println("Load::observer thread terminated");

        }

        public synchronized void terminate() {
            terminateR = true;
        }
    }

    private class LoadThread extends Thread {

        volatile boolean terminateR = false;

        volatile IRunnable r;

        IntelligentLoader l;

        public LoadThread(IntelligentLoader l) {
            this.l = l;
        }

        @Override
        public void run() {
            while (!terminateR) {
                if (r != null) {
                    r.run(l);
                    r = null;
                } else {
                    Thread.yield();
                }
            }
        }

        public synchronized byte get_IR_State() {

            if (r == null) return IRunnable.TERMINATED;

            return r.getState();

        }

        public synchronized boolean passR(IRunnable pr) {
            if (this.r != null) return false;
            this.r = pr;
            return true;
        }

        public synchronized void terminate() {
            terminateR = true;
        }

    }


    public void passRunnable(IRunnable r) {
        comptSbmt = false;
        deq.add(r);
    }

    public void passGLRunnable(IGLRunnable r) {
        gfx_t++;
        comptSbmt = false;
        rs.add(r);
    }

    public IntelligentLoader() {


        deq = new ArrayDeque<IRunnable>();
        rs = new DelayedRemovalArray<IGLRunnable>();


    }

    public void setCompletionListener(ResponseListener l) {
        this.l = l;
    }

    public void setMaxTs(int maxTs) {
        if (startMs != 0) return;
        this.maxTs = maxTs;
    }

    public void setPrefTs(int prefTs) {
        if (startMs != 0) return;
        this.prefTs = prefTs;
    }

    public LoadLogObserver getLoadLogO() {
        return llo;
    }
}
