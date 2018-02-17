package com.draniksoft.ome.editor.systems.render.editor;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.support.render.core.OverlayRendererI;
import net.mostlyoriginal.api.event.common.Subscribe;


public class OverlayRenderSys extends BaseSystem {

    private final static String tag = "OverlayRenderSys";

    @Wire
    ShapeRenderer r;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    Array<OverlayRendererI> rs;

    public OverlayRenderSys() {

    }


    @Override
    protected void initialize() {

	  rs = new Array<OverlayRendererI>();
	  bK = new Array<OverlayRendererI>();

	  world.getSystem(OmeEventSystem.class).registerEvents(this);

    }

    @Override
    protected void processSystem() {


        /*b.setProjectionMatrix(cam.combined);
	  b.begin();
        for (int i = 0; i < rs.size; i++) {
            rs.getColor(i).render(b, cam);
        }
        b.end();
       */


        r.setProjectionMatrix(cam.combined);
        r.setAutoShapeType(true);
        r.begin();
        for (int i = 0; i < rs.size; i++) {
            rs.get(i).render(r, cam);

        }
        r.end();

    }


    public Array<OverlayRendererI> getRs() {
	  return rs;
    }

    public void addRdr(OverlayRendererI r) {
	  Gdx.app.debug(tag, "Adding " + r.toString());
	  rs.add(r);
	  r.added(world);
    }

    public void removeRdr(OverlayRendererI r) {
	  Gdx.app.debug(tag, "Removing " + r.toString());
	  rs.removeValue(r, true);
    }

    public void removeRdr(int id) {

        int ri = -1;

        for (int i = 0; i < rs.size; i++) {

		OverlayRendererI r = rs.get(i);

            if (r.getId() == id) {


                ri = i;
                break;
            }
        }

        if (ri >= 0)
            removeRdr(rs.get(ri));
    }

    Array<OverlayRendererI> bK;

    public void removeRdrByPlaceBK(int[] all, int[] one) {
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

	  Array<OverlayRendererI> torem = new Array<OverlayRendererI>();
	  bK.clear();
	  for (int i = 0; i < rmIds.size; i++) {
		Gdx.app.debug(tag, "Removing OverlayR :: " + rs.get(rmIds.get(i)).getClass().getSimpleName());
		torem.add(rs.get(rmIds.get(i)));
		bK.add(rs.get(rmIds.get(i)));
	  }

	  rs.removeAll(torem, true);

    }

    public void restoreBK() {
	  for (OverlayRendererI i : bK) {
		addRdr(i);
	  }
	  bK.clear();
    }

    @Subscribe
    public void modeChanged(ModeChangeE e) {



    }


}
