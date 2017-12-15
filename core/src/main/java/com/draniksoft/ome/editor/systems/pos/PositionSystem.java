package com.draniksoft.ome.editor.systems.pos;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.components.tps.MapC;
import com.draniksoft.ome.utils.struct.Pair;

public class PositionSystem extends BaseEntitySystem {

    private static final String tag = "PositionSystem";
    /**
     * Creates an entity system that uses the specified aspect as a matcher
     * against entities.
     */

    ComponentMapper<PosSizeC> m;
    ComponentMapper<MObjectC> mM;

    public PositionSystem() {
	  super(Aspect.all(PosSizeC.class).exclude(MapC.class));
    }

    @Override
    protected void processSystem() {

    }

    public Pair<Integer, Integer> getCornerPos(int id) {
	  PosSizeC c = m.get(id);
	  return Pair.createPair(c.x, c.y);
    }

    public Pair<Integer, Integer> getCenterP(int id) {
	  PosSizeC c = m.get(id);
	  return Pair.createPair(c.x + c.w / 2, c.y + c.h / 2);
    }

    public Vector2 getCenterV(int id) {
	  PosSizeC c = m.get(id);
	  return new Vector2(c.x + c.w / 2, c.y + c.h / 2);
    }

    public void getCenterPosV(int id, Vector2 v) {
	  PosSizeC c = m.get(id);
	  v.set(c.x + c.w / 2, c.y + c.h / 2);
    }

    public void setByCenterPos(int id, int x, int y) {
	  PosSizeC c = m.get(id);
	  c.x = x - c.w / 2;
	  c.y = y - c.h / 2;
    }

    public void setByCornerPos(int id, int x, int y) {
	  PosSizeC c = m.get(id);
	  c.x = x;
	  c.y = y;
    }

    public void resizeByCorner(int id, int w, int h) {
	  PosSizeC c = m.get(id);
	  c.x = w;
	  c.y = h;
    }

    public void resizeByCenter(int id, int w, int h) {
	  PosSizeC c = m.get(id);
	  int wd = c.w - w;
	  int hd = c.h - h;
	  c.w = w;
	  c.h = h;
	  c.x += wd / 2;
	  c.y += hd / 2;
    }

    public int getTouch(int x, int y) {
	  Rectangle r = new Rectangle();
	  for (int i = 0; i < getEntityIds().size(); i++) {
		PosSizeC c = m.get(getEntityIds().get(i));
		r.setPosition(c.x, c.y);
		r.setSize(c.w, c.h);
		if (r.contains(x, y)) {
		    return getEntityIds().get(i);
		}
	  }

	  return -1;
    }

    public void save(int e) {
	  PosSizeC c = m.get(e);
	  MObjectC m = mM.get(e);
	  m.x = c.x;
	  m.y = c.y;
	  m.w = c.w;
	  m.h = c.h;
    }


}
