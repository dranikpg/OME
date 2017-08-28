package com.draniksoft.ome.editor.systems.render;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.draniksoft.ome.utils.PUtils;

public class PhysRDebugSys extends BaseSystem {

    @Wire
    World pw;

    Box2DDebugRenderer r;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    @Override
    protected void initialize() {
        r = new Box2DDebugRenderer();
    }

    @Override
    protected void processSystem() {

        r.render(pw, cam.combined.cpy().scl(PUtils.PPM));

    }
}
