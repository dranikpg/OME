package com.draniksoft.ome.utils.struct;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
}
