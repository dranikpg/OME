package com.draniksoft.ome.editor.ui.wins.inspector;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.workflow.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.SupportedReliantWin;
import com.draniksoft.ome.editor.ui.wins.em.AddCompWin;
import com.draniksoft.ome.utils.ESCUtils;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class InspectorMainWin extends SupportedReliantWin {

    private static final String tag = "InspectorMainWin";

    private World _w;
    EditorSystem sys;


    VisScrollPane p;
    VisTable rootPT;

    VisTable bottomT;

    public InspectorMainWin() {
        super("Inspector");
    }

    @Override
    public void _init(World w) {

        this._w = w;

        sys = w.getSystem(EditorSystem.class);
        w.getSystem(EventSystem.class).registerEvents(this);

        rootPT = new VisTable();
        p = new VisScrollPane(rootPT);
        p.setScrollingDisabled(true, false);

        bottomT = new VisTable();
        buildBottomT();

        add(p).expand().fill();
        row();
        add(new Separator()).expandX().fillX();
        row();
        add(bottomT).expandX().fillX().padBottom(10).padTop(10);

        rebuild();

        setSize(500, 700);
        setResizable(true);
        setResizeBorder(20);
    }

    private void buildBottomT() {

        VisTextButton addC = new VisTextButton("Add C");
        addC.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                _w.getSystem(UiSystem.class).open(UiSystem.WinCodes.addCWin, null);

                AddCompWin w = (AddCompWin) _w.getSystem(UiSystem.class).getWin(UiSystem.WinCodes.addCWin);
                w.reinit(_w.getSystem(EditorSystem.class).sel);


            }
        });

        bottomT.add(addC);

    }


    private void rebuild() {

        rootPT.clearChildren();

        Table t;
        for (CompositionObserver o : sys.getComObs()) {
            if (o.isSelActive() && (t = o.getSettingsT()) != null) {

                rootPT.add(t).expandX().fillX();
                rootPT.row();


            }
        }

        if (rootPT.getChildren().size == 0) {

            rootPT.add("NO ITEMS");

        }


    }

    @Override
    protected void maximized() {

    }

    @Override
    public void minimized() {

    }

    @Override
    public void on_opened(String uir) {

    }

    @Override
    public void on_closed() {

    }

    @Subscribe(priority = ESCUtils.EVENT_HIGH_PRIORITY)
    public void compChg(CompositionChangeE e) {

        rebuild();

    }

    @Subscribe(priority = ESCUtils.EVENT_MID_PRIORITY)
    public void selChanged(SelectionChangeE e) {

        rebuild();

        if (e.n >= 0) {
            if (isMinimized()) {
                expand();
            }
        } else {

            minimize();

        }


    }
}
