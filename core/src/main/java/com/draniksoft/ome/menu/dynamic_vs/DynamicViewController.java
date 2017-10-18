package com.draniksoft.ome.menu.dynamic_vs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.menu.MenuScreen;
import com.draniksoft.ome.menu.dynamic_vs.vs.EditorView;
import com.draniksoft.ome.menu.dynamic_vs.vs.NewMapView;

import static com.draniksoft.ome.menu.dynamic_vs.DynamicViewController.Codes.EDITOR;
import static com.draniksoft.ome.menu.dynamic_vs.DynamicViewController.Codes.EDITOR_NEW_MAP;

public class DynamicViewController {

    public static final String tag = "DynamicViewController";

    MenuScreen ms;

    // dimensions bottom left end_x,end_y + width, height
    int d[] = {0,0,0,0};
    // A group solves the draw order problem
    Group drawGroup;


    public static class Codes{

        public static final short EDITOR = 10;

        public static final short EDITOR_NEW_MAP = 11;
    }

    DynamicView newV;
    short nid;
    DynamicView view;
    short cid;


    // Hold all views
    IntMap<DynamicView> views;

    Action fadeInA;
    Action fadeOutA;

    public DynamicViewController(MenuScreen ms,int[] d, Group drawGroup){
        this.ms = ms;
        this.d = d;
        this.drawGroup = drawGroup;

        views = new IntMap<DynamicView>();

    }

    public void buildActions(){

        float t = 0.5f;

        fadeInA = Actions.sequence(
                Actions.moveTo(d[0] + d[2] + 20,d[1]),
                Actions.moveTo(d[0], d[1], t, Interpolation.pow2),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        if(view != null)
                            view.setVisible(false);
                        view = newV;
                        cid = nid;

                        newV = null;
                        nid = -1;

                        return true;
                    }
                });

        fadeOutA = Actions.moveTo(d[0] - d[2],d[1],t,Interpolation.pow2);

    }

    public void open(short id){
        open(id,null);
    }

    private void open(short id, String arg) {

        if(id == cid){
            if(arg != null){
                Gdx.app.debug(tag,"Passing args ["  + arg + "] to " + cid);
                view.open(arg);
            }else{
                return;
            }
        }

        if(newV != null){
            return;
        }

        if(!views.containsKey(id)){
            if(!createView(id)){
                return;
            }
        }

        Gdx.app.debug(tag,"Showing view with id:" + id );

        buildActions();

        newV = views.get(id);
        newV.setVisible(true);
        nid = id;

        newV.open(arg);
        newV.addAction(fadeInA);

        if(view != null){
            view.on_close();
            view.addAction(fadeOutA);
        }



    }

    private boolean createView(short id) {

        Gdx.app.debug(tag,"Creating view with id : " + id);

        DynamicView cv = null;

        switch (id){

            case EDITOR:

                cv = new EditorView(this);

                break;

            case EDITOR_NEW_MAP:

                cv = new NewMapView();

                break;

        }

        if(cv == null){
            return false;
        }

        cv.setPosition(d[0]+d[2],d[1]);
        cv.setSize(d[2],d[3]);
        cv.init();


        views.put(id,cv);
        drawGroup.addActor(cv);

        return true;

    }


    public MenuScreen getMs() {
        return ms;
    }
}
