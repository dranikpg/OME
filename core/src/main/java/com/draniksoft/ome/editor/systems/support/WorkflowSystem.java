package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.support.workflow.EditMode;
import com.draniksoft.ome.editor.systems.time.TimeActivitySys;

public class WorkflowSystem extends BaseSystem {

    private final String tag = "WorkflowSystem";

    boolean eM = true;

    EditMode curEM;

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void processSystem() {

    }

    public void changeMode(boolean editMode) {

        if (eM == editMode) return;

        eM = editMode;

        Gdx.app.debug(tag, "Changing mode to " + (editMode ? "Editmode" : "Showmode"));


        if (eM) {

            stopShowMode();
            startEditMode();

        } else {

            stopEditMode();
            startShowMode();

        }


    }

    private void stopShowMode() {

        world.getSystem(TimeActivitySys.class).setEnabled(false);

    }

    private void stopEditMode() {

        if (curEM != null) curEM.stopped();

    }

    private void startEditMode() {

        if (curEM != null) curEM.resumed();

    }

    private void startShowMode() {

        world.getSystem(TimeActivitySys.class).setEnabled(true);

    }

    public void attachEditMode(EditMode mode) {

        if (curEM != null) {
            curEM.detached();
        }

        curEM = mode;

        if (curEM == null) return;

        mode.attached(world);


    }

    public void detachEditMode() {
        attachEditMode(null);
    }

    public EditMode getCurEM() {
        return curEM;
    }
}
