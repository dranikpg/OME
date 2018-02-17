package com.draniksoft.ome.editor.support.compositionObserver.abstr;

import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;

public abstract class EasierCompositionObserver extends SimpleCompositionObserver {

    IntMap<ActionDesc> desc;

    @Override
    protected void _init() {
	  desc = new IntMap<ActionDesc>();
	  if (__ds == null) return;
	  for (ActionDesc d : __ds) {
		desc.put(d.code, d);
	  }
    }


    @Override
    public IntMap<ActionDesc> getDesc() {
	  return desc;
    }


    @Override
    public boolean isAviab(int ac) {
	  return isAviab(ac, _selE);
    }

    @Override
    public ActionDesc getDesc(int ac) {
	  return desc.get(ac);
    }


}
