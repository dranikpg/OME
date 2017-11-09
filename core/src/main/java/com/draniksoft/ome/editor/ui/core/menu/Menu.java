package com.draniksoft.ome.editor.ui.core.menu;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonValue;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTable;

import java.lang.reflect.Field;

public class Menu {

    private static final String tag = "UIMenu";

    VisImageButton btn;

    VisTable ct;
    ScrollPane contentPane;

    World _w;

    IntMap<MenuContentSupplierI> supps;
    int curSup;


    float defPadding = 10f;
    float btnMP = 20f;
    float btnBrdP = 20f;

    float dur = .35f;
    float adur = .2f;

    Interpolation ipol = Interpolation.pow3;
    Interpolation aipol = Interpolation.pow2;

    public Menu() {

    }

    public MenuContentSupplierI getSup() {
        if (curSup > 0) return supps.get(curSup);
        return null;
    }

    public void init(World w) {
        this._w = w;

        initUI();

        contentPane.setX(-contentPane.getWidth());

    }

    public void buildSups(JsonValue rootV) {

        supps = new IntMap<MenuContentSupplierI>();

        JsonValue vs = rootV.get("menu");

        for (JsonValue val : vs) {

            int k = Integer.valueOf(val.name);
            try {
                Class c = Class.forName("com.draniksoft.ome.editor.ui.menu." + val.asString());

                MenuContentSupplierI s = (MenuContentSupplierI) c.getConstructor().newInstance();

                s.init(this, _w);

                Field field = s.getClass().getField("id");
                field.setAccessible(true);
                field.set(null, k);

                supps.put(k, s);

            } catch (Exception e) {
                Gdx.app.error(tag, "", e);
            }


        }

        Gdx.app.debug(tag, "Parsed " + supps.size + " suppliers");


    }

    private void initUI() {

        btn = new VisImageButton("menu");
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btnPressed();
            }
        });
        btn.setProgrammaticChangeEvents(false);

        ct = new VisTable();
        contentPane = new ScrollPane(ct);

        contentPane.setScrollingDisabled(true, false);

    }

    private void btnPressed() {

        if (contentPane.getActions().size > 0) return;

        if (btn.isChecked()) {
            inflateSup(1, false);
            open();
        } else {
            close();
        }

    }

    public void inflateSup(int id) {
        inflateSup(id, true);
    }

    public void inflateSup(int id, boolean trans) {

        if (id == curSup) return;

        curSup = id;
        if (!supps.containsKey(id)) return;

        if (trans) {
            contentPane.addAction(Actions.sequence(

                    Actions.moveTo(-contentPane.getWidth(), 0, adur, aipol),

                    new Action() {
                        @Override
                        public boolean act(float delta) {
                            ct.clearChildren();
                            supps.get(curSup).build(ct);
                            contentPane.setWidth(ct.getWidth());
                            return true;
                        }
                    },

                    Actions.moveTo(0, 0, adur, aipol)


            ));
        } else {
            ct.clearChildren();
            supps.get(curSup).build(ct);
            contentPane.setWidth(ct.getWidth());
        }

    }

    public void close() {


        curSup = -1;

        btn.setChecked(false);

        contentPane.clearActions();
        contentPane.addAction(Actions.sequence(
                Actions.moveTo(-contentPane.getWidth(), 0, dur, ipol)
        ));

    }

    public void open(String args) {

        if (args != null) {
            try {
                int v = Integer.parseInt(args);
                inflateSup(v);
            } catch (Exception e) {
            }
        }

        if (curSup == -1) inflateSup(-1);

        open();
    }

    private void open() {

        btn.setChecked(true);
        contentPane.toFront();

        contentPane.clearActions();
        contentPane.addAction(Actions.sequence(
                Actions.moveTo(0, 0, dur, ipol)
        ));

    }

    public void addToStage(Stage st) {

        st.addActor(btn);
        st.addActor(contentPane);

    }

    public void resized(float w, float h) {

        btn.setSize(50 * Math.min(w, h) / 1000f, 50 * Math.min(w, h) / 1000f);

        btn.setPosition(btnBrdP, h - btn.getHeight() - btnBrdP);

        contentPane.setHeight(h - btn.getHeight() - btnBrdP - btnMP);

    }


    public boolean isOpen() {
        return btn.isChecked();
    }

    public int getCurSup() {
        return curSup;
    }

    public float getDefPadding() {
        return defPadding;
    }
}
