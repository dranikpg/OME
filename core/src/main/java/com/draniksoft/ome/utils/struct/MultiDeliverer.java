package com.draniksoft.ome.utils.struct;

import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

public class MultiDeliverer<T> {

    Array<Array<T>> ar;

    Array<T> wk;

    public Comparator<T> cc;

    public MultiDeliverer() {
	  ar = new Array<Array<T>>(false, 8);
	  wk = new Array<T>(8);
    }

    public void add(T a) {
	  wk.add(a);
    }

    public void commit() {
	  if (wk.size == 0) return;

	  if (cc != null) wk.sort(cc);

	  ar.add(new Array<T>(wk));
	  wk.clear();
    }

    public int subs() {
	  return ar.size;
    }


    public Array<T> fetch() {
	  wk.clear();

	  for (Array<T> suba : ar) {
		if (suba.size > 0) {
		    wk.add(suba.pop());
		}
	  }

	  for (int i = ar.size - 1; i >= 0; i--) {
		if (ar.get(i).size == 0) ar.removeIndex(i);
	  }

	  return wk;
    }

    public boolean isEmpty() {
	  return ar.size == 0;
    }

}
