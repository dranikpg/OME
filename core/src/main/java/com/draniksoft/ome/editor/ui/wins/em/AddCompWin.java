package com.draniksoft.ome.editor.ui.wins.em;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.ReliantBaseWin;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.Iterator;

public class AddCompWin extends ReliantBaseWin {

    private static final String tag = "AddCompWin";

    private int _e;

    World _w;

    ScrollPane p;
    VisTable root;

    public AddCompWin() {
        super("Add C");
    }

    public void reinit(int _e) {
        this._e = _e;
        rebuild();
    }

    private void rebuild() {

        root.clearChildren();

        Iterator<CompositionObserver> i = _w.getSystem(EditorSystem.class).getComObsI();

        CompositionObserver o;
        while (i.hasNext()) {
            o = i.next();

            if (o.isAviab(ActionDesc.BaseCodes.ACTION_CREATE)) {

                addPsb(o.getDesc(ActionDesc.BaseCodes.ACTION_CREATE), o);

            }


        }

    }

    private void addPsb(final ActionDesc desc, final CompositionObserver o) {

        VisTextButton b = new VisTextButton(desc.getName(), "menu-bar");

        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                o.execA(desc.code, _e);
                closeSelf();

            }
        });


        root.add(b);
        root.row();

    }

    private void closeSelf() {
        _w.getSystem(UiSystem.class).close(code);
    }


    @Override
    public void init(World w) {

        this._w = w;

        root = new VisTable();
        p = new ScrollPane(root);
        p.setScrollingDisabled(true, false);

        VisTextButton clozeb = new VisTextButton("X");
        clozeb.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                _w.getSystem(UiSystem.class).close(code);
            }
        });
        getTitleTable().add(clozeb).expand().right().padRight(5);


        add(p).expand().fill();

        setResizable(true);
        setResizeBorder(20);
        setSize(500, 300);
        setModal(true);

    }

    @Override
    public void on_opened(String uir) {
        centerWindow();
    }

    @Override
    public void on_closed() {

    }
}
