package com.draniksoft.ome.editor.res.color.constr;

import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.editor.res.impl.constructor.LeafConstructor;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;

public class ColorLeafConstructor extends LeafConstructor<ColorProvider> {
    @Override
    public WeakLinkedResource<ColorProvider> getSnapshotLink() {
	  return null;
    }


}
