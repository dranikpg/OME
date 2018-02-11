package com.draniksoft.ome.editor.components.path;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.struct.path.runtime.Path;

@Transient
public class PathRunTimeC extends Component {

    public Array<Path> ar;

    public int _last = 0;

}
