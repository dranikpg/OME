package com.draniksoft.ome.editor.ui.wins.inspector;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.SupportedReliantWin;
import com.draniksoft.ome.editor.ui.wins.em.AddCompWin;
import com.draniksoft.ome.utils.ESCUtils;
import com.kotcrab.vis.ui.widget.*;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.Iterator;

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

        Iterator<CompositionObserver> i = sys.getComObsI();
        while (i.hasNext()) {
            addToRoot(i.next());
        }

        if (rootPT.getChildren().size == 0) {

            rootPT.add("NO ITEMS");

        }


    }

    private void addToRoot(CompositionObserver o) {

        Table t;
        if (o.isSelActive() && (t = o.getSettingsT()) != null) {

            if (o.isAviab(ActionDesc.BaseCodes.ACTION_DELETE)) {

                VisTable header = new VisTable();

                VisLabel nameL = new VisLabel(o.getName());

                final CollapsibleWidget w = new CollapsibleWidget(t);

                VisTextButton delB = new VisTextButton("X");
                delB.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

                    }
                });

                nameL.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        w.setCollapsed(!w.isCollapsed(), true);
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });


                header.add(nameL).expandX().padLeft(50);
                header.add(delB);


                rootPT.add(header).expandX().fillX().padBottom(10);
                rootPT.row();


                rootPT.add(w).expandX().fillX().padBottom(20);
                rootPT.row();


            } else {

                rootPT.add(t).expandX().fillX().padBottom(20);
                rootPT.row();


            }

        }

    }

    @Override
    protected void maximized() {

    }

    @Override
    public void minimized() {

    }

    @Override
    public void on_opened(String uri) {
        Gdx.app.debug(tag, "opened " + uri);
        rootPT.setVisible(true);
        rebuild();
    }

    @Override
    public void on_closed() {
        getStage().setScrollFocus(null);
    }

    @Subscribe(priority = ESCUtils.EVENT_MID_PRIORITY)
    public void compChg(CompositionChangeE e) {

        rebuild();

    }

    @Subscribe(priority = ESCUtils.EVENT_MID_PRIORITY)
    public void selChanged(SelectionChangeE e) {

        if (e.n >= 0) {
            if (!isOpen()) _w.getSystem(UiSystem.class).open(code, "");
            rebuild();
        } else {
            rootPT.setVisible(false);
            _w.getSystem(UiSystem.class).close(this.code);
        }


    }
}
