package com.draniksoft.ome.editor.systems.render.obj;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.PUtils;

public class PhysRDebugSys extends BaseSystem {

    @Wire
    World pw;

    Box2DDebugRenderer r;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    @Wire(name = "engine_l")
    IntelligentLoader l;

    @Override
    protected void initialize() {

        l.passGLRunnable(new Gl_loader());

        setEnabled(Env.GFX_DEBUG);

    }

    @Override
    protected void processSystem() {

        r.render(pw, cam.combined.cpy().scl(PUtils.PPM));

    }

    private class Gl_loader implements IGLRunnable {

        @Override
        public byte run() {

            r = new Box2DDebugRenderer();

            return IGLRunnable.READY;
        }
    }
}
