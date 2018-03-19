package com.draniksoft.ome.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.utils.struct.CompoundIterator;
import com.draniksoft.ome.utils.struct.MtPair;

import java.util.Iterator;
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

    public static void test3() {
	  Array<Integer> a1 = new Array<Integer>();
	  Array<Integer> a2 = new Array<Integer>();
	  Array<Integer> a3 = new Array<Integer>();


	  a1.addAll(1, 2);
	  a2.addAll();
	  a3.addAll(5, 6);

	  Iterator<Integer> i = new CompoundIterator<Integer>(a1.iterator(), a2.iterator(), a3.iterator());
	  while (i.hasNext()) {
		System.out.print(" " + i.next());
	  }

    }

    public static void main(String[] arts) {
	  test3();
    }

}
