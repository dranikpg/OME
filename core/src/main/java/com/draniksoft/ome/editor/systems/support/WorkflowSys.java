package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class WorkflowSys extends BaseSystem {

    private static final String tag = "WorkflowSys";


    // true :: showmode, false :: editmode
    public boolean SHOW_M = false;

    @Override
    protected void initialize() {

        applyMode();

    }

    public void switchMode(boolean v) {

        if (v == SHOW_M) return;

        SHOW_M = v;

        applyMode();


    }

    private void applyMode() {

        Gdx.app.debug(tag, "Applying for mode " + SHOW_M);

        world.getSystem(EventSystem.class).dispatch(new ModeChangeE(SHOW_M));

    }

    @Override
    protected void processSystem() {

    }
}
