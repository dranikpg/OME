package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.base_gfx.drawable.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.EmptyDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.LinkedDrawable;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.projectVals.ColorEvent;
import com.draniksoft.ome.editor.support.event.projectVals.DrawableEvent;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.struct.EColor;
import com.draniksoft.ome.utils.struct.MtPair;

import java.util.Iterator;

public class ProjValsManager extends BaseSystem implements LoadSaveManager {

    private static String tag = "ProjValsManager";

    @Override
    protected void initialize() {

	  initColorData();

	  initDrawableData();

    }

    static boolean ET = true;

    /* DRAWABLE PART */

    IntMap<MtPair<Drawable, String>> drawableData;
    IntMap<Array<LinkedDrawable>> managedDrawables;

    int drawableIdx = 0;

    private void initDrawableData() {
	  drawableData = new IntMap<MtPair<Drawable, String>>();
	  managedDrawables = new IntMap<Array<LinkedDrawable>>();
    }

    public void register(LinkedDrawable d, int id) {
	  managedDrawables.get(d.linkID).removeValue(d, true);
	  managedDrawables.get(id).add(d);
	  d.link = drawableData.get(id).K();
	  d.linkID = id;
    }

    public LinkedDrawable obtainDrawable(int id) {
	  LinkedDrawable d = new LinkedDrawable();
	  managedDrawables.get(id).add(d);
	  d.link = drawableData.get(id).K();
	  d.linkID = id;
	  return d;
    }

    public void unregister(LinkedDrawable d) {
	  int id = d.linkID;
	  managedDrawables.get(id).removeValue(d, true);
	  d.linkID = -1;
	  d.link = new EmptyDrawable();
    }


    public int createNewDrawable() {
	  return createNewDrawable(null);
    }

    public int createNewDrawable(String name) {
	  drawableIdx++;

	  if (name == null) name = String.valueOf(drawableIdx);

	  managedDrawables.put(drawableIdx, new Array<LinkedDrawable>());
	  Drawable d = new EmptyDrawable();
	  drawableData.put(drawableIdx, MtPair.P(d, name));

	  world.getSystem(OmeEventSystem.class).dispatch(new DrawableEvent.DrawableAddedE(drawableIdx));
	  return drawableIdx;
    }

    public Iterator<MtPair<Drawable, String>> getDrawableIt() {
	  return drawableData.values().iterator();
    }

    public Iterator<IntMap.Entry<MtPair<Drawable, String>>> getDrawableItAll() {
	  return drawableData.iterator();
    }

    public Drawable getDrawable(int id) {
	  return drawableData.get(id).K();
    }

    public void setDrawable(int id, Drawable d) {
	  drawableData.get(id).K(d);
    }

    public String getDrawableName(int id) {
	  return drawableData.get(id).V();
    }

    public void setDrawableName(int id, String val) {
	  drawableData.get(id).V(val);
    }


    /*  COLOR PART */


    IntMap<Array<EColor>> managedColors;
    ObjectMap<Integer, MtPair<EColor, String>> colorData;

    int colorIdx = 0;

    private void initColorData() {
	  colorData = new ObjectMap<Integer, MtPair<EColor, String>>();
	  managedColors = new IntMap<Array<EColor>>();
    }

    public String getColorName(int id) {
	  return colorData.get(id).V();
    }

    public void changeColorName(String name, int id) {
	  colorData.get(id).V(name);
	  if (ET) world.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorNameChangeE(id));
    }

    public MtPair<EColor, String> getColorPair(int id) {
	  return colorData.get(id);
    }

    public EColor getColor(int id) {
	  return colorData.get(id).K();
    }

    public boolean hasColor(int id) {
	  return colorData.containsKey(id);
    }

    public void changeColor(int id, Color cc) {
	  colorData.get(id).K().set(cc);
	  for (Color c : managedColors.get(id)) {
		c.set(cc);
	  }
    }

    public int createColor() {
	  return createColor(null);
    }

    public int createColor(String name) {
	  EColor c = new EColor();
	  c.id = ++colorIdx;
	  if (name == null) name = String.valueOf(c.id);
	  colorData.put(colorIdx, MtPair.P(c, name));
	  managedColors.put(colorIdx, new Array<EColor>());

	  if (ET) world.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorAddedEvent(colorIdx));

	  return colorIdx;
    }

    public int createColor(String name, Color c) {
	  int id = createColor(name);
	  changeColor(id, c);
	  return id;
    }

    public Array<EColor> deleteColor(int id) {
	  Array<EColor> ar = new Array<EColor>();
	  colorData.remove(id);

	  for (EColor c : managedColors.get(id)) {
		ar.add(c);
		freeColor(c);
	  }

	  managedColors.get(id).clear();
	  managedColors.remove(id);
	  return ar;
    }

    public void registerColor(EColor c, int id) {
	  Gdx.app.debug(tag, "Migrating projectVals " + c.id + " to " + id);
	  if (c.id > 0) {
		if (managedColors.get(c.id) != null) managedColors.get(c.id).removeValue(c, true);
	  }
	  if (id > 0) {
		c.set(colorData.get(id).K());
		managedColors.get(id).add(c);
	  }
	  c.id = id;
    }

    public EColor obtainColor(int id) {
	  EColor c = new EColor();
	  registerColor(c, id);
	  return c;
    }

    public void freeColor(EColor c) {
	  registerColor(c, -1);
    }

    public ObjectMap.Entries<Integer, MtPair<EColor, String>> getColorData() {
	  return colorData.iterator();
    }

    public Iterator<IntMap.Entry<Array<EColor>>> getManagerColorsIt() {
	  return managedColors.iterator();
    }

    public Array<MtPair<EColor, String>> getColorArray() {
	  return colorData.values().toArray();
    }

    /* */

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
