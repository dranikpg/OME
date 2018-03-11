package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.draniksoft.ome.editor.support.CommandExecutor;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.strongjoshua.console.GUIConsole;
import net.mostlyoriginal.api.event.common.Subscribe;

public class ConsoleSys extends BaseSystem {

    private static final String tag = "ConsoleSys";

    @Wire
    InputMultiplexer mxp;

    GUIConsole console;


    @Wire(name = "engine_l")
    ExecutionProvider l;


    @Override
    protected void initialize() {

	  l.addShd(new Gfx_ldr());

	  world.getSystem(OmeEventSystem.class).registerEvents(this);

    }

    @Override
    protected void processSystem() {

        console.draw();


    }


    @Subscribe
    public void resize(ResizeEvent e) {

        console.refresh(true);

    }

    private class Gfx_ldr extends SimpleSyncTask {

	  public Gfx_ldr() {
		super(1);
	  }

        @Override
	  public void run() {
		console = new GUIConsole();

            console.setCommandExecutor(new CommandExecutor(world));
            console.log("See the github wiki for more details");

            console.setDisplayKeyID(Input.Keys.F1);

		mxp.addProcessor(2, console.getInputProcessor());

		active = false;
	  }
    }
}
