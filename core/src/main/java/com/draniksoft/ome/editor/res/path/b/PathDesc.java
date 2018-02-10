package com.draniksoft.ome.editor.res.path.b;

import com.draniksoft.ome.utils.struct.Points;
import dint.Dint;

public class PathDesc {

    public PathDesc() {
	  ar = new Points();
    }

    public int st = Dint.compose(1, 0, 0);
    public int et = Dint.compose(7, 0, 0);

    public Points ar;


}
