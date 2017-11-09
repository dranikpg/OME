package com.draniksoft.ome.editor.ui.core;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.utils.Env;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;

import static com.draniksoft.ome.editor.ui.core.menu.MenuContentSupplierI.id;

public abstract class PanelWin extends BaseWin {


    protected float gw, gh;
    private boolean pctm = true;
    protected float w = 0;
    float xc = 0;
    protected float wp = 0;

    Table topT;
    VisLabel nameL;
    VisTextButton closeB;

    Table c;

    float dur = 1f;
    Interpolation ipol = Interpolation.pow3;

    @Override
    public void _init() {
        setBackground("d_grey");

        buildBaseUI();

        on_init();

        updateName();

        setDebug(Env.GFX_DEBUG);

    }

    public void open(String args, boolean delay) {
        on_open(args);

        toFront();
        setVisible(true);
        open = true;

        addAction(Actions.sequence(

                Actions.delay(delay ? dur * 0.7f : 0f),
                Actions.moveTo(xc, 0, dur, ipol)

        ));


    }

    public void close() {
        on_close();

        toBack();
        open = false;
        addAction(Actions.sequence(
                Actions.moveTo(gw, 0, dur, ipol),
                Actions.visible(false)
        ));
    }


    protected void buildBaseUI() {
        topT = new Table();
        nameL = new VisLabel(getClass().getSimpleName(), "title");
        nameL.setWrap(true);
        closeB = new VisTextButton("X");
        closeB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                _w.getSystem(UiSystem.class).close(id);
            }
        });

        topT.add(nameL).expandX();
        topT.add(closeB).padLeft(20).padRight(20).right();
        add(topT).expandX().fillX();

        row();

        c = new Table();
        add(c).expand().fill();

    }


    public void updateBounds(float gw, float gh) {
        this.gw = gw;
        this.gh = gh;

        setHeight(gh);

        if (pctm) {
            wp = MathUtils.clamp(wp, 0f, 1f);
            float rw = gw * wp;
            w = rw;
            xc = gw - rw;

        } else {
            xc = gw - w;
        }

        if (open) {
            setX(xc);
        } else {
            setX(gw);
        }

        setWidth(w);
    }

    protected void updateName() {
        nameL.setText(name);
        setName(name);
    }

    protected void setPctMode(boolean m) {
        pctm = m;
        updateBounds(gw, gh);
    }


    protected abstract void on_close();

    protected abstract void on_open(String args);

    protected abstract void on_init();


    public <T extends Actor> Cell<T> _add(T actor) {
        return c.add(actor);
    }

    public Cell _row() {
        return c.row();
    }


    public void immdClose() {
        setX(gw);
        setVisible(false);
    }

}
