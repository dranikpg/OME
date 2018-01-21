package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.base_gfx.color.simple.LinkColor;
import com.draniksoft.ome.editor.base_gfx.color.utils.ColorProvider;
import com.draniksoft.ome.editor.base_gfx.color.utils.RootColorProvider;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.EmptyDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.RootDrawable;
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

    IntMap<MtPair<RootDrawable, String>> drawableData;
    IntMap<Array<LinkedDrawable>> managedDrawables;

    IntMap<String> rawDwbSrc;

    int drawableIdx = 0;

    private void initDrawableData() {
	  drawableData = new IntMap<MtPair<RootDrawable, String>>();
	  managedDrawables = new IntMap<Array<LinkedDrawable>>();
	  rawDwbSrc = new IntMap<String>();
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
	  RootDrawable rd = new RootDrawable();
	  rd.d = d;
	  drawableData.put(drawableIdx, MtPair.P(rd, name));

	  if (ET) world.getSystem(OmeEventSystem.class).dispatch(new DrawableEvent.DrawableAddedE(drawableIdx));
	  return drawableIdx;
    }

    public Iterator<MtPair<RootDrawable, String>> getDrawableIt() {
	  return drawableData.values().iterator();
    }

    public Iterator<IntMap.Entry<MtPair<RootDrawable, String>>> getDrawableItAll() {
	  return drawableData.iterator();
    }

    public Drawable getRawDrawable(int id) {
	  return drawableData.get(id).K().d;
    }

    public RootDrawable getDrawableBinding(int id) {
	  return drawableData.get(id).K();
    }

    public void setDrawable(int id, Drawable d) {
	  drawableData.get(id).K().d = d;
    }

    public String getDrawableName(int id) {
	  return drawableData.get(id).V();
    }

    public void setDrawableName(int id, String val) {
	  drawableData.get(id).V(val);
	  if (ET) world.getSystem(OmeEventSystem.class).dispatch(new DrawableEvent.DrawableNameChangedE(id));
    }

    public String getDwbRawSrc(int id) {
	  return rawDwbSrc.get(id);
    }

    public IntArray getDrawableKeys() {
	  return drawableData.keys().toArray();
    }

    /*  COLOR PART */


    IntMap<Array<LinkColor>> managedColors;
    IntMap<MtPair<RootColorProvider, String>> colorData;

    int colorIdx = 0;

    private void initColorData() {
	  colorData = new IntMap<MtPair<RootColorProvider, String>>();
	  managedColors = new IntMap<Array<LinkColor>>();
    }

    public String getColorName(int id) {
	  return colorData.get(id).V();
    }

    public void setColorName(String name, int id) {
	  colorData.get(id).V(name);
	  if (ET) world.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorNameChangeE(id));
    }

    public MtPair<RootColorProvider, String> getColorPair(int id) {
	  return colorData.get(id);
    }

    public RootColorProvider getColor(int id) {
	  return colorData.get(id).K();
    }

    public ColorProvider getColorSrc(int id) {
	  return colorData.get(id).K().p;
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

    }

    public void createNewColor() {
	  createNewColor(null);
    }

    public void createNewColor(String name) {
	  colorIdx++;

	  colorData.put(colorIdx, MtPair.P(new RootColorProvider(), name == null ? "" : name));
	  managedColors.put(colorIdx, new Array<LinkColor>());

	  if (ET) world.getSystem(OmeEventSystem.class).dispatch(new ColorEvent.ColorAddedEvent(colorIdx));

    }

    public void changeColor(int id, ColorProvider newpv) {
	  colorData.get(id).K().p = newpv;
    }

    // CREATION UTILS

    public LinkColor obtainColor(int id) {
	  LinkColor c = new LinkColor();
	  register(c, id);
	  return c;
    }

    public IntMap.Entries<MtPair<RootColorProvider, String>> getColorData() {
	  return colorData.entries();
    }

    public IntArray getColorKeys() {
	  return colorData.keys().toArray();
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
