package com.draniksoft.ome.editor.systems.time;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.state.InactiveC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.components.time.TimedC;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.utils.struct.Pair;
import dint.Dint;

public class TimeActivitySys extends IteratingSystem {

    final static String tag = "TimeActivitySys";

    /**
     * Creates a new EntityProcessingSystem.
     */
    public TimeActivitySys() {
        super(Aspect.one(TimedC.class, TimedMoveC.class).exclude(InactiveC.class));

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


        setEnabled(false);
    }

    ComponentMapper<TInactiveC> tiM;
    ComponentMapper<TimedC> tM;

    ComponentMapper<TimedMoveC> tmM;

    TimeMgr tmgr;


    @Override
    protected void process(int e) {

        if (tmgr.getTime() == -1) {
            return;
        }


        if (tM.has(e))
            processTimeC(tM.get(e), e);

        if (tmM.has(e)) {
            processTMoveC(tmM.get(e), e);
        }

    }

    private void processTMoveC(TimedMoveC c, int e) {


        int lx = -1;
        int ly = -1;


        for (MoveDesc d : c.a) {

            if (tmgr.getTime() > d.e) {
                lx = d.x;
                ly = d.y;
            }

            if (tmgr.isNow(d.s, d.e)) {

                float psd = (((float) Dint.diff(tmgr.getTime(), d.s)) +
                        Math.min(d.e == tmgr.getTime() ? 0 : 1f, tmgr.getStepPrecent())) / ((float) Dint.diff(d.e, d.s));

                Pair<Integer, Integer> p = Pair.createPair(d.sx, d.sy);

                float dx = ((float) d.x - p.getElement0()) * psd;
                float dy = ((float) d.y - p.getElement1()) * psd;

                world.getSystem(PhysicsSys.class).setSyncPos(p.getElement0() + dx,
                        p.getElement1() + dy,
                        e);

                return;

            }

        }

        if (lx == -1 || ly == -1) return;
        world.getSystem(PhysicsSys.class).setSyncPos(lx, ly, e);


    }

    private void processTimeC(TimedC c, int e) {

        if (tmgr.isNow(c.s, c.e)) {
            if (tiM.has(e)) {
                Gdx.app.debug(tag, "Reactivating component " + e);
                tiM.remove(e);
            }
        } else {
            if (!tiM.has(e)) {
                Gdx.app.debug(tag, "Deactivating component " + e);
                tiM.create(e);
            }

        }
    }
}
