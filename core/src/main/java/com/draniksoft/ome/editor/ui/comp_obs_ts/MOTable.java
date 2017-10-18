package com.draniksoft.ome.editor.ui.comp_obs_ts;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.actions.mapO.ChangeDwbA;
import com.draniksoft.ome.editor.support.actions.mapO.MoveMOA;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.ems.MoveMOEM;
import com.draniksoft.ome.editor.support.event.EditModeChangeE;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.utils.WinResponseListener;
import com.draniksoft.ome.editor.ui.wins.asset.DrawableSelectionWin;
import com.draniksoft.ome.utils.ESCUtils;
import com.kotcrab.vis.ui.widget.*;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class MOTable extends VisTable {

    private static final String tag = "MOTable";

    VisTable curDWBT;
    VisImageButton curDWB;

    VisLabel xC;
    VisLabel yC;

    VisTextField xF;
    VisTextField yF;

    VisTextButton emTgrB;

    VisTable rightT;

    World _w;
    int _e;

    PosSizeC psc;

    ChangeListener dwl;


    public MOTable() {

    }

    public void init(int e, final World w) {

        this._w = w;
        this._e = e;

        psc = w.getMapper(PosSizeC.class).get(e);

        w.getSystem(EventSystem.class).registerEvents(this);


        dwl = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                _w.getSystem(UiSystem.class).open(UiSystem.WinCodes.drawableSelWin, null);
                DrawableSelectionWin win = (DrawableSelectionWin) _w.getSystem(UiSystem.class).getWin(UiSystem.WinCodes.drawableSelWin);

                if (win == null) return;

                win.reinit(new WinResponseListener() {
                    @Override
                    public void rsp(short code, Object info) {

                        if (code == WinResponseListener.submitted) {

                            saveDW((String) info);

                        }

                    }
                });

            }
        };

        curDWBT = new VisTable();


        xC = new VisLabel();
        yC = new VisLabel();

        xF = new VisTextField();
        xF.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());

        xF.addCaptureListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {

                if (character == '\n') {
                    getStage().setKeyboardFocus(null);
                    saveVals();
                }

                return super.keyTyped(event, character);

            }
        });
        xF.setFocusTraversal(true);

        yF = new VisTextField();
        yF.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());
        yF.addCaptureListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {

                if (character == '\n') {
                    getStage().setKeyboardFocus(null);
                    saveVals();
                }

                return super.keyTyped(event, character);

            }
        });

        emTgrB = new VisTextButton("Edit position");
        emTgrB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                w.getSystem(EditorSystem.class).attachNewEditMode(EditModeDesc.IDS.moveMO);
            }
        });

        rightT = new VisTable();
        rightT.add("X : ");
        rightT.add(xF);
        rightT.row();
        rightT.add("Y : ");
        rightT.add(yF);
        rightT.row();
        rightT.add(emTgrB).padTop(20).colspan(2);

        add(curDWBT).expand().fill().padRight(10).left();
        add(rightT).expand().fill();

        updateVals();

    }

    private void saveDW(String dwid) {

        ChangeDwbA a = new ChangeDwbA();
        a._e = _e;
        a.dwid = dwid;

        _w.getSystem(ActionSystem.class).exec(a);

    }

    private void saveVals() {

        MoveMOA a = new MoveMOA();
        a.x = Float.parseFloat(xF.getText());
        a.y = Float.parseFloat(yF.getText());
        a._e = _e;

        _w.getSystem(ActionSystem.class).exec(a);


    }

    @Subscribe(priority = ESCUtils.EVENT_DEF_PRIORITY)
    public void EMChangeE(EditModeChangeE e) {

        if (e.newEM != null && e.newEM instanceof MoveMOEM) {
            xF.setDisabled(true);
            yF.setDisabled(true);
        } else {

            xF.setDisabled(false);
            yF.setDisabled(false);

        }

    }

    @Subscribe(priority = ESCUtils.EVENT_DEF_PRIORITY)
    public void DataChanged(EntityDataChangeE e) {

        if (e._e == this._e) {
            this._e = e._e;
            updateVals();

        }

    }

    private void updateVals() {

        xF.setText(String.valueOf(psc.x + psc.w / 2));
        yF.setText(String.valueOf(psc.y + psc.w / 2));

        curDWBT.clearChildren();


        TextureRegionDrawable d = new TextureRegionDrawable(_w.getSystem(DrawableMgr.class).getRegion(_w.getMapper(MObjectC.class).get(_e).dwbID));

        if (d == null) return;

        curDWB = new VisImageButton(d);
        curDWBT.add(curDWB).expand().fill();
        curDWB.addListener(dwl);
    }

}
