package com.draniksoft.ome.utils.struct;

public class Relais<T> extends Pair<T, T> {

    boolean f;

    public Relais(T element0, T element1) {
	  super(element0, element1);
    }

    public T a() {
	  if (f) return e0;
	  else return e1;
    }

    public T b() {
	  if (f) return e1;
	  else return e0;
    }

    public void relais() {
	  f = !f;
    }


}
