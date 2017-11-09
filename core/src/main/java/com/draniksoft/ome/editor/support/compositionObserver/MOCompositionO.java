package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;

public class MOCompositionO extends SimpleCompositionObserver {

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
        } else if (ac == ActionDesc.BaseCodes.ACTION_RESET) {
            return matches(e);
        }

        return false;

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

    }

    @Override
    public String getName() {
        return "Map Object";
    }

    @Override
    public Table getSettingsT() {
        return null;
    }


    @Override
    protected Aspect.Builder getAspectB() {
        return Aspect.all(MObjectC.class, PosSizeC.class, DrawableC.class, PhysC.class);
    }
}
