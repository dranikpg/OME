package com.draniksoft.ome.editor.components.tps

import com.artemis.Component


/*
     Class[Component] for representing the non active data about an object
     It is used to serialize itself
 */

class MObjectC : Component() {

    // start positions :: center
    var x: Int = 0
    var y: Int = 0

    // width height :: whole size
    var w: Int = 0
    var h: Int = 0

    /* id::uri for a drawable
    */
    var dwbData: String? = null

}
