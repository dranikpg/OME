package com.draniksoft.ome.editor.res.impl.ext_mgmnt;

import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.editor.support.track.ReferenceTracker;
import com.draniksoft.ome.utils.lang.Text;

public class ResContainer<T> implements ReferenceTracker {

    public boolean resolved = true;

    public Text name;
    public String handle;

    public ResConstructor<T> ctr;

    public transient RootResource<T> r;

    transient int refs = 0;

    @Override
    public int references() {
	  return refs;
    }

    @Override
    public int reference(int delta) {
	  return refs += delta;
    }
}
