package com.draniksoft.ome.editor.systems.gfx_support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraSys extends BaseSystem {

    @Wire
    OrthographicCamera camera;

    @Override
    protected void processSystem() {

        camera.update();

    }


}
