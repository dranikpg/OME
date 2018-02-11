package com.draniksoft.ome.utils.struct;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/*
	Serialization wrapper class with different utils
 */
public class Points extends Array<Vector2> {

    public Points() {
	  super();
    }

    public Points(int capacity) {
	  super(capacity);
    }

    public Points(Array<? extends Vector2> array) {
	  super(array);
    }

    public Vector2 last() {
	  return peek();
    }

    public void deepCopy(Points p) {
	  setSize(p.size);
	  for (int i = 0; i < p.size; i++) {
		set(i, new Vector2(p.get(i)));
	  }
    }
}
