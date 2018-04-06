package com.draniksoft.ome.editor.res.color.constr;

import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.editor.res.impl.constructor.GroupResConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;

public class ColorGroupConstructor extends GroupResConstructor<ColorProvider> {


    @Override
    public WeakLinkedResource<ColorProvider> getSnapshotLink() {
	  return null;
    }


}
