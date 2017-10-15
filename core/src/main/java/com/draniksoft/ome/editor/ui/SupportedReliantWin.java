package com.draniksoft.ome.editor.ui;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.kotcrab.vis.ui.widget.VisTextButton;

public abstract class SupportedReliantWin extends ReliantBaseWin {

    private float minExHeight = 200f;
    private boolean minimized = false;

    boolean collapseToBottom = false;

    public SupportedReliantWin(String title) {
        super(title);
    }

    float prevH = 0;

    final Vector2 tmpV = new Vector2();

    private VisTextButton hideB;

    @Override
    public final void init(final World w) {

        VisTextButton closeB = new VisTextButton("X");
        hideB = new VisTextButton("M", "toggle");
        hideB.setProgrammaticChangeEvents(false);

        closeB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (closeRequest())
                    w.getSystem(UiSystem.class).close(code);
            }
        });



        hideB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (hideB.isChecked()) {
                    minimize();
                } else {
                    expand();
                }

            }
        });

        getTitleTable().add(hideB).expand().right();
        getTitleTable().add(closeB).padLeft(10).padRight(5);

        _init(w);

        prevH = getHeight();

    }

    protected boolean closeRequest() {
        return true;
    }


    public final void minimize() {
        minimized();

        getStage().setScrollFocus(null);
        getStage().setKeyboardFocus(null);

        hideB.setChecked(true);

        setResizable(false);
        minimized = true;
        prevH = getHeight();

        setY(localToStageCoordinates(tmpV.set(0, getTitleTable().getY())).y);
        setHeight(getTitleTable().getHeight());
    }

    public final void expand() {
        maximized();

        hideB.setChecked(false);

        setResizable(true);
        minimized = false;

        setHeight(Math.max(minExHeight, prevH));
        setY(localToStageCoordinates(tmpV.set(0, getTitleTable().getY())).y - (getHeight() - getTitleTable().getHeight()));

    }

    protected abstract void maximized();

    public boolean isMinimized() {
        return minimized;
    }

    public abstract void minimized();

    public abstract void _init(World w);


}
