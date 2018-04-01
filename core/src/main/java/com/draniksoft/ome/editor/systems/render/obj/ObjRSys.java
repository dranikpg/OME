package com.draniksoft.ome.editor.systems.render.obj;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cyphercove.gdx.flexbatch.CompliantBatch;
import com.cyphercove.gdx.flexbatch.batchable.Quad2D;
import com.draniksoft.ome.editor.base_gfx.batchables.STB;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.components.pos.PosC;
import com.draniksoft.ome.utils.GU;

public class ObjRSys extends IteratingSystem {

    static final String tag = "ObjRSys";


    public ObjRSys() {
        super(Aspect.all(PosC.class, DrawableC.class));
    }

    ComponentMapper<PosBoundsC> posM;
    ComponentMapper<DrawableC> drwM;


    @Wire(name = "batch")
    CompliantBatch<Quad2D> b;

    @Wire(name = "game_cam")
    OrthographicCamera cam;



    @Override
    protected void begin() {
        b.setProjectionMatrix(cam.combined);
        b.begin();

        b.draw(STB.circle(200, 200, (int) (40 * GU.CAM_SCALE(0.4f, 1f)), Color.CHARTREUSE));

    }


    @Override
    protected void process(int e) {

    }

    @Override
    protected void end() {

        b.end();

    }
}
