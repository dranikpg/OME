package com.draniksoft.ome.editor.ui.wins.uimgmnt;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.ui.BaseWindow;
import com.draniksoft.ome.editor.ui.SupportedReliantWin;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisRadioButton;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class WindowMgrWin extends SupportedReliantWin {

    public WindowMgrWin() {
        super("WinMgr");
    }

    UiSystem uiS;

    ScrollPane p;
    VisTable rootT;

    float curt = 0;
    float maxT = 1f;

    @Override
    public void minimized() {

    }

    @Override
    public void _init(World w) {

        uiS = w.getSystem(UiSystem.class);

        rootT = new VisTable();
        p = new ScrollPane(rootT);

        rebuild();

        VisTable globR = new VisTable();
        globR.add(p).expand().fill();

        add(globR).expand().fill();
        globR.setWidth(400);

        setResizable(true);
        setResizeBorder(20);

    }

    private void rebuild() {

        rootT.clearChildren();

        for (final BaseWindow w : uiS.getWins()) {

            VisLabel l = new VisLabel(w.getName());
            rootT.add(l);

            final VisRadioButton b = new VisRadioButton("opened");
            b.setChecked(w.isOpen());
            b.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (b.isChecked()) uiS.open(w.code, null);
                    else uiS.close(w.code);
                }
            });

            rootT.add(b).padLeft(10);

            VisTextButton fg = new VisTextButton("FG");
            rootT.add(fg).padLeft(20);
            fg.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    uiS.getWin(w.code).toFront();
                }
            });

            if (w.code == code) b.setVisible(false);

            rootT.row();

        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        curt += delta;

        if (curt > maxT) {
            curt = 0;
            rebuild();
        }

    }

    @Override
    public void on_opened(String uir) {

    }

    @Override
    public void on_closed() {

    }
}
