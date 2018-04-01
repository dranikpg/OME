package com.draniksoft.ome.editor.systems.gfx_support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.draniksoft.ome.utils.GU;
import com.draniksoft.ome.utils.cam.Target;

public class CameraSys extends BaseSystem implements InputProcessor {

    public static final String tag = CameraSys.class.getSimpleName();

    @Wire(name = "game_cam")
    OrthographicCamera camera;

    @Wire
    InputMultiplexer multiplexer;

    float oneZoomU = 0.1f;
    float dragS = 0.1f;

    Vector2 stv;
    boolean ignore = false;

    Target t;

    Vector2 tmpV;
    Vector3 tmp3;

    @Override
    protected void initialize() {
        multiplexer.addProcessor(this);
        stv = new Vector2();

	  tmpV = new Vector2();
	  tmp3 = new Vector3();
    }

    @Override
    protected void processSystem() {

	  camera.update();

	  GU.CAM_SCALE = camera.zoom;

	  if (t != null) {
		lerpToTarget();
	  }

    }

    private void lerpToTarget() {

	  t.getPos(tmpV);
	  camera.position.lerp(tmp3.set(tmpV, camera.position.z), t.alpha);

	  if (Math.abs(tmp3.dst(camera.position)) < t.treesHold) {
		if (t.freeOnReach) {
		    Gdx.app.debug(tag, "Freeing target on reach");
		    t = null;
		}

	  }

    }

    public void setTarget(Target t) {
	  Gdx.app.debug(tag, "New target");
	  this.t = t;
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

	  if (button == 1 || (t != null && t.locked)) {
		ignore = true;
            return false;
        }

        stv.set(screenX, screenY);


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        ignore = false;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

	  if (ignore || !canMove()) return false;

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


    boolean canMove() {
	  return t == null || !t.locked;
    }

    Target bk;

    public void createBK() {
	  bk = t;
	  t = null;
    }

    public void inflateBK() {
	  t = bk;
    }

    public void removeWeak() {
	  if (t.dieOnDrag) {
		t = null;
	  }
    }
}
