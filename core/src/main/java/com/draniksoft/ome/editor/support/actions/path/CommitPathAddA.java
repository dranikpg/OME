package com.draniksoft.ome.editor.support.actions.path;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.path.b.PathSDesc;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.time.ObjTimeCalcSys;
import org.jetbrains.annotations.NotNull;

public class CommitPathAddA implements Action {


    public PathSDesc d;
    public Array<PathSDesc> ar;

    public int _e;

    @Override
    public void invoke(@NotNull World w) {

    }

    @Override
    public void undo(@NotNull World w) {

	  int i = ar.indexOf(d, true);

	  if (ar.size > i + 1) {
		Vector2 last = null;
		if (i > 1) {
		    PathSDesc d = ar.get(i - 1);
		    last = d.ar.get(d.ar.size - 1);
		} else {
		    last = w.getSystem(PositionSystem.class).getCenterV(_e);
		}
		PathSDesc af = ar.get(i + 1);
		if (af.alignToPrev) {
		    af.ar.get(0).set(last);
		    w.getSystem(ObjTimeCalcSys.class).processEntityPath(_e, i + 1);
		}
	  }

	  w.getSystem(ObjTimeCalcSys.class).removePathIdx(_e, i);

	  w.getSystem(OmeEventSystem.class).dispatch(new EntityDataChangeE.PathCountChangeE(_e));
    }

    @Override
    public boolean isUndoable() {
	  return true;
    }

    @Override
    public boolean isCleaner() {
	  return false;
    }

    @NotNull
    @Override
    public String getSimpleConcl() {
	  return "Commit path";
    }

    @Override
    public void destruct() {

    }
}
