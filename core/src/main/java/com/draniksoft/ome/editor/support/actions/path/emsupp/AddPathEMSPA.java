package com.draniksoft.ome.editor.support.actions.path.emsupp;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.struct.path.runtime.Path;
import com.draniksoft.ome.editor.struct.path.runtime.TranslationTable;
import com.draniksoft.ome.editor.struct.path.srz.PathSzr;
import com.draniksoft.ome.editor.struct.path.transform.PathTransformer;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.utils.struct.Points;
import org.jetbrains.annotations.NotNull;

public class AddPathEMSPA implements Action, PathEMSupportiveAction {

    private static String tag = "AddPathEMSPA";

    int _e;

    PathSzr orig;
    Path origPP;

    @Override
    public Action self() {
	  return this;
    }

    @Override
    public void ifor(int e) {
	  _e = e;
    }

    @Override
    public void prepocess(World w, PathDescC descC, PathRunTimeC rtC) {

	  orig = new PathSzr();
	  orig.pts = new Points();
	  origPP = new Path();
	  origPP.pts = new Points();
	  origPP.tb = new TranslationTable();

	  addFirst(w, descC);

	  descC.ar.add(orig);
	  rtC.ar.add(origPP);

    }

    public void addFirst(World w, PathDescC descC) {
	  Vector2 p = new Vector2();
	  if (descC.ar.size == 0) {
		w.getSystem(PositionSystem.class).getCenterPosV(_e, p);
	  } else {
		p.set(descC.ar.get(descC.ar.size - 1).pts.last());
	  }
	  orig.pts.add(p);

    }

    @Override
    public void discard(World w, PathDescC descC, PathRunTimeC rtC) {
	  descC.ar.removeValue(orig, true);
	  rtC.ar.removeValue(origPP, true);

	  // TODO throw event

    }

    @Override
    public void invoke(@NotNull World w) {
	  Gdx.app.debug(tag, "Processing");
	  PathTransformer t = new PathTransformer();
	  t.init(orig.pts);
	  t.calc(origPP.pts, origPP.tb);

	  // TODO throw event

    }

    @Override
    public void undo(@NotNull World w) {

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
	  return "Added path";

    }

    @Override
    public void destruct() {

    }


}
