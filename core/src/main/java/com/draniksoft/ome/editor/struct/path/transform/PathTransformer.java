package com.draniksoft.ome.editor.struct.path.transform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.draniksoft.ome.editor.struct.path.runtime.TranslationTable;
import com.draniksoft.ome.utils.struct.Points;

/*
	TODO update and add functionality instead of full recalculation
 */

/*
	My machine does about 1000-1500 points in one ms
 */

public class PathTransformer {

    private static String tag = "PathTransformer";

    Points touches;

    int pixelsPerPoint = 40;
    float transPointsPerPoint = 2;

    CatmullRomSpline<Vector2> sp;


    public PathTransformer() {
	  sp = new CatmullRomSpline<Vector2>();
    }

    public void init(Points touches) {
	  this.touches = touches;
    }

    public void calc(Points out, TranslationTable t) {

	  Gdx.app.debug(tag, "Calculating");

	  if (touches.size < 2) {
		Gdx.app.debug(tag, "Not enough points");
		return;
	  }

	  long st = System.currentTimeMillis();

	  touches.insert(0, touches.first());
	  touches.add(touches.peek());

	  Vector2[] tvp = touches.toArray(Vector2.class);
	  sp.set(tvp, false);

	  int transpa = (int) (touches.size * transPointsPerPoint);
	  Points translationPoints = new Points(transpa);

	  for (int i = 0; i <= transpa - 1; i++) {
		Vector2 v = new Vector2();
		sp.valueAt(v, i * 1f / (transpa - 1));
		translationPoints.add(v);
	  }

	  FloatArray dstAr = new FloatArray(translationPoints.size);
	  dstAr.add(0);
	  for (int i = 1; i < translationPoints.size; i++) {
		dstAr.add(dstAr.get(i - 1) + translationPoints.get(i - 1).dst(translationPoints.get(i)));
	  }

	  t.fromDistances(dstAr);

	  float length = dstAr.get(dstAr.size - 1);

	  Gdx.app.debug(tag, "Length is " + length);

	  int k = (int) Math.max(length * 1f / pixelsPerPoint, 10f);
	  out.setSize(k);

	  for (int i = 0; i <= out.size - 1; i++) {
		Vector2 p = new Vector2();
		sp.valueAt(p, i * 1f / (out.size - 1));
		out.set(i, p);
	  }

	  touches.removeIndex(0);
	  touches.removeIndex(touches.size - 1);

	  Gdx.app.debug(tag, "Constructed " + k + " points");
	  Gdx.app.debug(tag, "Took " + (System.currentTimeMillis() - st));

    }


}
