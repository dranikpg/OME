package com.draniksoft.ome.editor.components.pos;

import com.artemis.Component;
import com.artemis.annotations.DelayedComponentRemoval;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.physics.box2d.Body;


@Transient
@DelayedComponentRemoval
public class PhysC extends Component {

    public Body b;

}
