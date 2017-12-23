package com.draniksoft.ome.utils;

import dint.Dint;

public class DateUtils {

    public static String format(int d) {
	  return Dint.day(d) + "." + Dint.month(d) + "." + Dint.year(d);
    }

}
