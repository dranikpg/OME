package com.draniksoft.ome.editor;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.draniksoft.ome.editor.launch.LaunchBundle;
import com.draniksoft.ome.utils.GUtils;


public class EditorAdapter extends ApplicationAdapter {

    private static World engine;
    LaunchBundle bundle;


    public EditorAdapter(LaunchBundle bundle){
        this.bundle = bundle;

    }

    @Override
    public void create() {

        engine = loadEngine();


        GUtils.getWindow().setVisible(true);


    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        engine.process();

    }

    public World loadEngine(){

        if(engine != null){
            return engine;
        }

        WorldConfigurationBuilder configurationBuilder = new WorldConfigurationBuilder();


        WorldConfiguration configuration = configurationBuilder.build();


        return new World(configuration);



    }

}

