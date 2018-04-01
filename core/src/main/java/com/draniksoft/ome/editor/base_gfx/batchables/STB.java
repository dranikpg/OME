package com.draniksoft.ome.editor.base_gfx.batchables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cyphercove.gdx.flexbatch.Batchable;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;

import static com.draniksoft.ome.utils.GU.CIRCLE_SEGMENTS;
import static com.draniksoft.ome.utils.GU.WHITE;

/*STaticBatchable*/
/*
	THIS IS NOT A FACTORY; DONT SAVE THE VALUES; USE IMMEDIATELY ON GL THREAD
 */
public class STB {

    public static void init() {
	  // could avoided due to class init times
	  ccl = new DynamicColorCircle();
	  qd = new Quad2D();
    }

    static DynamicColorCircle ccl;

    static Quad2D qd;

    /*
    	 rect
     */

    public static Batchable rect(int x, int y, int w, int h, TextureRegion r) {
	  return rect(x, y, w, h, Color.WHITE, r);
    }

    public static Batchable rect(int x, int y, int w, int h, Color c) {
	  return rect(x, y, w, h, c, WHITE());
    }

    public static Batchable rect(int x, int y, int w, int h, Color c, TextureRegion r) {
	  qd.position(x, y).size(w, h).color(c).textureRegion(r);
	  return qd;
    }


    /*
    	Circle
     */

    public static Batchable circle(int x, int y, int rd, Color c) {
	  ccl.x = x;
	  ccl.y = y;
	  ccl.radius = rd;
	  ccl.color = c.toFloatBits();
	  ccl.seg = CIRCLE_SEGMENTS(rd);
	  return ccl;
    }


    public static Batchable circle(int x, int y, int rd, Color c, int seg) {
	  ccl.x = x;
	  ccl.y = y;
	  ccl.radius = rd;
	  ccl.color = c.toFloatBits();
	  ccl.seg = seg;
	  return ccl;
    }


}
