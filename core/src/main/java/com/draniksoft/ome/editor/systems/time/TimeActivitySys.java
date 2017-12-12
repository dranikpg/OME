package com.draniksoft.ome.editor.systems.time;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.draniksoft.ome.editor.components.state.InactiveC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class TimeActivitySys extends IteratingSystem {

    final static String tag = "TimeActivitySys";

    /**
     * Creates a new EntityProcessingSystem.
     */
    public TimeActivitySys() {
	  super(Aspect.exclude(InactiveC.class));

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


    TimeMgr tmgr;


    @Override
    protected void process(int e) {


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
