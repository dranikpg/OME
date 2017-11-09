package com.draniksoft.ome.editor.ui.wins;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.core.PanelWin;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.ui.LinkButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.Iterator;


public class Inspector extends PanelWin {

    int _sel = -1;

    VisTable mainC;
    ScrollPane pane;

    LinkButton addCB;

    @Override
    protected void on_close() {

    }

    @Override
    protected void on_open(String args) {

    }

    @Override
    protected void on_init() {

        _w.getSystem(EventSystem.class).registerEvents(this);

        mainC = new VisTable();
        pane = new ScrollPane(mainC);
        pane.setScrollingDisabled(true, false);

        _add(pane).expand().fill();
        _row();

        addCB = new LinkButton(AppDO.I.L().get("add_comp")) {
            @Override
            public void on_press() {
                openCAdd();
            }
        };

        _add(addCB).expandX().center();
    }

    private void openCAdd() {
    }

    private void rebuild() {

        Iterator<CompositionObserver> i = _w.getSystem(EditorSystem.class).getComObsI();

        CompositionObserver o;
        while (i.hasNext()) {
            o = i.next();
            if (o.isSelActive()) {
                mainC.add(new VisLabel(o.getName())).expandX().center();
                mainC.row();
                mainC.add(o.getSettingsT()).expandX().fillX();
                mainC.row();
            }
        }

    }

    @Subscribe
    public void selchange(SelectionChangeE e) {
        _sel = e.n;
        updateSel();
    }

    @Subscribe
    public void compostChng(CompositionChangeE e) {
        if (_sel == e.e) {
            rebuild();
        }
    }


    private void updateSel() {
        rebuild();
    }

}
