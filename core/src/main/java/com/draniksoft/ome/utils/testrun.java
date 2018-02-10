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

	  int e = 2;
	  int y = "QCX".hashCode() % 3000;
	  int x = "NLX".hashCode() % 3000;
	  for (int d = 0; d <= x; d++)
		e = (e ^ d) % y;
	  System.out.println(e);

    }

}
