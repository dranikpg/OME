package com.draniksoft.ome.editor;

import com.artemis.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.utils.GUtils;


public class EditorAdapter extends ApplicationAdapter {

    public static String tag = "EditorAdapter";

    private static World engine;
    MapLoadBundle bundle;

    Viewport gameVP;
    Viewport uiVP;


    public EditorAdapter(MapLoadBundle bundle){
        this.bundle = bundle;

    }

    @Override
    public void create() {


        GUtils.getWindow().setVisible(true);


    }

    @Override
    public void render() {

        engine.process();

       // System.out.println(Gdx.graphics.getFramesPerSecond());

    }


    @Override
    public void resize(int width, int height) {



    }


    @Override
    public void pause() {

        Gdx.app.debug(tag, "AppAdapter :: Paused ");

    }


    @Override
    public void dispose() {


    }
}

