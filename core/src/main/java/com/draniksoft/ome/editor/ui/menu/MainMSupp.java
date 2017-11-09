package com.draniksoft.ome.editor.ui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.ui.core.menu.MenuContentSupplierI;
import com.draniksoft.ome.utils.SUtils;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class MainMSupp extends MenuContentSupplierI {

    Table ct;

    @Override
    public void _init() {
        buildCT();

    }

    String es = "editor_main_menu";

    protected void buildCT() {

        ct = new Table();


        VisTextButton exitToM = new VisTextButton(SUtils.getS("exit_to_menu"), es);
        exitToM.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        VisTextButton saveB = new VisTextButton(SUtils.getS("save"), es);

        VisTextButton loadB = new VisTextButton(SUtils.getS("open"), es);
        loadB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                m.inflateSup(OpenMSupp.id);
            }
        });

        VisTextButton settingsB = new VisTextButton(SUtils.getS("settings"), es);

        VisTextButton projB = new VisTextButton(SUtils.getS("project"), es);

        addBL(loadB);
        addBL__(saveB);

        addBL(projB);
        addBL__(settingsB);

        addBL(exitToM);

    }


    protected void addBL__(VisTextButton b) {
        b.padLeft(10).padRight(10);
        addLine(b, true);
    }

    protected void addBL(VisTextButton b) {
        b.padLeft(10).padRight(10);
        addLine(b, false);
    }

    protected void addLine(Actor b, boolean doubleP) {

        ct.add(b).expandX().fillX().padTop(doubleP ? m.getDefPadding() : 4 * m.getDefPadding());
        ct.row();

    }

    @Override
    public void build(Table t) {

        t.setWidth(ct.getPrefWidth());

        t.add(ct).expand().fill();

    }

}
