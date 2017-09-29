package com.draniksoft.ome.editor.ui.wins;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.ui.ReliantBaseWin;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;

public class MOList extends ReliantBaseWin {

    VisTextButton closeB;

    VisList<String> l;

    public MOList() {
        super("Map locations");
    }

    @Override
    public void init(final World w) {

        closeB = new VisTextButton("X");
        closeB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                w.getSystem(UiSystem.class).close(code);
            }
        });
        getTitleTable().add(closeB).expand().right();

        l = new VisList<String>();
        IntBag b = w.getAspectSubscriptionManager().get(Aspect.all(MObjectC.class)).getEntities();

        String[] els = new String[b.size()];
        for (int i = 0; i < b.size(); i++) {

            els[i] = Integer.toString(b.get(i));
        }

        l.setItems(els);

        add(l).expand().height(400);

        row();

        add(new VisTextField()).expandX().fillX();

        setResizable(true);
        setMovable(true);
        setResizeBorder(10);
    }

    @Override
    public void on_closed() {

    }

    @Override
    public void on_opened(String uir) {

    }
}
