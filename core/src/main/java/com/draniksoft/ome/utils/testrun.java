package com.draniksoft.ome.utils;

public class testrun {

    public static class leletest {

	  public int v;

    }

    public static void changel(leletest l) {
	  l = new leletest();
	  l.v = 20;
    }

    public static void main(String[] args) {

	  leletest t = new leletest();
	  t.v = 10;

	  changel(t);

	  System.out.println(t.v);

    }

}
