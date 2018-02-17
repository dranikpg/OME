package com.draniksoft.ome.editor.components.pos;

import com.artemis.Component;
import com.artemis.annotations.Transient;

/*
    Runtime version of map dimens
 */
@Transient
public class PosSizeC extends Component {

    // X and Y are LEFT BOTTOM COORDS
    public int x,y;
    public int w,h;

}
