package com.draniksoft.ome.editor.ui.comp_obs_ts;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class TimedMovsTable extends VisTable {

    World _w;
    int _e;

    VisTextButton edit;
    VisLabel l;

    public void init(World w, int _e) {
        this._w = w;
        this._e = _e;

        TimedMoveC c = w.getMapper(TimedMoveC.class).get(_e);

        edit = new VisTextButton("Edit");
        edit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                _w.getSystem(EditorSystem.class).attachNewEditMode(EditModeDesc.IDS.editTimedMoves);
            }
        });

        l = new VisLabel("Currently " + c.a.size + " paths");
        add(l);
        add(edit).padLeft(20);


        reload();

        _w.getSystem(EventSystem.class).registerEvents(this);
    }


    @Subscribe
    public void dataChanhged(EntityDataChangeE.TimedMoveValsChange e) {

        if (e._e == this._e)
            reload();

    }

    private void reload() {

        l.setText("Currently " + _w.getMapper(TimedMoveC.class).get(_e).a.size + " paths");

    }


}
