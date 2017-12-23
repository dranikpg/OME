package com.draniksoft.ome.editor.systems.time;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.components.state.InactiveC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.support.container.path.PathDesc;
import com.draniksoft.ome.editor.support.container.path.PathRTDesc;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.utils.iface.Awaitable;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;
import dint.Dint;
import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.system.core.SpreadProcessingSystem;

import static com.draniksoft.ome.editor.manager.TimeMgr.TT.*;

public class ObjTimeCalcSys extends SpreadProcessingSystem {

    final static String tag = "ObjTimeCalcSys";


    public ObjTimeCalcSys() {
	  super(Aspect.exclude(InactiveC.class), 1 / 20f);

        sp = new CatmullRomSpline<Vector2>();
        tmpV = new Vector2();
    }


    int pathPointsPerPoint = 5;
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
	  world.getSystem(OmeEventSystem.class).registerEvents(this);
    }

    @Override
    protected void process(int e) {
        int t = world.getSystem(TimeMgr.class).getTime();
        if (pRM.has(e)) {
            timeCalcPath(e);
        }
    }


    @Subscribe
    public void modeChanged(ModeChangeE e) {
        if (e instanceof ModeChangeE.ShowQuitEvent) {
            setEnabled(false);
        } else if (e instanceof ModeChangeE.ShowEnterEvent) {
            setEnabled(true);
        } else if (e instanceof ModeChangeE.ShowRequestEvent) {
            reload((Awaitable) e);
        }
    }

    private void reload(Awaitable aw) {
        aw.await();
        InitOnTimeThread t = new InitOnTimeThread();

        t.t = world.getSystem(TimeMgr.class).getTime();
        t.aw = aw;

        t.start();
    }

    private class InitOnTimeThread extends Thread {
        public int t;
        public Awaitable aw;

        public InitOnTimeThread() {
            setName("ObjTimeCalcSys :: RELOAD");
        }

        @Override
        public void run() {
            Gdx.app.debug(tag + " :: LoadThread", "Started entity preprocessing");

            IntBag bag = ObjTimeCalcSys.this.getEntityIds();

            int idx;
            PathRunTimeC c;

            for (int i = 0; i < bag.size(); i++) {

                if (pRM.has(bag.get(i))) {

                    c = pRM.get(bag.get(i));

                    idx = findPathIdxForTime(c, t);

                    c.idx = idx;

                }

            }

            Gdx.app.debug(tag + " :: LoadThread", "Finished");
            aw.ready();

        }
    }

    /*
        Online Entity processing
     */

    PathRunTimeC rtc;

    /*
        Determine the given pos for T
     */

    private void timeCalcPath(int _e) {
        rtc = pRM.get(_e);
        if (rtc.idx == -1) return;

        PathRTDesc d = rtc.p.get(rtc.idx);

        if (d.st > _T) return;

        int len = Dint.diff(d.et, d.st);

        float p1 = (Dint.diff(_T, d.st) * 1f / len);
        float mfract = _TSTEP * 1f / len;
        float p2 = mfract * _RP;
        float p = p1 + p2;

        int pI = Math.min((int) (d.ar.size * p), d.ar.size - 1);

        Vector2 v = d.ar.get(pI);

        if (pI + 1 < d.ar.size) {
            tmpV.set(v);
        } else {
            tmpV.set(v);
        }

        world.getSystem(PositionSystem.class).setByCenterPos(_e, (int) tmpV.x, (int) tmpV.y);
    }


    /*
        Preparation of online entity processing
     */

    private int findPathIdxForTime(PathRunTimeC c, int t) {
        // TODO replace with binsearch
        int cn = 0;
        for (PathDesc d : c.p) {
            if (t > d.et) {
                cn++;
            } else {
                return cn;
            }
        }
        return -1;

    }
    /*
        Basic entity path processing
     */

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

	  Gdx.app.debug(tag, "Processin " + _e + " " + idx + " PRC:: " + precF);

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

    private void processPathD(PathDesc src, PathRTDesc dst) {
        processPathD(src, dst, 1f);
    }

    private void processPathD(PathDesc src, PathRTDesc dst, float precF) {
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

	  int k = (int) (res.size * pathPointsPerPoint * precF);
	  dst.ar.clear();
	  dst.ar.ensureCapacity(k);
	  dst.pathPointsPerPoint = pathPointsPerPoint;

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
