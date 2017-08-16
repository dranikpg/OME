package com.draniksoft.ome.menu.dynamic_vs.vs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.menu.dynamic_vs.DynamicView;
import com.draniksoft.ome.menu.dynamic_vs.DynamicViewController;
import com.draniksoft.ome.utils.SUtils;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class EditorView extends DynamicView {

    VisTextButton newMapB;
    VisTextButton loadM;

    DynamicViewController vc;

    public EditorView(DynamicViewController vc){

        this.vc = vc;

    }

    @Override
    public void init() {

        newMapB = new VisTextButton(SUtils.f_m("new_map_b"));
        loadM = new VisTextButton("DUMMY");


        newMapB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                vc.open(DynamicViewController.Codes.EDITOR_NEW_MAP);
            }
        });


        add(newMapB).expandX().center().padTop(20);
        add(loadM).expandX().center().padTop(20);

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
