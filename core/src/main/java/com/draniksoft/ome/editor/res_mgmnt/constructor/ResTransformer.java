package com.draniksoft.ome.editor.res_mgmnt.constructor;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.res_mgmnt.res_ifaces.RootResource;
import com.draniksoft.ome.utils.FUtills;

public class ResTransformer {

    ResConstructor rootConst;
    int t = -1;

    public RootResource parse(String src, int t) {
	  this.t = t;

	  JsonValue v = FUtills.r.parse(src);


	  return null;
    }

}
