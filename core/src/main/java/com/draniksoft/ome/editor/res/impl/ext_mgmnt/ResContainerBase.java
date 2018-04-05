package com.draniksoft.ome.editor.res.impl.ext_mgmnt;

import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.types.ResTypes;
import com.draniksoft.ome.utils.lang.Text;

import java.util.Iterator;

public class ResContainerBase<T> {

    ResTypes t;
    IntMap<ResContainer<T>> cts;
    int lk;

    public ResContainerBase() {
    }

    public void empty(ResTypes t) {
	  this.t = t;
	  cts = new IntMap<ResContainer<T>>(5);
	  lk = 0;
    }

    public ResContainer<T> get(int id) {
	  return cts.get(id);
    }

    public boolean has(int id) {
	  return cts.containsKey(id);
    }

    public Iterator<ResContainer<T>> getAll() {
	  return cts.values().iterator();
    }


    public int create(Text name, String handle) {
	  ResContainer c = new ResContainer();
	  c.name = name;
	  c.handle = handle;
	  c.ctr = null;
	  c.r = t.createRoot();
	  lk++;
	  cts.put(lk, c);
	  return lk;
    }

    public void update(int id, ResConstructor<T> ctr, Resource<T> res) {
	  ResContainer c = cts.get(id);
	  if (c == null) return;
	  c.ctr = ctr;
	  c.r.update(res);
    }


}
