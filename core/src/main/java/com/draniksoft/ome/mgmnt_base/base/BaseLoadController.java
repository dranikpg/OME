package com.draniksoft.ome.mgmnt_base.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.mgmnt_base.impl.ConfigManager;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.observer.LoadLogInfo;
import com.draniksoft.ome.utils.struct.ResponseListener;

import static com.draniksoft.ome.support.load.IntelligentLoader.LOAD_FAILED;
import static com.draniksoft.ome.support.load.IntelligentLoader.LOAD_SUCCESS;

public class BaseLoadController {

    private static String tag = "BaseLoadController";

    IntelligentLoader l;


    enum LoadState {
        AppDO, Lang, CONFIG, MISC, NULL_PTR
    }

    LoadState s = LoadState.AppDO;

    ResponseListener ls;

    public void startLoad(IntelligentLoader l, ResponseListener lstr) {
        this.l = l;
        this.ls = lstr;

        l.setCompletionListener(new ResponseListener() {
            /*
            Called on some other thread
             */
            @Override
            public void onResponse(short code) {

                if (code == LOAD_SUCCESS) {


                    s = LoadState.values()[s.ordinal() + 1];

                    updateLoad();

                } else {

                    critError();

                }

            }
        });

        AppDO.I.setLoadState(AppDataManager.STARTUP_LOAD);

        l.passRunnable(AppDO.I);

        l.start();

    }

    private void critError() {

        Gdx.app.error(tag, "Critical  crash");
        Gdx.app.error(tag, l.getLoadLogO().getCrit().toString());

        printWgs();

        ls.onResponse((short) LOAD_FAILED);

    }

    private void updateLoad() {

        Gdx.app.debug(tag, "State :: " + s.name());

        if (s == LoadState.NULL_PTR) {

            l.terminate();

            printWgs();

            ls.onResponse((short) LOAD_SUCCESS);
        } else if (s == LoadState.Lang) {
            AppDO.I.L().setLoadState(AppDataManager.STARTUP_LOAD);
            l.passRunnable(AppDO.I.L());
        } else if (s == LoadState.CONFIG) {
            AppDO.I.getMgr(ConfigManager.class).setLoadState(AppDataManager.STARTUP_LOAD);
            l.passRunnable(AppDO.I.getMgr(ConfigManager.class));
        } else if (s == LoadState.MISC) {
            AppDO.I.F().setLoadState(AppDO.STARTUP_LOAD);
            l.passRunnable(AppDO.I.F());
            AppDO.I.LH().setLoadState(AppDataManager.STARTUP_LOAD);
            l.passRunnable(AppDO.I.LH());
		AppDO.I.LML().setLoadState(AppDataManager.STARTUP_LOAD);
		l.passRunnable(AppDO.I.LML());
	  }

    }

    private void printWgs() {

        Gdx.app.log(tag, l.getLoadLogO().getWarnings().size + " warnings ");
        for (LoadLogInfo i : l.getLoadLogO().getWarnings()) {
            Gdx.app.debug(tag, i.toString());
        }

    }


    public void updateGL() {

        l.update();

    }

    public Array<LoadLogInfo> getLoadWrgs() {

        return l.getLoadLogO().getWarnings();

    }

    public boolean hasCritE() {

        return l.getLoadLogO().critState();

    }

    public LoadLogInfo getCrit() {
        return l.getLoadLogO().getCrit();
    }

}
