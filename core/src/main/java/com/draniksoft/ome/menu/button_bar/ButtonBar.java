package com.draniksoft.ome.menu.button_bar;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.menu.dynamic_vs.DynamicViewController;
import com.draniksoft.ome.utils.SUtils;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class ButtonBar extends VisTable {

    DynamicViewController vc;

    public ButtonBar(DynamicViewController _vc){

        this.vc = _vc;

        VisTextButton t1B = new VisTextButton(SUtils.f_m("editor_b"),"blue");
        t1B.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                vc.open(DynamicViewController.Codes.EDITOR);
            }
        });


        VisTextButton t2B = new VisTextButton("t2","blue");
        t2B.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                vc.open(DynamicViewController.Codes.EDITOR_NEW_MAP);
            }
        });

        add(t1B).padBottom(20);
        row();
        add(t2B);
    }

}
