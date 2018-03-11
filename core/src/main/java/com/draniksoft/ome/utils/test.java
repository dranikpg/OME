package com.draniksoft.ome.utils;

import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.utils.struct.MtPair;

import java.util.Scanner;

public class test {

    public static void test1() {
	  Scanner sc = new Scanner(System.in);

	  String st = "";
	  while (!"exit".equals(st)) {
		st = sc.nextLine();
		MtPair<String, String> p = SUtils.parseAssetURI(st);
		if (p == null) {
		    System.out.println("#NULL");
		    continue;
		}
		System.out.println("Extension : " + p.K());
		System.out.println("Value : " + p.V());
	  }
    }

    public static void test2() {
	  DelayedRemovalArray<Integer> ia = new DelayedRemovalArray<Integer>();
	  ia.addAll(1, 2, 3, 4, 5);
	  System.out.println(ia.toString());

	  ia.begin();
	  for (Integer i : ia) {
		if (i % 2 == 0) ia.removeValue(i, true);
	  }
	  ia.end();

	  System.out.println(ia.toString());


    }

    public static void main(String[] arts) {
	  test2();
    }

}
