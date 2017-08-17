package com.draniksoft.ome.editor.systems.render;

import com.artemis.BaseSystem;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UIRenderSys extends BaseSystem {

    Stage stage;

    @Override
    protected void processSystem() {

        stage.act();

        stage.draw();

    }
}
