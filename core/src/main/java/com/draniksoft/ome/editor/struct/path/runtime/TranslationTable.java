package com.draniksoft.ome.editor.struct.path.runtime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.FloatArray;

/*
	Table to map spline percentage to path precentage
 */
public class TranslationTable {

    private static String tag = "TranslationTable";

    private FloatArray values;

    // first item which is smaller
    private int _last = -1;

    public TranslationTable() {
	  values = new FloatArray();
    }

    public void fromDistances(FloatArray dst) {
	  float length = dst.get(dst.size - 1);
	  values.setSize(dst.size);
	  for (int i = 0; i < dst.size; i++) {
		values.set(i, dst.get(i) / length);
	  }
	  Gdx.app.debug(tag, "Constructing with " + dst.size + " vals ");
	  resetRuntime();
    }

    public void resetRuntime() {
	  _last = -1;
    }


    // finds the next value based on percentage
    public float next(float p) {
	  if (_last == -1) {
		findLast(p);
	  }
	  int i = Math.max(0, _last);
	  while (p > values.get(i)) i++;
	  _last = i - 1;

	  if (_last == values.size - 1) {
		return 1f;
	  } else {
		float pct = (p - values.get(_last)) / (values.get(_last) + values.get(_last + 1));
		pct = MathUtils.clamp(pct, 0, 1);
		return _last * 1f / values.size + pct * 1f / values.size;
	  }
    }

    // finds the spline value for the given percent
    public float find(float p) {
	  int idx = 0;
	  int i = 0;
	  while (i < values.size && p >= values.get(i)) i++;
	  idx = i - 1;

	  if (idx >= values.size - 1) {
		return 1f;
	  } else {
		float pct = (p - values.get(idx)) / (values.get(idx) + values.get(idx + 1));
		pct = MathUtils.clamp(pct, 0, 1);
		return idx * 1f / values.size + pct * 1f / values.size;
	  }
    }

    //

    private void findLast(float v) {
	  int i = 0;
	  while (i < values.size && v >= values.get(i)) i++;
	  _last = i - 1;
    }


}

