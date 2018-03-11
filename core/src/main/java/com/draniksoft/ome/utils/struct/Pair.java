package com.draniksoft.ome.utils.struct;

public class Pair<K, V> {

    private final K e0;
    private final V e1;

    public static <K, V> Pair<K, V> P(K element0, V element1) {
	  return new Pair<K, V>(element0, element1);
    }

    public Pair(K element0, V element1) {
	  this.e0 = element0;
	  this.e1 = element1;
    }

    public K K() {
	  return e0;
    }

    public V V() {
	  return e1;
    }

    @Override
    public String toString() {

	  return ((e0 != null) ? e0.toString() : " ") + ";" +
		    ((e1 != null) ? e1.toString() : " ");

    }
}
