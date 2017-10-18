package com.draniksoft.ome.editor.support.actions.timed._base_;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.time.TimedC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class ChangeTimedCValsA implements Action {

    public int s, e;

    int os, oe;

    public int _e;

    @Override
    public void _do(World w) {


        ComponentMapper<TimedC> m = w.getMapper(TimedC.class);


        if (!m.has(_e)) return;

        TimedC c = m.get(_e);
        os = c.s;
        oe = c.e;

        setVals(s, e, w);

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.TimedValsChange(_e));

    }

    public void setVals(int sv, int ev, World w) {

        Gdx.app.debug("T", "to " + sv + " " + ev);

        ComponentMapper<TimedC> m = w.getMapper(TimedC.class);

        if (!m.has(_e)) return;

        TimedC c = m.get(_e);
        c.e = ev;
        c.s = sv;


        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.TimedValsChange(_e));

    }

    @Override
    public void _undo(World w) {

        setVals(os, oe, w);

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.TimedValsChange(_e));
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }

    @Override
    public String getSimpleConcl() {
        return "Changed Timed vals";
    }

    @Override
    public void destruct() {

    }
}
