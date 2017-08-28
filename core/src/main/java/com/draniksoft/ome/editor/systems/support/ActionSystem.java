package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.support.actions.Action;

import java.util.LinkedList;

public class ActionSystem extends BaseSystem {

    public static final String tag = "ActionSystem";

    // The history
    LinkedList<Action> stack;
    int maxStackSize = 10;

    @Override
    protected void processSystem() {

        stack = new LinkedList<Action>();

    }

    public void exec(Action a) {

        Gdx.app.debug(tag, "Executing " + a.getClass().getSimpleName());

        a._do(world);


        stack.add(a);

        if (a.isCleaner()) {
            stack.clear();
            return;
        }

        if (stack.size() > maxStackSize) {
            Gdx.app.debug(tag, "Action stack overflow");
            stack.remove(0);
        }

    }

    public void undo() {

        int i = stack.size();

        while (i > 0) {

            Action a = stack.get(i);

            if (a.isUndoable()) {

                Gdx.app.debug(tag, "Undoing " + a.getClass().getSimpleName());

                a._undo(world);
                stack.removeLast();
                return;
            }

            i--;
        }


    }


}
