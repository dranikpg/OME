package com.draniksoft.ome.editor.systems.render;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class FloatUIRenderSys extends BaseSystem {

    @Wire(name = "game_stage")
    Stage s;

    @Override
    protected void processSystem() {

        s.act();
        s.draw();

    }
}
