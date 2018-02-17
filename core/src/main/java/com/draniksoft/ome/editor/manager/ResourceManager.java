package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.res.pv_res_wrapper.BasePVResWrapper;
import com.draniksoft.ome.editor.res.pv_res_wrapper.PVResWrapper;
import com.draniksoft.ome.editor.res.pv_res_wrapper.UserPVResWrapper;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.LinkedResource;
import com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResTypes;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.projectVals.ProjectValEvent;
import com.draniksoft.ome.support.load.IntelligentLoader;

import java.util.Iterator;

public class ResourceManager extends BaseSystem implements LoadSaveManager {

    private static String tag = "ResourceManager";

    @Override
    protected void initialize() {
	  data = new IntMap<IntMap<PVResWrapper>>();
	  limits = new IntMap<Integer>();
	  managed = new IntMap<IntMap<Array<LinkedResource>>>();

	  prepocData();
	  prepocLimits();
	  prepocManaged();


    }

    static boolean ET = true;

    // TYPE   // ID
    IntMap<IntMap<PVResWrapper>> data;
    IntMap<IntMap<Array<LinkedResource>>> managed;
    IntMap<Integer> limits;


    public PVResWrapper get(ResTypes t, int id) {
	  return data.get(t.ordinal()).get(id);
    }

    public int create(ResTypes t) {
	  return create(t, true);
    }

    // create new managed value
    public int create(ResTypes t, boolean event) {
	  int id = incLimit(t.ordinal());
	  PVResWrapper wp = new UserPVResWrapper();
	  wp.setName(id + "");
	  wp.res = t.createRoot();
	  data.get(t.ordinal()).put(id, wp);
	  if (event) world.getSystem(OmeEventSystem.class).dispatch(new ProjectValEvent.Create(t, id));
	  return id;
    }

    // register linked resource as
    public void register(ResTypes t, LinkedResource lk, int id) {
	  checkManagedAr(t.ordinal(), id);
	  managed.get(t.ordinal()).get(id).removeValue(lk, true);
	  managed.get(t.ordinal()).get(id).add(lk);
	  lk.ifor(data.get(t.ordinal()).get(id).res, id);
    }

    public void update(ResTypes t, Resource r, int id) {
	  update(t, r, id, true);
    }

    // update managed val data
    public void update(ResTypes t, Resource r, int id, boolean event) {
	  data.get(t.ordinal()).get(id).res.set(r);
    }

    public void updateConstructor(ResTypes t, ResConstructor ct, int id) {
	  data.get(t.ordinal()).get(id).ctr = ct;
    }

    public void delete(ResTypes t, int id) {
	  delete(t, id, true);
    }

    // delete managed val
    public void delete(ResTypes t, int id, boolean event) {
	  throw new GdxRuntimeException("Unsupported operation :: delete");
    }

    // clone managed val, returns id
    public int clone(ResTypes t, int id) {
	  throw new GdxRuntimeException("Unsupported operation :: clone");
    }

    // Main tings helper

    // Creates new resource and setts name, but dispatches only one create event after name setting
    public int createWName_OneEvent(ResTypes t, String name) {
	  int id = create(t, false);
	  setName(t, id, name, false);
	  world.getSystem(OmeEventSystem.class).dispatch(new ProjectValEvent.Create(t, id));
	  return id;
    }

    // TODO when the need arises
    public void updateWAction(ResTypes t, int id, Resource r, ResConstructor c) {
	  throw new GdxRuntimeException("TODO !!");
    }

    //

    public String getName(ResTypes t, int id) {
	  return data.get(t.ordinal()).get(id).getName();
    }

    public void setName(ResTypes t, int id, String nw) {
	  setName(t, id, nw, true);
    }

    public void setName(ResTypes t, int id, String nw, boolean event) {
	  data.get(t.ordinal()).get(id).setName(nw);
	  Gdx.app.debug(tag, data.get(t.ordinal()).get(id).getName());
	  if (event) world.getSystem(OmeEventSystem.class).dispatch(new ProjectValEvent.Rename(t, id));
    }

    public boolean isRenameAllowed(ResTypes t, int id) {
	  return data.get(t.ordinal()).get(id).allowed(PVResWrapper.MODIFIC.RENAME);
    }


    //


    public Iterator<IntMap.Entry<PVResWrapper>> getIT(ResTypes t) {
	  return data.get(t.ordinal()).iterator();
    }

    public IntArray getKeys(ResTypes t) {
	  return data.get(t.ordinal()).keys().toArray();
    }

    //

    int incLimit(int id) {
	  int t = limits.get(id) + 1;
	  limits.put(id, t);
	  return t;
    }

    void checkManagedAr(int i1, int i2) {
	  if (managed.get(i1).get(i2) == null) {
		managed.get(i1).put(i2, new Array<LinkedResource>());
	  }
    }

    //

    void prepocData() {
	  for (ResTypes t : ResTypes.values()) {
		data.put(t.ordinal(), new IntMap<PVResWrapper>());
	  }
    }

    void prepocLimits() {
	  for (ResTypes t : ResTypes.values()) {
		limits.put(t.ordinal(), (int) BasePVResWrapper.THRESHOLD);
	  }
    }

    void prepocManaged() {
	  for (ResTypes t : ResTypes.values()) {
		managed.put(t.ordinal(), new IntMap<Array<LinkedResource>>());
	  }
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
