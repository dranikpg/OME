package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.base_gfx.color.ColorProvider;
import com.draniksoft.ome.editor.base_gfx.color.LinkColor;
import com.draniksoft.ome.editor.base_gfx.color.LinkDestColorProvider;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.EmptyDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.projectVals.ColorEvent;
import com.draniksoft.ome.editor.support.event.projectVals.DrawableEvent;
import com.draniksoft.ome.support.load.IntelligentLoader;
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


    IntMap<Array<LinkColor>> managedColors;
    ObjectMap<Integer, MtPair<LinkDestColorProvider, String>> colorData;

    int colorIdx = 0;

    private void initColorData() {
	  colorData = new ObjectMap<Integer, MtPair<LinkDestColorProvider, String>>();
	  managedColors = new IntMap<Array<LinkColor>>();
    }

    public String getColorName(int id) {
	  return colorData.get(id).V();
    }

    public void changeColorName(String name, int id) {
	  colorData.get(id).V(name);
	  if (ET) world.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorNameChangeE(id));
    }

    public MtPair<LinkDestColorProvider, String> getColorPair(int id) {
	  return colorData.get(id);
    }

    public ColorProvider getColor(int id) {
	  return colorData.get(id).K();
    }

    public boolean hasColor(int id) {
	  return colorData.containsKey(id);
    }

    public ColorProvider getColorProvider(int id) {
	  return colorData.get(id).K();
    }

    public void register(LinkColor c, int id) {
	  if (c.id >= 0) {
		managedColors.get(id).removeValue(c, true);
	  }
	  c.pv = colorData.get(id).K();
	  c.id = id;
	  managedColors.get(id).add(c);
    }

    public void free(LinkColor c) {

	  if (c.id >= 0) {
		managedColors.get(c.id).removeValue(c, true);
	  }

	  // TODO : CHANGE REFERENCE

    }


    public ObjectMap.Entries<Integer, MtPair<LinkDestColorProvider, String>> getColorData() {
	  return colorData.entries();
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
