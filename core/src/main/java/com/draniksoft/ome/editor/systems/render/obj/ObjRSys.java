package com.draniksoft.ome.editor.systems.render.obj;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PosC;

public class ObjRSys extends IteratingSystem {

    static final String tag = "ObjRSys";

    public ObjRSys() {
        super(Aspect.all(PosC.class, DrawableC.class));
    }

    ComponentMapper<PosC> posM;
    ComponentMapper<DrawableC> dwbM;


    @Wire(name = "batch")
    CompliantBatch<Quad2D> b;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    @Override
    protected void begin() {
        b.setProjectionMatrix(cam.combined);
        b.begin();

    }

    PosC posC;
    DrawableC dwbC;

    @Override
    protected void process(int e) {
	  posC = posM.get(e);
	  dwbC = dwbM.get(e);

	  if (cam.frustum.boundsInFrustum(posC.x - 100, posC.y - 100, 0, 100, 100, 0))
		dwbC.d.draw(b, posC.x, posC.y);
    }

    @Override
    protected void end() {
        b.end();
    }
}
