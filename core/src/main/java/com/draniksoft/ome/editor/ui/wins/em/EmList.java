package com.draniksoft.ome.editor.ui.wins.em;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.event.EditModeChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.SupportedReliantWin;
import com.draniksoft.ome.utils.ESCUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class EmList extends SupportedReliantWin {

    private static final String tag = "EmList";

    World w;
    Array<EditModeDesc> ds;

    EditorSystem sys;
    int sel;

    VisTable r;

    public EmList() {
        super("Edit mode list");

        setName("Edit mode list");
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


        sys = w.getSystem(EditorSystem.class);
        sel = ESCUtils.getFirstSel(w);

        r = new VisTable();
        add(r).expand().fill();

        w.getSystem(EventSystem.class).registerEvents(this);

        rebuild();


        setResizable(true);
        setResizeBorder(15);

        setModal(true);

    }

    private void rebuild() {


        Gdx.app.debug(tag, "Rebuilding");

        r.clearChildren();

        ds = sys.getEmDesc();


        for (EditModeDesc d : ds) {

            if (d.selRequired) {

                if (sel >= 0) visualizeEM(d);

            } else visualizeEM(d);


        }

    }

    private void visualizeEM(final EditModeDesc d) {

        r.add(new VisLabel(d.getName()));

        VisTextButton b;

        if (sys.getCurModeDesc() == d) {

            b = new VisTextButton("X");
            b.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    sys.detachEditMode();
                }
            });


        } else {

            b = new VisTextButton("Set");
            b.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    sys.attachNewEditMode(d);
                }
            });

        }

        r.add(b).padLeft(20);

        r.row();

    }

    @Override
    public void on_opened(String uri) {

        if (uri != null && uri.contains("#init")) {

            w.getSystem(UiSystem.class).close(this.code);

        }

    }

    @Override
    public void on_closed() {

    }

    @Subscribe
    public void selectionChanged(SelectionChangeE e) {
        sel = e.n;
        rebuild();
    }

    @Subscribe
    public void emChanged(EditModeChangeE e) {

        rebuild();
    }
}
