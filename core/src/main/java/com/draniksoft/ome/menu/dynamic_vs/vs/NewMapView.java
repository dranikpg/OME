package com.draniksoft.ome.menu.dynamic_vs.vs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.launch.LaunchBundle;
import com.draniksoft.ome.menu.ControlUtils;
import com.draniksoft.ome.menu.dynamic_vs.DynamicView;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;

public class NewMapView extends DynamicView {

    VisTextField mapNameF;
    VisTextField pathF;
    VisTextButton pathEO;

    VisCheckBox subfolderCB;

    VisTextButton createButton;


    public NewMapView(){

    }

    @Override
    public void init() {


       mapNameF = new VisTextField();
       mapNameF.setMessageText("MapName");

       pathF = new VisTextField();
       pathF.setMessageText("Path");

       pathEO = new VisTextButton("Show");

       subfolderCB = new VisCheckBox("Create sub folder");

       createButton = new VisTextButton("Create");
       createButton.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               ControlUtils.launchEditor(
                       new LaunchBundle()
               );
           }
       });

       addCps();

    }

    private void addCps() {


        VisTable t2 = new VisTable();
        t2.add(pathF).expandX().fillX();
        t2.add(pathEO);

        //

        add("Create new map").colspan(2).padTop(20);
        row().padTop(30);


        add("Name  ").left();
        add(mapNameF).expandX().fillX().padLeft(20).padRight(20);
        row().padTop(20);

        add("Folder  ").left();
        add(t2).expandX().fillX().padLeft(20).padRight(20);
        row().padTop(20);

        add(subfolderCB).colspan(2).expandX();
        row().padTop(20);

        addSeparator(false).colspan(2).padTop(20).padBottom(20);
        row().padTop(30);

        add(createButton).colspan(2);


    }

    @Override
    public void open(String args) {

    }

    @Override
    public void on_close() {

    }

    @Override
    public void on_refresh_req() {

    }
}
