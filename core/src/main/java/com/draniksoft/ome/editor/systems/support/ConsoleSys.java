package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.draniksoft.ome.editor.support.CommandExecutor;
import com.strongjoshua.console.GUIConsole;

public class ConsoleSys extends BaseSystem {

    @Wire
    InputMultiplexer mxp;

    GUIConsole console;



    @Override
    protected void initialize() {

        console = new GUIConsole();

        console.setCommandExecutor(new CommandExecutor(world));

        console.setDisplayKeyID(Input.Keys.F1);

        mxp.addProcessor(console.getInputProcessor());
    }

    @Override
    protected void processSystem() {


        console.draw();


    }

    public void resize(int width, int height) {

        console.refresh(true);

    }
}
