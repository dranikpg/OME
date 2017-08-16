package com.draniksoft.ome.menu.button_bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.menu.dynamic_vs.DynamicViewController;
import com.draniksoft.ome.utils.SUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class ButtonBar extends VisTable {

    DynamicViewController vc;
    VisLabel fps;

    VisTextButton editB;

    VisTextButton exitB;

    public ButtonBar(DynamicViewController _vc){

        this.vc = _vc;

        editB = new VisTextButton(SUtils.f_m("editor_b"),"blue");
        editB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                vc.open(DynamicViewController.Codes.EDITOR);
            }
        });


        exitB = new VisTextButton(SUtils.f_m("exit_b"),"blue");
        exitB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Gdx.app.exit();
            }
        });

        add(editB).padBottom(20);
        row();
        add(exitB);

        fps = new VisLabel("0");
        row().padTop(50);add(fps);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        fps.setText(" " + Gdx.graphics.getFramesPerSecond());
    }
}
