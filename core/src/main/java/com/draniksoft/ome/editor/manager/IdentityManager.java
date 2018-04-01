package com.draniksoft.ome.editor.manager;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.Manager;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.utils.IntIntMap;
import com.draniksoft.ome.editor.components.base.IdentityC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;

/*
	Constant identity for entities
 */

public class IdentityManager extends Manager implements LoadSaveManager {

    IntIntMap m;
    ComponentMapper<IdentityC> mp;

    int c = 1;

    @Override
    protected void initialize() {
	  m = new IntIntMap(64);

	  world.getAspectSubscriptionManager().get(Aspect.all(IdentityC.class)).addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
		@Override
		public void inserted(IntBag entities) {
		    for (int i = 0; i < entities.size(); i++) {
			  int e = entities.get(i);
			  IdentityC c = mp.get(e);
			  if (c.id == -1) c.id = register(e);
			  else m.put(c.id, e);
		    }
		}

		@Override
		public void removed(IntBag entities) {
		    for (int i = 0; i < entities.size(); i++) {
			  int e = entities.get(i);
			  IdentityC c = mp.get(e);
			  remove(c.id);
		    }
		}
	  });

    }

    public int register(int e) {
	  m.put(c, e);
	  return c++;
    }

    public void remove(int id) {
	  m.remove(id, -1);
    }

    public int get(int id) {
	  return m.get(id, -1);
    }

    @Override
    public void save(ProjectSaver s) {

    }

    @Override
    public void load(ProjectLoader ld) {

    }
}
