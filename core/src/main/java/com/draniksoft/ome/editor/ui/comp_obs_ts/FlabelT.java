package com.draniksoft.ome.editor.ui.comp_obs_ts;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.components.selection.FLabelC;
import com.draniksoft.ome.editor.support.actions.flabel.ChangeFlabelTxtA;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class FlabelT extends VisTable {

    private static final String tag = "FLabelT";

    World _w;
    int _e;

    VisTextField f;

    VisCheckBox changeS;

    VisSlider size;

    public FlabelT(World _w, int _e) {
        this._w = _w;
        this._e = _e;

        _w.getSystem(EventSystem.class).registerEvents(this);

        f = new VisTextField();

        f.setTextFieldListener(new VisTextField.TextFieldListener() {
            @Override
            public void keyTyped(VisTextField textField, char c) {

                if (c == '\n') {
                    saveT();
                    getStage().setKeyboardFocus(null);
                }

            }
        });

        changeS = new VisCheckBox("Change scale on zoom");

        size = new VisSlider(0.5f, 1.5f, 0.25f, false);
        size.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveSz();
            }
        });


        add("Text : ").padLeft(10);
        add(f).expandX().fillX().padRight(10);

        add(changeS);

        row().padBottom(10);

        add("Size ");
        add(size);


    }


    @Subscribe
    public void tChanged(EntityDataChangeE.FlabelTextChangeE e) {

        if (e._e == _e) {
            update();
        }

    }

    public void saveSz() {
        Gdx.app.debug(tag, "Saving size " + size.getValue());

        FLabelC c = _w.getMapper(FLabelC.class).get(_e);

        c.sizeM = size.getValue();

    }

    public void saveSC() {


    }

    private void saveT() {

        String t = f.getText();

        ChangeFlabelTxtA a = new ChangeFlabelTxtA();
        a._e = _e;
        a.newT = t;

        _w.getSystem(ActionSystem.class).exec(a);

    }

    private String getEtext() {

        return _w.getMapper(FLabelC.class).get(_e).txt;

    }

    private void update() {

        f.setText(getEtext());


    }

}
