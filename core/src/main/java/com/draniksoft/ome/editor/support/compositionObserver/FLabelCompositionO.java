package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.selection.FLabelC;
import com.draniksoft.ome.editor.support.actions.flabel.CreateFLabelC;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.ui.comp_obs_ts.FlabelT;

public class FLabelCompositionO extends SimpleCompositionObserver {

    private static final String tag = "FLabelCompositionO";

    IntMap<ActionDesc> ds;

    @Override
    protected void on_selchanged(boolean previousActivity) {

    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

        Gdx.app.debug(tag, "Comp changed");

    }

    @Override
    protected void _init() {

        ds = new IntMap<ActionDesc>();

        if (__ds != null) {

            for (ActionDesc d : __ds) {

                ds.put(d.code, d);

            }

        }

    }

    @Override
    protected Aspect.Builder getAspectB() {
        return Aspect.all(FLabelC.class, PosSizeC.class);
    }

    @Override
    public IntMap<ActionDesc> getDesc() {
        return ds;
    }

    @Override
    public boolean isAviab(int ac, int e) {

        if (ac == ActionDesc.BaseCodes.ACTION_CREATE) {

            return !matches(e);

        } else {

            return matches(e);

        }

    }

    @Override
    public boolean isAviab(int ac) {
        return isAviab(ac, _selE);
    }

    @Override
    public ActionDesc getDesc(int ac) {
        return ds.get(ac);
    }

    @Override
    public void execA(int id, int _e, Object... os) {

        if (id == ActionDesc.BaseCodes.ACTION_CREATE) {

            CreateFLabelC a = new CreateFLabelC();
            a._e = _e;

            _w.getSystem(ActionSystem.class).exec(a);

        }

    }

    @Override
    public String getName() {
        return "Flabel";
    }

    @Override
    public Table getSettingsT() {
        return new FlabelT(_w, _selE);
    }
}
