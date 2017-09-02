package com.draniksoft.ome.editor.systems.support;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.state.InactiveC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.components.time.TimeC;
import com.draniksoft.ome.editor.manager.TimeMgr;

public class TimeActivitySys extends IteratingSystem {

    final static String tag = "TimeActivitySys";

    /**
     * Creates a new EntityProcessingSystem.
     */
    public TimeActivitySys() {
        super(Aspect.all(TimeC.class).exclude(InactiveC.class));


    }

    @Override
    protected void initialize() {
        getSubscription().addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
            @Override
            public void inserted(IntBag entities) {

            }

            @Override
            public void removed(IntBag es) {

                for (int i = 0; i < es.size(); i++) {

                    if (tiM.has(es.get(i))) {

                        tiM.remove(es.get(i));

                    }

                }

            }
        });
    }

    ComponentMapper<TInactiveC> tiM;
    ComponentMapper<TimeC> tM;

    TimeMgr tmgr;

    TimeC tTC;

    @Override
    protected void process(int e) {

        if (tmgr.getTime() == -1) {
            return;
        }

        tTC = tM.get(e);

        if (tmgr.isNow(tTC.s, tTC.e)) {

            if (tiM.has(e)) {


                Gdx.app.debug(tag, "Reactivating component " + e);

                tiM.remove(e);

            }

        } else {

            //Gdx.app.debug(tag,"Is not now " + tTC.s + " " + tTC.e);

            if (!tiM.has(e)) {

                Gdx.app.debug(tag, "Deactivating component " + e);

                tiM.create(e);

            }

        }


    }
}
