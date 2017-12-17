package com.draniksoft.ome.editor.systems.time;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.components.state.InactiveC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.support.container.path.PathDesc;
import com.draniksoft.ome.editor.support.container.path.PathRTDesc;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class ObjTimeCalcSys extends IteratingSystem {

    final static String tag = "ObjTimeCalcSys";


    public ObjTimeCalcSys() {
        super(Aspect.exclude(InactiveC.class));

        sp = new CatmullRomSpline<Vector2>();
        tmpV = new Vector2();
    }


    int pixelPerPathPoint = 30;
    int lenSamples = 50;

    Vector2 tmpV;

    ComponentMapper<TInactiveC> tiM;

    ComponentMapper<PathDescC> pSM;
    ComponentMapper<PathRunTimeC> pRM;

    CatmullRomSpline<Vector2> sp;



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

    @Override
    protected void process(int e) {


    }

    @Subscribe
    public void modeChanged(ModeChangeE e) {

        setEnabled(e.SHOW_MODE);

    }

    public void processEntityPathC(final ResponseListener l, final int _e) {
        new Thread() {
            @Override
            public void run() {
                processEntityPathC(_e);
                l.onResponse(ResponseCode.SUCCESSFUL);
            }
        }.start();
    }

    public void processEntityPath(int _e, int idx) {
        processEntityPath(_e, idx, 1f);
    }

    public void processEntityPath(int _e, int idx, float precF) {

        PathDescC dc = pSM.get(_e);
        PathRunTimeC tc = pRM.get(_e);

        if (tc.p.get(idx) != null) {
            processPathD(dc.ar.get(idx), tc.p.get(idx), precF);
        } else {
            PathRTDesc d = new PathRTDesc();
            processPathD(dc.ar.get(idx), d, precF);
            tc.p.set(idx, d);
        }
    }

    public void processEntityPathC(int _e) {
        PathDescC dc = pSM.get(_e);
        PathRunTimeC tc = pRM.has(_e) ? pRM.get(_e) : pRM.create(_e);
        if (tc.p == null) tc.p = new Array<PathRTDesc>();
        tc.p.setSize(dc.ar.size);
        for (int i = 0; i < dc.ar.size; i++) {
            if (tc.p.get(i) != null) {
                processPathD(dc.ar.get(i), tc.p.get(i));
            } else {
                PathRTDesc d = new PathRTDesc();
                processPathD(dc.ar.get(i), d);
                tc.p.set(i, d);
            }
        }
    }

    public void processPathD(PathDesc src, PathRTDesc dst) {
        processPathD(src, dst, 1f);
    }

    public void processPathD(PathDesc src, PathRTDesc dst, float precF) {
        dst.st = src.st;
        dst.et = src.et;

        if (src.ar.size == 1) {
            dst.ar.add(new Vector2(src.ar.get(0)));
            return;
        } if (src.ar.size < 2) {
            return;
        }

        Array<Vector2> res = new Array<Vector2>(src.ar.size + 2);
        res.add(src.ar.get(0));
        res.addAll(src.ar);
        res.add(src.ar.get(src.ar.size - 1));

        sp.set((Vector2[]) res.toArray(Vector2.class), false);

        int k = (int) (sp.approxLength(src.ar.size) / (pixelPerPathPoint) * precF);
        dst.ar.clear();
        dst.ar.ensureCapacity(k);
        dst.pixelPerPathPoint = pixelPerPathPoint;

        for (int i = 0; i <= k; i++) {
            tmpV = sp.valueAt(tmpV, i * 1f / k);
            dst.ar.add(new Vector2(tmpV.x, tmpV.y));
        }

    }

    public void removePathIdx(int _e, int id) {
        PathDescC dc = pSM.get(_e);
        PathRunTimeC tc = pRM.get(_e);
        dc.ar.removeIndex(id);
        tc.p.removeIndex(id);
    }
}
