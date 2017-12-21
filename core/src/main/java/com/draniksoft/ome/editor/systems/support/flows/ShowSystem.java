package com.draniksoft.ome.editor.systems.support.flows;

import com.artemis.BaseSystem;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import net.mostlyoriginal.api.event.common.Subscribe;

/*
	Support system for showin the map
	Will be used heavier in later things
 */

public class ShowSystem extends BaseSystem {


    @Override
    protected void initialize() {
    }

    @Override
    protected void processSystem() {

    }

    @Subscribe
    public void mode(ModeChangeE e) {

    }


}
