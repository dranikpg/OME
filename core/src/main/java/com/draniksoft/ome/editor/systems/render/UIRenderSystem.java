package com.draniksoft.ome.editor.systems.render;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UIRenderSystem extends BaseSystem {

    @Wire(name = "top_stage")
    Stage stage;

    @Wire(name = "ui_cam")
    OrthographicCamera cam;

    @Override
    protected void processSystem() {

        cam.update();

        stage.act();

        stage.draw();

    }
}
