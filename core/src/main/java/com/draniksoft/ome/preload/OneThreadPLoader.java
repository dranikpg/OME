package com.draniksoft.ome.preload;

import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.mgmnt_base.AppDataObserver;
import com.draniksoft.ome.utils.ResponseListener;
import com.draniksoft.ome.utils.preload.Initable;

public class OneThreadPLoader implements PreLoader {

    public static final String tag = OneThreadPLoader.class.getSimpleName();

    public static class LoaderRspL implements ResponseListener{

        public static final String tag = "LoaderResponseListener::OneT";

        OneThreadPLoader loader;
        ResponseListener rootL;

        long nanos;

        public LoaderRspL(OneThreadPLoader loader, ResponseListener rootL){
            this.loader = loader;
            this.rootL = rootL;

            nanos = System.nanoTime();
        }

        @Override
        public void onResponse(short code) {

            Gdx.app.debug(tag,"Got response " + code);
            Gdx.app.debug(tag,"Sine last rsp #taken " + (System.nanoTime() - nanos)/10000f);
            nanos = System.nanoTime();

            if(code == Initable.Codes.ERROR){

                loader.stopLoading();

                rootL.onResponse(Codes.ERROR);

            }

        }

    }

    public class LoadRunnable implements Runnable{

        public volatile boolean running = true;

        @Override
        public void run() {

            millis = System.currentTimeMillis();

            //

            AppDataObserver.getI().init(myL,false);
            if (!running)return;


            AppDataObserver.getI().L().init(myL,false);
            if(!running)return;


            //

            Gdx.app.debug(tag,"Overall #taken " + (System.currentTimeMillis()-millis));

            rootL.onResponse(Codes.READY);

        }

    }

    ResponseListener rootL;
    ResponseListener myL;

    Thread t;
    LoadRunnable loadR;

    long millis;

    @Override
    public void init(ResponseListener l) {
        this.rootL = l;

        Gdx.app.debug(tag,"Initing");

        myL = new LoaderRspL(this,rootL);

    }

    @Override
    public void startLoading() {

        loadR = new LoadRunnable();

        t = new Thread(loadR);
        t.setName("LoadThread");
        t.start();

        Gdx.app.debug(tag,"Started load thread");

    }

    @Override
    public void stopLoading() {

        if(loadR != null){
            loadR.running = false;
            Gdx.app.debug(tag,"Stopping loading thread");
        }

    }
}
