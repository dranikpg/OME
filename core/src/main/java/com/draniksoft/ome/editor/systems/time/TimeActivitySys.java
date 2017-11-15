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
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.utils.struct.Pair;
import dint.Dint;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

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

        world.getSystem(EventSystem.class).registerEvents(this);
    }

    ComponentMapper<TInactiveC> tiM;
    ComponentMapper<TimedC> tM;

    ComponentMapper<TimedMoveC> tmM;

    TimeMgr tmgr;


    @Override
    protected void process(int e) {

        if (tM.has(e))
            processTimeC(tM.get(e), e);

        if (tmM.has(e)) {
            processTMoveC(tmM.get(e), e);
        }

    }

    private void processTMoveC(TimedMoveC c, int e) {


        float lx, ly;

	  Pair<Float, Float> pp = world.getSystem(PhysicsSys.class).getPhysPos(e);
	  lx = pp.getElement0();
        ly = pp.getElement1();

        for (MoveDesc d : c.a) {

            if (tmgr.getTime() > d.time_e) {
                lx = d.end_x;
                ly = d.end_y;
                d.rt_sx = -1;

            } else if (tmgr.isNow(d.time_s, d.time_e)) {

                if (d.rt_sx < 0) {
                    if (d.start_x < 0) {
                        d.rt_sx = (int) lx;
                        d.rt_sy = (int) ly;
                    } else {
                        d.rt_sx = d.start_x;
                        d.rt_sy = d.start_y;
                    }
                }

                float dif1 = Dint.diff(tmgr.getTime(), d.time_s);
                float dif2 = Dint.diff(d.time_e, d.time_s);

                float psd = dif1 / dif2;
                psd = Math.min(psd, 1);


                float dx = ((float) d.end_x - d.rt_sx) * psd;
                float dy = ((float) d.end_y - d.rt_sy) * psd;

		    world.getSystem(PhysicsSys.class).setSyncCentricPos(d.rt_sx + dx,
				d.rt_sy + dy,
                        e);


                return;

            }

        }

        if (lx == -1 || ly == -1) return;

	  //world.getSystem(PhysicsSys.class).setSyncCentricPos(lx, ly, time_e);


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


    @Subscribe
    public void modeChanged(ModeChangeE e) {

        setEnabled(e.SHOW_MODE);

        if (!e.SHOW_MODE) {
            clearRTPS();
        }

    }

    private void clearRTPS() {


    }
}
