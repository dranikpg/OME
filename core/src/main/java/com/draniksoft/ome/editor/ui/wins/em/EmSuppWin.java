package com.draniksoft.ome.editor.ui.wins.em;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.ui.ReliantBaseWin;
import com.kotcrab.vis.ui.widget.VisTable;

public class EmSuppWin extends ReliantBaseWin {

    VisTable root;

    World _w;

    public EmSuppWin() {
        super("Edit");
    }

    @Override
    public void init(World w) {
        this._w = w;
        root = new VisTable();

        add(root).fill().expand();

        setResizable(true);
        setResizeBorder(20);

        setSize(400, 300);

    }

    public void pushT(Table t) {
        clear();

        root.add(t).expand().fill();

    }

    public void clear() {
        root.clearChildren();
    }


    @Override
    public void on_opened(String uir) {

    }


    @Override
    public void on_closed() {
        _w.getSystem(EditorSystem.class).attachNewEditMode(null);
    }

}
