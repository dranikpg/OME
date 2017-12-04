package com.draniksoft.ome.editor.support.actions.mapO;

import com.artemis.World;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.utils.struct.Pair;
import net.mostlyoriginal.api.event.common.EventSystem;

public class MoveMOA implements Action {

    public int _e;
    public float x, y;

    Pair<Float, Float> oldP;

    public MoveMOA(int _e, float x, float y) {
        this._e = _e;
        this.x = x;
        this.y = y;
    }

    public MoveMOA() {
    }

    @Override
    public void invoke(World w) {

	  oldP = w.getSystem(PhysicsSys.class).getPhysPos(_e);

        w.getSystem(PhysicsSys.class).saveMOSyncPos(x, y, _e);

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.MOPositonChangeE(_e));

    }

    @Override
    public void undo(World w) {

        w.getSystem(PhysicsSys.class).saveMOSyncPos(oldP.getElement0(), oldP.getElement1(), _e);

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.MOPositonChangeE(_e));
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
        return "Changed Object positon";
    }

    @Override
    public void destruct() {

    }
}
