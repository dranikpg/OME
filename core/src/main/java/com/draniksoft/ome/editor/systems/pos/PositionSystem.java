package com.draniksoft.ome.editor.systems.pos;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.components.tps.MapC;
import com.draniksoft.ome.utils.struct.Pair;

public class PositionSystem extends BaseEntitySystem {

    private static final String tag = "PositionSystem";
    /**
     * Creates an entity system that uses the specified aspect self a matcher
     * against entities.
     */

    ComponentMapper<PosBoundsC> m;

    public PositionSystem() {
	  super(Aspect.all(PosBoundsC.class).exclude(MapC.class));
    }

    @Override
    protected void processSystem() {

    }

    public Pair<Integer, Integer> getCornerPos(int id) {
	  PosBoundsC c = m.get(id);
	  return Pair.P(c.x, c.y);
    }

    public Pair<Integer, Integer> getCenterP(int id) {
	  PosBoundsC c = m.get(id);
	  return Pair.P(c.x + 20 / 2, c.y + 20 / 2);
    }

    public Vector2 getCenterV(int id) {
	  PosBoundsC c = m.get(id);
	  return new Vector2(c.x + 20 / 2, c.y + 20 / 2);
    }

    public void getCenterPosV(int id, Vector2 v) {
	  PosBoundsC c = m.get(id);
	  v.set(c.x + 20 / 2, c.y + 20 / 2);
    }

    public void setByCenterPos(int id, int x, int y) {
	  PosBoundsC c = m.get(id);
	  c.x = x - 20 / 2;
	  c.y = y - 20 / 2;
    }

    public void setByCornerPos(int id, int x, int y) {
	  PosBoundsC c = m.get(id);
	  c.x = x;
	  c.y = y;
    }

    public void resizeByCorner(int id, int w, int h) {
	  PosBoundsC c = m.get(id);
	  c.x = w;
	  c.y = h;
    }

    public void resizeByCenter(int id, int w, int h) {
	  PosBoundsC c = m.get(id);
	  int wd = 20 - w;
	  int hd = 20 - h;
	  //  20 = w;
	  // 20 = h;
	  c.x += wd / 2;
	  c.y += hd / 2;
    }

    public int getTouch(int x, int y) {
	  Rectangle r = new Rectangle();
	  for (int i = 0; i < getEntityIds().size(); i++) {
		PosBoundsC c = m.get(getEntityIds().get(i));
		r.setPosition(c.x, c.y);
		r.setSize(20, 20);
		if (r.contains(x, y)) {
		    return getEntityIds().get(i);
		}
	  }

	  return -1;
    }

    public void save(int e) {

    }


    /*
    	Setts the PosSize to the Mo's
     */
    public void resetPos(int e) {
	  PosBoundsC c = m.get(e);
	 /* PositionC m = mM.get(e);
	  c.x = m.x - 20 / 2;
	  c.y = m.y - 20 / 2;*/
	  //20 = m.w;
	  // 20 = m.h;
    }
}
