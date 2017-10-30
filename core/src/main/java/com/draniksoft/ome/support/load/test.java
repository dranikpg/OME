package com.draniksoft.ome.support.load;

import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.ArrayList;
import java.util.Set;

public class test {

    static int mt = 1;
    static int pt = Math.min(mt, 1);
    static int i = 1000;

    static IntelligentLoader l;

    public static void main(String[] args) {

        Thread.currentThread().setName("MainT");

        l = new IntelligentLoader();


        l.setMaxTs(mt);
        l.setPrefTs(pt);

        l.setCompletionListener(new ResponseListener() {
            @Override
            public void onResponse(short code) {
                l.terminate();

                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                Thread[] ta = threadSet.toArray(new Thread[threadSet.size()]);


            }
        });

        addTasks();

        l.start();


    }

    private static void addTasks() {

        while (i-- >= 0) {

            l.passRunnable(new HeavyWRunnavle(10));
            l.passRunnable(new SleepRunnable(10));

        }

    }

    public static class HeavyWRunnavle implements IRunnable {

        int wl = 1;

        ArrayList<Integer> lst;

        public HeavyWRunnavle(int wl) {
            this.wl = wl;
        }

        @Override
        public void run(IntelligentLoader l) {

            for (int i = 0; i < wl * 1e3; i++) {
                lst = new ArrayList<Integer>();
                lst.add((int) (i / 3232 * 2312 * Math.cos(312312) / 3124f));
                lst.add((int) (i / 31231 * 5345 * Math.sin(312312) / 4234));

            }

        }

        @Override
        public byte getState() {
            return 0;
        }
    }

    public static class SleepRunnable implements IRunnable {

        int t;

        public SleepRunnable(int t) {
            this.t = t;
        }

        @Override
        public void run(IntelligentLoader l) {
            try {
                Thread.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public byte getState() {
            return IRunnable.HANGUP;
        }
    }


}
