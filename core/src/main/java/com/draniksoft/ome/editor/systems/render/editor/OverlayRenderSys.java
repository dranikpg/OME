package com.draniksoft.ome.editor.systems.render.editor;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.render.core.OverlayRenderer;
import com.draniksoft.ome.editor.support.render.ut.FixedLineOverlayR;

/*
	Note : Does not perform any rendering
 */
public class OverlayRenderSys extends BaseSystem {

    private final static String tag = "OverlayRenderSys";

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    @Wire(name = "batch")
    CompliantBatch<Quad2D> b;

    Array<OverlayRenderer> rs;

    public OverlayRenderSys() {

    }


    @Override
    protected void initialize() {

	  rs = new Array<OverlayRenderer>();
	  bK = new Array<OverlayRenderer>();

	  world.getSystem(OmeEventSystem.class).registerEvents(this);

	  addRdr(new FixedLineOverlayR());

    }

    @Override
    protected void processSystem() {

	  b.begin();

	  for (OverlayRenderer r : rs) {
		r.draw(b, cam);
	  }

	  b.end();

    }


    public Array<OverlayRenderer> getRs() {
	  return rs;
    }

    public void addRdr(OverlayRenderer r) {
	  Gdx.app.debug(tag, "Adding " + r.toString());
	  rs.add(r);
	  r.added(world);
    }

    public void removeRdr(OverlayRenderer r) {
	  Gdx.app.debug(tag, "Removing " + r.toString());
	  rs.removeValue(r, true);
    }


    Array<OverlayRenderer> bK;

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

	  Array<OverlayRenderer> torem = new Array<OverlayRenderer>();
	  bK.clear();
	  for (int i = 0; i < rmIds.size; i++) {
		Gdx.app.debug(tag, "Removing OverlayR :: " + rs.get(rmIds.get(i)).getClass().getSimpleName());
		torem.add(rs.get(rmIds.get(i)));
		bK.add(rs.get(rmIds.get(i)));
	  }

	  rs.removeAll(torem, true);

    }

    public void restoreBK() {
	  for (OverlayRenderer i : bK) {
		addRdr(i);
	  }
	  bK.clear();
    }


}
