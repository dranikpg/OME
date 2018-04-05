package com.draniksoft.ome.editor.res.drawable.typedata;

import com.artemis.World;
import com.draniksoft.ome.editor.manager.ResourceManager;
import com.draniksoft.ome.editor.res.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;
import com.draniksoft.ome.editor.res.impl.types.ResTypes;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.utils.struct.MtPair;

public class LinkedDrawableTD implements ResDataHandler {

    MtPair<String, Integer> id;

    LinkedDrawable rt;

    public LinkedDrawableTD() {
	  id = new MtPair<String, Integer>();
    }

    @Override
    public void init() {
	  id = new MtPair<String, Integer>();
    }

    @Override
    public void initL(WeakLinkedResource link) {
	  rt = new LinkedDrawable(null);
	  link.set(rt);

	  setAddress(id.K(), id.V());
    }

    @Override
    public void deinitL() {
	  rt = null;
    }

    public void setAddress(String ext, int i_d) {
	  id.K(ext);
	  id.V(i_d);
	  if (rt != null)
		rt.setR(MainBase.engine.getSystem(ResourceManager.class).getR(ResTypes.DRAWABLE, ext, i_d));
    }

    @Override
    public Resource build(World w) {
	  LinkedDrawable ld = new LinkedDrawable(id.constant());
	  return ld;
    }

    @Override
    public ResDataHandler copy() {
	  LinkedDrawableTD td = new LinkedDrawableTD();
	  td.id = this.id.copy();
	  return td;
    }
}
