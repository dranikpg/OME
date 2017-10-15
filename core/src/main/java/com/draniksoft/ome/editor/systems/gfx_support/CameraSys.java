package com.draniksoft.ome.editor.systems.gfx_support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.manager.MapMgr;

public class CameraSys extends BaseSystem implements InputProcessor {

    public static final String tag = CameraSys.class.getSimpleName();

    @Wire(name = "game_cam")
    OrthographicCamera camera;

    @Wire
    InputMultiplexer multiplexer;

    MapMgr mapM;

    float oneZoomU = 0.1f;
    float dragS = 0.01f;


    Vector2 stv;

    @Override
    protected void initialize() {
        multiplexer.addProcessor(this);

        stv = new Vector2();
    }

    @Override
    protected void processSystem() {

        camera.update();

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        stv.set(screenX, screenY);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        float tdrgas = dragS * camera.zoom;

        camera.translate(
                tdrgas * (stv.x - screenX),
                -tdrgas * (stv.y - screenY)
        );


        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }


    @Override
    public boolean scrolled(int amount) {

        if (amount > 0) {
            camera.zoom += oneZoomU;
        } else {
            camera.zoom -= oneZoomU;
        }

        camera.zoom = MathUtils.clamp(camera.zoom, 0.2f, 10);

        return false;
    }
}
