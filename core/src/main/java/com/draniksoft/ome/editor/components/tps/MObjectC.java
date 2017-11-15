package com.draniksoft.ome.editor.components.tps;

import com.artemis.Component;


/*
     Class[Component] for representing the non active data about an object
     It is used to serialize itself
 */

public class MObjectC extends Component {

    // start positions :: center
    public int x, y;

    // width height :: whole size
    public int w;
    public int h;

    /* id::uri for a drawable
    */
    public String dwbData;

}
