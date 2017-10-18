package com.draniksoft.ome.editor.ui.comp_obs_ts;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.draniksoft.ome.editor.components.time.TimedC;
import com.draniksoft.ome.editor.support.actions.timed._base_.ChangeTimedCValsA;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.utils.ESCUtils;
import com.kotcrab.vis.ui.util.InputValidator;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class TimedOTable extends VisTable {

    private static final String tag = "TimedOTable";

    VisValidatableTextField sT;
    VisValidatableTextField eT;

    protected int _e;
    private World w;

    public TimedOTable() {


    }

    public class TeValidator implements InputValidator {

        @Override
        public boolean validateInput(String input) {

            try {
                Integer.parseInt(input);
                return true;
            } catch (Exception e) {
                return false;
            }


        }


    }

    public void init(int _e, World w) {

        this._e = _e;
        this.w = w;

        w.getSystem(EventSystem.class).registerEvents(this);

        sT = new VisValidatableTextField(new TeValidator());
        sT.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());
        eT = new VisValidatableTextField(new TeValidator());
        eT.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());

        eT.addCaptureListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {

                if (character == '\n') {
                    saveVals();
                }

                return super.keyTyped(event, character);

            }
        });

        sT.addCaptureListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {

                if (character == '\n') {
                    saveVals();
                }

                return super.keyTyped(event, character);

            }
        });

        add("From : ").padLeft(10);
        add(sT).expandX().fillX().padLeft(10).padRight(10);


        row();

        add("To : ").padLeft(10);
        add(eT).expandX().fillX().padLeft(10).padRight(10);

        updateVals();


    }

    private void saveVals() {

        if (!(sT.isInputValid() || eT.isInputValid())) return;

        ChangeTimedCValsA a = new ChangeTimedCValsA();
        a.s = Integer.parseInt(sT.getText());
        a.e = Integer.parseInt(eT.getText());
        a._e = _e;

        w.getSystem(ActionSystem.class).exec(a);

    }


    private void updateVals() {

        Gdx.app.debug(tag, "Updating vals");

        TimedC c = w.getMapper(TimedC.class).get(_e);
        if (c == null) return;

        sT.setText("" + c.s);
        eT.setText("" + c.e);


    }

    @Subscribe(priority = ESCUtils.EVENT_DEF_PRIORITY)
    public void DataChanged(EntityDataChangeE.TimedValsChange e) {

        Gdx.app.debug(tag, "Got event");

        if (e._e == this._e) {

            updateVals();

        }

    }


}
