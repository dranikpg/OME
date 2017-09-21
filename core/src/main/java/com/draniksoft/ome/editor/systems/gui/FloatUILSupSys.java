package com.draniksoft.ome.editor.systems.gui;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.selection.FLabelC;
import com.draniksoft.ome.editor.components.selection.SelectionC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.support.event.SelectionChangeE;
import com.kotcrab.vis.ui.widget.VisLabel;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.LinkedList;

public class FloatUILSupSys extends IteratingSystem {

    static final String tag = "FLoatUILSupSys";

    LinkedList<Actor> actrs;

    public FloatUILSupSys() {
        super(Aspect.all(FLabelC.class, PosSizeC.class).exclude(TInactiveC.class));
    }
    @Override
    protected void initialize() {
        actrs = new LinkedList<Actor>();
        tv = new Vector2();

        getSubscription().addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
            @Override
            public void inserted(IntBag es) {

                Gdx.app.debug(tag, "Entities added ");

                for (int e = 0; e < es.size(); e++) {

                    tc = flM.get(es.get(e));

                    if (tc.lid == -1) {
                        tc.lid = newLabel(tc.txt);
                    }

                }

            }

            @Override
            public void removed(IntBag es) {

                Gdx.app.debug(tag, "Entities removed");

                for (int i = 0; i < es.size(); i++) {

                    actrs.get(flM.get(es.get(i)).lid).addAction(Actions.sequence(

                            Actions.alpha(0, 1f),
                            new com.badlogic.gdx.scenes.scene2d.Action() {
                                @Override
                                public boolean act(float delta) {
                                    tc.lid = -1;
                                    return true;
                                }
                            },
                            Actions.removeActor()

                    ));

                }

            }

        });

    }


    ComponentMapper<FLabelC> flM;
    ComponentMapper<PosSizeC> pM;
    ComponentMapper<SelectionC> sM;

    @Wire(name = "game_stage")
    Stage s;

    @Wire(name = "game_vp")
    Viewport gameVP;

    @Wire(name = "ui_vp")
    Viewport uiVP;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    FLabelC tc;
    PosSizeC pc;

    Vector2 tv;
    Actor ta;

    @Override
    protected void process(int e) {

        tc = flM.get(e);

        if (tc.lid == -1) return;

        pc = pM.get(e);

        if (cam.frustum.boundsInFrustum(pc.x, pc.y, 0, pc.w, pc.h, 0)) {

            tv.set(pc.x, pc.y);

            tv = gameVP.project(tv);
            tv = uiVP.unproject(tv);

            ta = actrs.get(tc.lid);
            ta.setVisible(true);
            ta.setScale(1 / cam.zoom);
            ta.setPosition(tv.x, uiVP.getWorldHeight() - tv.y - ta.getHeight());

        } else {
            ta = actrs.get(tc.lid);
            ta.setVisible(false);
        }

    }

    private int newLabel(String txt) {

        VisLabel l = new VisLabel();
        l.setText(txt);

        l.setColor(0, 0, 0, 1);

        s.addActor(l);

        actrs.add(l);

        Gdx.app.debug(tag, "Added new label :: id ::  " + (actrs.size() - 1));

        return (actrs.size() - 1);
    }

    @Subscribe
    public void selectionChange(SelectionChangeE e) {

        if (e.old != -1 && flM.has(e.old)) {
            actrs.get(flM.get(e.old).lid).setColor(0, 0, 0, 1);
        }

        if (e.n != -1 && flM.has(e.n)) {

            if (flM.get(e.n).lid == -1) {
                flM.get(e.n).lid = newLabel(tc.txt);
            }

            actrs.get(flM.get(e.n).lid).setColor(1, 0, 0, 1);
        }

    }

}
