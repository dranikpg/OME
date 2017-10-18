package com.draniksoft.ome.editor.support.compositionObserver.timed;

import com.artemis.Aspect;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.time.TimedC;
import com.draniksoft.ome.editor.support.actions.timed._base_.AddTimeCA;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.ui.comp_obs_ts.TimedOTable;

public class TimedCompositionO extends SimpleCompositionObserver {


    IntMap<ActionDesc> desc;


    @Override
    protected void on_selchanged(boolean previousActivity) {

    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

    }

    @Override
    protected void _init() {

        desc = new IntMap<ActionDesc>();

        if (__ds == null) return;

        for (ActionDesc d : __ds) {
            desc.put(d.code, d);
        }

    }


    @Override
    public IntMap<ActionDesc> getDesc() {
        return desc;
    }

    @Override
    public boolean isAviab(int ac, int e) {
        if (ac == ActionDesc.BaseCodes.ACTION_CREATE) {
            return !matches(e);
        } else if (ac == ActionDesc.BaseCodes.ACTION_DELETE || ac == ActionDesc.BaseCodes.ACTION_RESET) {
            return matches(e);
        }

        return true;
    }

    @Override
    public boolean isAviab(int ac) {
        return isAviab(ac, _selE);
    }

    @Override
    public ActionDesc getDesc(int ac) {
        return desc.get(ac);
    }

    @Override
    public void execA(int id, int _e, Object... os) {

        if (id == ActionDesc.BaseCodes.ACTION_CREATE) {

            AddTimeCA a = new AddTimeCA();
            a._e = _e;
            a.se = 0;
            a.ee = 0;
            _w.getSystem(ActionSystem.class).exec(a);

        }

    }

    @Override
    public String getName() {
        return "Timed C";
    }

    @Override
    public Table getSettingsT() {
        TimedOTable t = new TimedOTable();
        t.init(_selE, _w);
        return t;
    }

    @Override
    protected Aspect.Builder getAspectB() {
        return Aspect.all(TimedC.class);
    }
}
