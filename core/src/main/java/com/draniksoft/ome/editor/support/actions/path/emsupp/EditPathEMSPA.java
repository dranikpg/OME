package com.draniksoft.ome.editor.support.actions.path.emsupp;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.struct.path.runtime.Path;
import com.draniksoft.ome.editor.struct.path.srz.PathSzr;
import com.draniksoft.ome.editor.struct.path.transform.PathTransformer;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.utils.struct.Points;
import org.jetbrains.annotations.NotNull;

public class EditPathEMSPA implements Action, PathEMSupportiveAction {

    private static String tag = "EditPathEMSPA";

    int _e;
    public int i;

    Path origPP;
    PathSzr orig;

    Points pts;

    public EditPathEMSPA() {
	  pts = new Points();
    }

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
	  Gdx.app.debug(tag, "preprocessing");
	  origPP = rtC.ar.get(i);
	  orig = descC.ar.get(i);
	  pts.deepCopy(orig.pts);

    }

    @Override
    public void discard(World w, PathDescC descC, PathRunTimeC rtC) {
	  Gdx.app.debug(tag, "Discarding");
	  PathTransformer t = new PathTransformer();
	  t.init(pts);
	  t.calc(origPP.pts, origPP.tb);

    }

    @Override
    public void invoke(@NotNull World w) {
	  Gdx.app.debug(tag, "Processing final");
	  PathTransformer t = new PathTransformer();
	  t.init(orig.pts);
	  t.calc(origPP.pts, origPP.tb);
    }

    @Override
    public void undo(@NotNull World w) {
	  Gdx.app.debug(tag, "Undoing");
	  PathTransformer t = new PathTransformer();
	  t.init(pts);
	  t.calc(origPP.pts, origPP.tb);
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
	  return "Edited path";
    }

    @Override
    public void destruct() {

    }

}
