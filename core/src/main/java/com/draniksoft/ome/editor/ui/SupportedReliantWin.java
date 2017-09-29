package com.draniksoft.ome.editor.ui;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.kotcrab.vis.ui.widget.VisTextButton;

public abstract class SupportedReliantWin extends ReliantBaseWin {

    private float minExHeight = 200f;

    public SupportedReliantWin(String title) {
        super(title);
    }

    float prevH = 0;

    @Override
    public final void init(final World w) {

        VisTextButton closeB = new VisTextButton("X");
        final VisTextButton hideB = new VisTextButton("M", "toggle");

        closeB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                w.getSystem(UiSystem.class).close(code);
            }
        });

        final Vector2 tmpV = new Vector2();

        hideB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (hideB.isChecked()) {
                    minimized();
                    setResizable(false);
                    prevH = getHeight();
                    setY(localToStageCoordinates(tmpV.set(0, getTitleTable().getY())).y);
                    setHeight(getTitleTable().getHeight() + 1);

                } else {
                    setResizable(true);
                    setHeight(Math.max(minExHeight, prevH));
                    setY(localToStageCoordinates(tmpV.set(0, getTitleTable().getY())).y - (getHeight() - getTitleTable().getHeight()));
                }

            }
        });

        getTitleTable().add(hideB).expand().right();
        getTitleTable().add(closeB).padLeft(10).padRight(5);

        _init(w);

        prevH = getHeight();

    }

    public abstract void minimized();

    public abstract void _init(World w);


}
