package com.draniksoft.ome.menu.dynamic_vs.vs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.menu.ControlUtils;
import com.draniksoft.ome.menu.dynamic_vs.DynamicView;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class NewMapView extends DynamicView {


    VisTextButton createB;

    public NewMapView(){

    }

    @Override
    public void init() {

        createB = new VisTextButton("Create");
        createB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ControlUtils.launchEditor(new MapLoadBundle());
            }
        });

        addCps();


    }

    private void addCps() {

        add(createB).pad(50);

    }

    @Override
    public void open(String args) {

    }

    @Override
    public void on_close() {

    }

    @Override
    public void on_refresh_req() {

    }
}
