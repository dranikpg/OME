package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.impl.ext_mgmnt.ResContainer;
import com.draniksoft.ome.editor.res.impl.ext_mgmnt.ResSubExt;
import com.draniksoft.ome.editor.res.impl.res_ifaces.LinkedResource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResTypeDescriptor;
import com.draniksoft.ome.editor.res.impl.types.ResTypes;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.lang.Text;

public class ResourceManager extends BaseSystem implements LoadSaveManager {

    private static String tag = "ResourceManager";


    Array<Array<ResTypeDescriptor>> desc;

    @Override
    protected void processSystem() {

    }

    @Override
    protected void initialize() {
	  desc = new Array<Array<ResTypeDescriptor>>();
	  desc.setSize(ResTypes.values().length);
	  parseDesc();
    }

    /* Create - update */

    public int create(ResTypes t, String ext, Text name, String handle) {
	  Extension e = world.getSystem(ExtensionManager.class).getExt(ext);
	  if (e == null) return -1;
	  ResSubExt sext = e.getSub(ResSubExt.class);
	  if (sext == null) return -1;
	  return sext.get(t).create(name, handle);
    }

    public void update(ResTypes t, String ext, int id, ResConstructor ct) {
	  ResSubExt ex = getS(ext);
	  if (ex == null) return;
	  ex.get(t).update(id, ct, ct.build(world));
    }

    /* Register - unregister */

    public void unregister(ResTypes t, String ext, int id) {
	  ResSubExt ex = getS(ext);
	  if (ex == null) return;
	  ResContainer ct = ex.get(t).get(id);
	  ex.unregister(ct);
    }

    public void register(LinkedResource res, ResTypes t, String ext, int id) {
	  ResSubExt ex = getS(ext);
	  if (ex == null) return;
	  ResContainer ct = ex.get(t).get(id);
	  res.setR(ct.r);
	  ex.register(ct);
    }

    /* Getter */

    public RootResource getR(ResTypes t, String ext, int id) {
	  ResSubExt ex = getS(ext);
	  if (ex == null) return null;
	  return ex.get(t).get(id).r;
    }

    public ResContainer getCt(ResTypes t, String ext, int id) {
	  ResSubExt ex = getS(ext);
	  if (ex == null) return null;
	  return ex.get(t).get(id);
    }

    private ResSubExt getS(String ext) {
	  Extension e = world.getSystem(ExtensionManager.class).getExt(ext);
	  if (e == null) return null;
	  return e.getSub(ResSubExt.class);
    }

    @Override
    public void save(ProjectSaver s) {

    }

    @Override
    public void load(ProjectLoader ld) {

    }

    /*
    	Desc
     */


    public Array<ResTypeDescriptor> getDesc(ResTypes t) {
	  return desc.get(t.ordinal());
    }

    public ResTypeDescriptor findDesc(ResTypes t, Class c) {
	  for (ResTypeDescriptor d : getDesc(t)) {
		if (d.c == c) return d;
	  }
	  return null;
    }

    public Array<ResTypeDescriptor> collectDesc(ResTypes t, boolean g, boolean local) {
	  Array<ResTypeDescriptor> ar = new Array<ResTypeDescriptor>();
	  for (ResTypeDescriptor d : getDesc(t)) {
		if (d.group != g) continue;
		if (local) {
		    if (!d.local) continue;
		} else {
		    if (!d.global) continue;
		}
		ar.add(d);
	  }
	  return ar;
    }

    private void parseDesc() {
	  JsonValue v = FUtills.r().parse(Gdx.files.internal("data/res_type_desc.json"));
	  for (JsonValue type_v : v) {
		ResTypes t = ResTypes.valueOf(type_v.name);
		if (t == null) continue;
		Array<ResTypeDescriptor> ar = new Array<ResTypeDescriptor>();
		for (JsonValue val : type_v) {
		    ar.add(ResTypeDescriptor.read(val));
		}
		desc.set(t.ordinal(), ar);
		Gdx.app.debug(tag, "Read " + t.name() + " : " + ar.size);
	  }
    }


}
