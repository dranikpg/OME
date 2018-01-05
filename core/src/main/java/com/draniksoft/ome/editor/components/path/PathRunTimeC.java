package com.draniksoft.ome.editor.components.path;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.support.container.path.PathRTDesc;

@Transient
public class PathRunTimeC extends Component {

    public Array<PathRTDesc> p;

    /**
     * Idx -> index of the path THAT HAS NOT BEEN FULLY PROCESSED (can be -1 if there is no such matching for global T)
     * . In case of time changeColor the new index should be found
     */

    public int idx;

}
