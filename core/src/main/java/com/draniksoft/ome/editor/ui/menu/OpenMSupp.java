package com.draniksoft.ome.editor.ui.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;
import com.draniksoft.ome.editor.ui.core.menu.MenuContentSupplierI;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.SUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class OpenMSupp extends MenuContentSupplierI {

    String s = "editor_main_menu";
    String ss = "editor_main_menu_sm";


    Table ct;

    @Override
    public void _init() {

        ct = new Table();


        VisTextButton backB = new VisTextButton(SUtils.getS("back"), s);
        backB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                m.inflateSup(1);
            }
        });
        ct.add(backB).padBottom(m.getDefPadding() * 4f).expandX().left();
        ct.row();


        VisTextButton newB = new VisTextButton(SUtils.getS("create_new"), s);
        VisTextButton selectF = new VisTextButton(SUtils.getS("select_file"), s);

        ct.add(newB).padBottom(m.getDefPadding()).expandX().left();
        ct.row();
        ct.add(selectF).padBottom(m.getDefPadding() * 4f).expandX().left();
        ct.row();


        Array<String> last = AppDO.I.LH().getLast();
        VisLabel msg;

        if (last.size > 0) {

            msg = new VisLabel(SUtils.getS("open_recent"), s);

            ct.add(msg).padBottom(m.getDefPadding() * .5f).expandX().left();
            ct.row();

            for (final String lst : last) {
                VisTextButton openB = new VisTextButton(lst, ss);

                openB.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

                        w.getSystem(ProjecetLoadSys.class).setBundle(new MapLoadBundle(lst));
                        w.getSystem(ProjecetLoadSys.class).load();

                        m.close();

                    }
                });

                ct.add(openB).padBottom(m.getDefPadding() * 5f);

            }

        }


    }


    @Override
    public void build(Table t) {

        t.setWidth(ct.getPrefWidth());

        t.add(ct).expand().fill();

    }
}
