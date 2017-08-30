package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.support.input.SelectIC;

public class InputSys extends BaseSystem implements InputProcessor {

    static final String tag = "InputSys";

    @Wire
    World phys;

    @Wire(name = "game_vp")
    Viewport gameVP;

    @Wire
    InputMultiplexer mx;

    // aka the current tool
    InputController mainIC;

    // aka the behavior
    InputController defIC;

    @Override
    protected void initialize() {

        mx.addProcessor(this);

        setDefIC(new SelectIC());
    }

    @Override
    protected void processSystem() {

        if (mainIC != null) {
            mainIC.update();
        }

        if (defIC != null) {
            defIC.update();
        }

    }


    public void setMainIC(InputController mainIC) {

        if (this.mainIC != null) {
            this.mainIC.destruct();
        }

        this.mainIC = mainIC;

        if (mainIC != null) mainIC.init(world);
    }

    public void setDefIC(InputController defIC) {

        if (this.defIC != null) {
            this.defIC.destruct();
        }

        this.defIC = defIC;

        if (defIC != null) defIC.init(world);
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

        if (((mainIC == null) || !mainIC.touchDown(screenX, screenY, pointer, button)) && defIC != null) {
            defIC.touchDown(screenX, screenY, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if ((mainIC == null || mainIC.touchUp(screenX, screenY, pointer, button)) && defIC != null) {
            defIC.touchUp(screenX, screenY, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if ((mainIC == null || mainIC.touchDragged(screenX, screenY, pointer)) && defIC != null) {
            defIC.touchDragged(screenX, screenY, pointer);
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        if ((mainIC == null || mainIC.mouseMoved(screenX, screenY)) && defIC != null) {
            defIC.mouseMoved(screenX, screenY);
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        if (mainIC != null) {
            mainIC.scrolled(amount);
        }

        return false;
    }

    public InputController getMainIC() {
        return mainIC;
    }

    public InputController getDefIC() {
        return defIC;
    }
}
