package com.draniksoft.ome.editor.support.compositionObserver.timed;

import com.artemis.Aspect;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.support.actions.timed.move.AddTimedMoveCA;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.comp_obs_ts.TimedMovsTable;

public class TimedMoveCompositionO extends SimpleCompositionObserver {


    IntMap<ActionDesc> desc;

    @Override
    protected void on_selchanged(boolean previousActivity) {

        updateEM();

    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

        updateEM();
    }

    private void updateEM() {
        _w.getSystem(EditorSystem.class).setEMDavbt(EditModeDesc.IDS.editTimedMoves,
                isSelActive() ? 1 : 3);
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
    protected Aspect.Builder getAspectB() {
        return Aspect.all(TimedMoveC.class);
    }

    @Override
    public IntMap<ActionDesc> getDesc() {
        return desc;
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
        return desc.get(ac);
    }

    @Override
    public void execA(int id, int _e, Object... os) {

        if (id == ActionDesc.BaseCodes.ACTION_CREATE) {

            AddTimedMoveCA a = new AddTimedMoveCA();
            a._e = _e;

            _w.getSystem(ActionSystem.class).exec(a);

        }


    }

    @Override
    public String getName() {
        return "Timed Move";
    }

    @Override
    public Table getSettingsT() {

        TimedMovsTable stxt = new TimedMovsTable();
        stxt.init(_w, _selE);

        return stxt;
    }
}
