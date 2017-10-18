package com.draniksoft.ome.editor.ui;

import com.artemis.World;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public abstract class ReliantBaseWin extends BaseWindow {

    private boolean open = false;
    protected float closeDur = 0.3f;
    protected float openDur = 0.3f;
    protected Interpolation openInt = Interpolation.pow3;
    protected Interpolation closeInt = Interpolation.pow3;

    public ReliantBaseWin(String title) {
        super(title);
        setName(title);
    }

    public ReliantBaseWin(String title, String style) {
        super(title, style);
        setName(title);
    }

    @Override
    public abstract void init(World w);

    public boolean toFrontOnOpen = true;

    @Override
    public final void open(String uri) {

        on_opened(uri);

        if (toFrontOnOpen) toFront();

        if (isOpen()) return;

        open = true;
        addAction(
                Actions.sequence(
                        Actions.visible(true),
                        Actions.alpha(1, openDur, openInt)
                ));
    }

    public abstract void on_opened(String uir);


    @Override
    public final void close() {
        open = false;
        on_closed();
        addAction(
                Actions.sequence(
                        Actions.alpha(0, closeDur, closeInt),
                        Actions.visible(false))
        );
    }

    public abstract void on_closed();

    @Override
    public final boolean isOpen() {
        return open;
    }
}
