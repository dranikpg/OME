package com.draniksoft.ome.editor.systems.render;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class BaseRenderSys extends BaseSystem {


    @Override
    protected void initialize() {


    }

    @Override
    protected void processSystem() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.graphics.getFramesPerSecond() < 40) {
            Gdx.app.debug("FPS", "LOG FPS ");
        }

    }
}
