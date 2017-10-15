package com.draniksoft.ome.editor.ui.wins.actions;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.ActionHistoryChangeE;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.ui.SupportedReliantWin;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.widget.*;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class ActionList extends SupportedReliantWin {

    World w;

    SimpleListAdapter<Action> adapter;
    ListView<Action> lw;

    ActionSystem sys;

    VisTextButton undoB;
    VisTable bottomT;

    public ActionList() {
        super("Action list");
    }

    @Override
    protected void maximized() {

    }

    @Override
    public void minimized() {

    }

    @Override
    public void _init(World w) {
        this.w = w;
        w.getSystem(EventSystem.class).registerEvents(this);

        sys = w.getSystem(ActionSystem.class);

        adapter = new SimpleListAdapter<Action>(new Array<Action>()) {
            @Override
            protected VisTable createView(Action item) {

                VisTable t = new VisTable();
                VisLabel l = new VisLabel(item.getSimpleConcl());
                l.setEllipsis(true);
                t.add(l).expand().fill();


                return t;

            }
        };

        lw = new ListView<Action>(adapter);


        bottomT = new VisTable();
        undoB = new VisTextButton("undo");
        undoB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sys.undo();
            }
        });

        bottomT.add(undoB);


        add(new VisScrollPane(lw.getMainTable())).expand().fill();
        row();
        add(new Separator()).expandX().fillX().padTop(5);
        row();
        add(bottomT).padTop(5).padBottom(10).expandX().fillX();


        setResizable(true);
        setResizeBorder(15);


        setSize(200, 300);

        update();

    }

    public void update() {

        Array<Action> items = new Array<Action>((sys.getStack().toArray(new Action[]{})));

        adapter.clear();
        adapter.addAll(items);

        adapter.itemsChanged();
    }

    @Override
    public void on_opened(String uir) {

    }

    @Override
    public void on_closed() {

    }

    @Subscribe
    public void actionHChanged(ActionHistoryChangeE e) {
        update();
    }
}
