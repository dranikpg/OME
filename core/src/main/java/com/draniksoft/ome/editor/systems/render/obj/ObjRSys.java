package com.draniksoft.ome.editor.systems.render.obj;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.state.InactiveC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.components.tps.LocationC;

public class ObjRSys extends IteratingSystem {

    static final String tag = "ObjRSys";


    public ObjRSys() {
        super(Aspect.all(PosSizeC.class, DrawableC.class, LocationC.class)
                .exclude(InactiveC.class, TInactiveC.class));
    }

    ComponentMapper<PosSizeC> posM;
    ComponentMapper<DrawableC> drwM;


    @Wire
    SpriteBatch b;

    @Wire(name = "game_cam")
    OrthographicCamera cam;



    @Override
    protected void begin() {

        b.setProjectionMatrix(cam.combined);

        b.begin();

    }

    PosSizeC ttc;
    DrawableC tdc;

    @Override
    protected void process(int e) {

        ttc = posM.get(e);
        tdc = drwM.get(e);

        if (tdc.d == null) return;

        if (cam.frustum.boundsInFrustum(ttc.x, ttc.y, 0, ttc.w, ttc.h, 0)) {


            tdc.d.draw(b, ttc.x, ttc.y, ttc.w, ttc.h);

        }

    }

    @Override
    protected void end() {

        b.end();

    }
}
