package com.draniksoft.ome.editor.support.actions.path;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.container.path.PathSDesc;
import com.draniksoft.ome.editor.systems.time.ObjTimeCalcSys;
import org.jetbrains.annotations.NotNull;

public class CommitPathChangeA implements Action {

    public PathSDesc dsc;
    public Array<Vector2> orig;

    public int _e;

    @Override
    public void invoke(@NotNull World w) {

    }

    @Override
    public void undo(@NotNull World w) {
	  PathDescC c = w.getMapper(PathDescC.class).get(_e);

	  int idx = c.ar.indexOf(dsc, true);
	  dsc.ar = orig;

	  if (c.ar.size > idx + 1) {
		if (c.ar.get(idx + 1).alignToPrev) {
		    Vector2 lastP = orig.get(orig.size - 1);
		    c.ar.get(idx + 1).ar.get(0).set(lastP.x, lastP.y);
		    w.getSystem(ObjTimeCalcSys.class).processEntityPath(_e, idx + 1);
		}
	  }

	  w.getSystem(ObjTimeCalcSys.class).processEntityPath(_e, idx);
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
	  return "Path chane";
    }

    @Override
    public void destruct() {

    }
}
