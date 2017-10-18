package com.draniksoft.ome.editor.support.input;

import com.artemis.World;
import com.badlogic.gdx.Input;

public interface InputController {

    void init(World w);

    void destruct();

    void update();

    /**
     *
     * Descriptions copied from badlogicgames::libgdx::inputProcessor
     *
     * A main controller should return true to stop the second from processing the input
     *
     */

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    boolean keyDown(int keycode);

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    boolean keyUp(int keycode);

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     *
     * @param screenX The end_x coordinate, origin is in the upper left corner
     * @param screenY The end_y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    boolean touchDown(int screenX, int screenY, int pointer, int button);

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     *
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    boolean touchUp(int screenX, int screenY, int pointer, int button);

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    boolean touchDragged(int screenX, int screenY, int pointer);

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @return whether the input was processed
     */
    boolean mouseMoved(int screenX, int screenY);

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    boolean scrolled(int amount);

}
