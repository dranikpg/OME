package com.draniksoft.ome.editor.res.path.constr;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.path.b.PathTranslationTable;
import com.draniksoft.ome.utils.struct.Pair;
import com.draniksoft.ome.utils.struct.Points;

/*
	Class for intelligent path constructing, translation table matching, caching

 	TODO EM CLEANUP !!!!

 */


public class PathConstructor {

    private static final String tag = "PathConstructor";

    Points touchP;

    PathTranslationTable tt;
    CatmullRomSpline<Vector2> spline;

    Points pt;
    Array<Pair<Vector2, Vector2>> meshP;

    public PathConstructor() {

	  spline = new CatmullRomSpline<Vector2>();

	  pt = new Points();
	  touchP = new Points();

	  tt = new PathTranslationTable();
    }

    //

    public Points getTP() {
	  return touchP;
    }

    //

    public void addTP(Vector2 p) {
	  touchP.addAll(p);
    }

    public void removeTP(int id) {
	  touchP.removeIndex(id);
    }

    public void posUpdate(int id) {
	  rebuild();
    }

    //

    private void updateFrom() {
	  rebuild();
    }

    //

    public void rebuild() {


    }


}
