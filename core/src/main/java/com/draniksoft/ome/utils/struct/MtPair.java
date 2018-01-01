package com.draniksoft.ome.utils.struct;

public class MtPair<K, V> {

    K val0;
    V val1;

    public MtPair(K val0, V val1) {
	  this.val0 = val0;
	  this.val1 = val1;
    }

    public MtPair() {

    }

    public K K() {
	  return val0;
    }

    public V V() {
	  return val1;
    }

    public void K(K val0) {
	  this.val0 = val0;
    }

    public void V(V val1) {
	  this.val1 = val1;
    }

    public static <K, V> MtPair<K, V> P(K element0, V element1) {
	  return new MtPair<K, V>(element0, element1);
    }
}
