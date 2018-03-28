package com.draniksoft.ome.utils;

/*
	Frame time steps
 */

public class FM {

    public static int FRAME = 0;

    /*
	  # fired by Main
     */
    public static void frame() {
	  //LAST_FRAME = FRAME;
	  FRAME++;
	  /* Frame overflow ?? probably not */
    }

}
