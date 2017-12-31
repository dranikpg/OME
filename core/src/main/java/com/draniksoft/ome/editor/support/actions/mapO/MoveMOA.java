package com.draniksoft.ome.editor.support.actions.mapO;

import com.artemis.World;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.utils.struct.Pair;

public class MoveMOA implements Action {

    public int _e;
    public int x, y;
    public boolean center = false;

    Pair<Integer, Integer> oldP;

    PositionSystem sys;

    public MoveMOA(int _e, int x, int y, boolean center) {
	  this._e = _e;
        this.x = x;
        this.y = y;
    }

    public MoveMOA() {
    }

    @Override
    public void invoke(World w) {
	  sys = w.getSystem(PositionSystem.class);
	  oldP = sys.getCornerPos(_e);
	  if (center) {
		sys.setByCenterPos(_e, x, y);
	  } else {
		sys.setByCornerPos(_e, x, y);
	  }
	  sys.save(_e);
	  w.getSystem(OmeEventSystem.class).dispatch(new EntityDataChangeE.MOPositonChangeE(_e));
    }

    @Override
    public void undo(World w) {
	  sys.setByCornerPos(_e, oldP.K(), oldP.K());
	  sys.save(_e);
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
	  oldP = null;
    }
}
