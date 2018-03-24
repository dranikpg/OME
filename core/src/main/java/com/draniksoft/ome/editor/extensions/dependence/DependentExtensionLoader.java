package com.draniksoft.ome.editor.extensions.dependence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.extensions.t.ExtensionState;
import com.draniksoft.ome.editor.extensions.t.ExtensionType;
import com.draniksoft.ome.editor.manager.ExtensionManager;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.ut.StepLoader;
import com.draniksoft.ome.utils.struct.MultiDeliverer;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.Comparator;
import java.util.concurrent.Callable;

public class DependentExtensionLoader implements Callable<Object> {

    private static final String tag = "DependencyGrouper";


    private class DaoNode {
	  public ExtensionDao d;
	  public boolean v = false;

	  public DaoNode(ExtensionDao d) {
		this.d = d;
	  }
    }

    public ExtensionManager mgr;

    ExecutionProvider pv;
    Array<String> tol;

    ObjectMap<String, DaoNode> map;
    ObjectSet<String> added;

    Array<Extension> lastL;

    MultiDeliverer<ExtensionDao> delver;

    ResponseListener l;

    /*
	  Dao collection
     */

    public DependentExtensionLoader(ExtensionManager mgr) {
	  this.mgr = mgr;
    }

    private void init() {

	  Gdx.app.debug(tag, "Init");

	  map = new ObjectMap<String, DaoNode>();
	  added = new ObjectSet<String>();

	  delver = new MultiDeliverer<ExtensionDao>();

	  lastL = new Array<Extension>(false, 5);

	  delver.cc = new Comparator<ExtensionDao>() {
		@Override
		public int compare(ExtensionDao t0, ExtensionDao t1) {
		    return Integer.valueOf(t1.req.size).compareTo(t0.req.size);
		}
	  };
    }

    private void index(Array<String> s) {
	  for (String ss : s) {
		inspectReq(ss);
	  }
	  Gdx.app.debug(tag, "To load :  " + map.size);
    }

    private void inspectReq(String req) {
	  if (map.containsKey(req)) return;
	  if (mgr.hasExtension(req)) return;

	  ExtensionDao d = mgr.findDao(req);
	  added.add(req);
	  map.put(req, new DaoNode(d));

	  if (d == null) {
		mgr.createExtension(req, ExtensionType.UNRESOLVED, ExtensionState.WORKING);
		return;
	  } else {
		mgr.createExtension(req, d.t.getT(), ExtensionState.LOADING);
	  }

	  for (String s : d.req) {
		inspectReq(s);
	  }
    }

    /*

     */

    private void group() {
	  for (DaoNode n : map.values()) {
		if (!n.v) {
		    delver.commit();
		    dfs(n);
		}
	  }
	  delver.commit();
	  Gdx.app.debug(tag, "Grouped to " + delver.subs() + " groups");
    }

    private void dfs(DaoNode n) {
	  n.v = true;
	  delver.add(n.d);
	  for (String sub : n.d.req) {
		if (map.containsKey(sub)) {
		    if (!map.get(sub).v)
			  dfs(map.get(sub));
		}

	  }


    }


    public ObjectSet<String> getAdded() {
	  return added;
    }

    public void load(ExecutionProvider pv, Array<String> tol, ResponseListener l) {
	  this.l = l;
	  this.pv = pv;
	  this.tol = tol;
	  pv.exec(this);
    }


    private void update(ExecutionProvider ec) {
	  lastL.clear();
	  Array<ExtensionDao> daos = delver.fetch();
	  for (ExtensionDao d : daos) {

		try {
		    Extension e = mgr.rawLoadExtension(d, ec);
		    lastL.add(e);
		} catch (Exception e) {
		    Gdx.app.error(tag, "", e);
		}
	  }
	  Gdx.app.debug(tag, "Parallel ext load " + lastL.size);
	  Gdx.app.debug(tag, "Subs left " + delver.subs());
    }


    private void finishLoadStep() {
	  Gdx.app.debug(tag, "Finishing load step");
	  for (Extension e : lastL) {
		mgr.endExtensionLoad(e);
	  }
    }


    @Override
    public Object call() throws Exception {

	  init();

	  index(tol);

	  group();

	  final StepLoader l = new StepLoader(pv);

	  l.setListener(new ResponseListener() {
		@Override
		public void onResponse(short code) {
		    finishLoadStep();
		    if (delver.isEmpty()) {
			  l.dispose();
		    } else {
			  l.reset();
			  update(l);
		    }
		}
	  });
	  l.reset();

	  update(l);

	  return null;
    }


}
