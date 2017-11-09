package com.draniksoft.ome.editor.systems.render.editor;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.draniksoft.ome.editor.components.supp.SelectionC;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.editor.support.render.def.PathRenderer;
import com.draniksoft.ome.editor.support.render.def.SelectionRenderer;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;


public class OverlayRenderSys extends BaseEntitySystem {

    private final static String tag = "OverlayRenderSys";

    @Wire
    SpriteBatch b;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    Array<OverlyRendererI> rs;

    public OverlayRenderSys() {
        super(Aspect.all(SelectionC.class));
    }


    @Override
    protected void initialize() {


        rs = new Array<OverlyRendererI>();

        restoreDef();

        world.getSystem(EventSystem.class).registerEvents(this);

    }

    int _e;

    @Override
    protected void processSystem() {


        if (getEntityIds().size() < 1)
            _e = -1;
        else
            _e = getEntityIds().get(0);

        b.setProjectionMatrix(cam.combined);
        b.begin();

        for (int i = 0; i < rs.size; i++) {

            rs.get(i).render(_e, b, cam);

        }

        b.end();

    }

    public void restoreDef() {

        addRdr(new SelectionRenderer());
        addRdr(new PathRenderer());


    }


    public Array<OverlyRendererI> getRs() {
        return rs;
    }

    public void addRdr(OverlyRendererI r) {
        rs.add(r);
        r.added(world);
    }

    public void removeRdr(OverlyRendererI r) {
        rs.removeValue(r, true);
    }

    public void removeRdrByID(int id) {

        int ri = -1;

        for (int i = 0; i < rs.size; i++) {

            OverlyRendererI r = rs.get(i);

            if (r.getId() == id) {


                ri = i;
                break;
            }
        }

        if (ri >= 0)
            removeRdr(rs.get(ri));
    }

    public void removeRdrByPlace(int[] all, int[] one) {

        IntArray rmIds = new IntArray();

        for (int i = 0; i < getRs().size; i++) {

            int[] ps = rs.get(i).getPos();

            int allsF = 0;
            boolean globB = false;
            for (int p_num : ps) {

                for (int op_num : one) {

                    if (p_num == op_num) {

                        rmIds.add(i);
                        globB = true;
                        break;

                    }

                }

                if (globB) break;

                for (int ap_num : all) {

                    if (ap_num == p_num) {

                        allsF++;

                    }

                }


            }

            if (!globB)
                if (allsF == all.length)
                    rmIds.add(i);


        }

        Array<OverlyRendererI> torem = new Array<OverlyRendererI>();
        for (int i = 0; i < rmIds.size; i++) {
            Gdx.app.debug(tag, "Removing OverlayR :: " + rs.get(rmIds.get(i)).getClass().getSimpleName());
            torem.add(rs.get(rmIds.get(i)));
        }

        rs.removeAll(torem, true);


    }

    @Subscribe
    public void modeChanged(ModeChangeE e) {


        if (e.SHOW_MODE) {

            removeRdrByID(OverlyRendererI.IDs.SelR);
            removeRdrByID(OverlyRendererI.IDs.PathR);

        } else {
            restoreDef();
        }


    }


}
