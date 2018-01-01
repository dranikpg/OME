package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.color.ColorEvent;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.struct.EColor;
import com.draniksoft.ome.utils.struct.MtPair;

import java.util.Iterator;

public class ColorManager extends BaseSystem implements LoadSaveManager {

    private static String tag = "ColorManager";

    IntMap<Array<EColor>> managed;

    ObjectMap<Integer, MtPair<EColor, String>> data;

    int minB = 0;

    public String getName(int id) {
	  return data.get(id).V();
    }

    public void changeName(String name, int id) {
	  data.get(id).V(name);
	  world.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorNameChangeE(id));
    }

    public MtPair<EColor, String> getP(int id) {
	  return data.get(id);
    }

    public EColor get(int id) {
	  return data.get(id).K();
    }

    public boolean has(int id) {
	  return data.containsKey(id);
    }

    public void change(int id, Color cc) {
	  data.get(id).K().set(cc);
	  for (Color c : managed.get(id)) {
		c.set(cc);
	  }
    }

    public int create(String name) {
	  EColor c = new EColor();
	  c.id = ++minB;
	  data.put(minB, MtPair.P(c, name));
	  managed.put(minB, new Array<EColor>());
	  return minB;
    }

    public int create(String name, Color c) {
	  int id = create(name);
	  change(id, c);
	  return id;
    }

    public Array<EColor> delete(int id) {
	  Array<EColor> ar = new Array<EColor>();
	  data.remove(id);

	  for (EColor c : managed.get(id)) {
		ar.add(c);
		freeMgnd(c);
	  }

	  managed.get(id).clear();
	  managed.remove(id);
	  return ar;
    }

    public void reg(EColor c, int id) {
	  Gdx.app.debug(tag, "Migrating color " + c.id + " to " + id);
	  if (c.id > 0) {
		if (managed.get(c.id) != null) managed.get(c.id).removeValue(c, true);
	  }
	  if (id > 0) {
		c.set(data.get(id).K());
		managed.get(id).add(c);
	  }
	  c.id = id;
    }

    public EColor newMgnd(int id) {
	  EColor c = new EColor();
	  reg(c, id);
	  return c;
    }

    public void delMgnd(EColor c) {
	  if (c.id > 0) {
		managed.get(c.id).removeValue(c, true);
	  }
    }

    public void freeMgnd(EColor c) {
	  reg(c, -1);
    }

    public ObjectMap.Entries<Integer, MtPair<EColor, String>> getDataI() {
	  return data.iterator();
    }

    public Iterator<IntMap.Entry<Array<EColor>>> getManagedI() {
	  return managed.iterator();
    }

    public Array<MtPair<EColor, String>> getAr() {
	  return data.values().toArray();
    }

    @Override
    protected void initialize() {
	  data = new ObjectMap<Integer, MtPair<EColor, String>>();
	  managed = new IntMap<Array<EColor>>();
    }

    @Override
    protected void processSystem() {

    }

    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {

    }

    @Override
    public void load(IntelligentLoader il, ProjectLoader ld) {

    }
}
