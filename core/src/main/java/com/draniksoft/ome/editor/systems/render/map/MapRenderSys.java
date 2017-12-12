package com.draniksoft.ome.editor.systems.render.map;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector3;
import com.draniksoft.ome.editor.components.gfx.TexRegC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MapC;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import net.mostlyoriginal.api.event.common.Subscribe;

public class MapRenderSys extends BaseEntitySystem {

    public static final String tag = "MapRenderSystem";

    public MapRenderSys() {
        super(Aspect.all(MapC.class, TexRegC.class, PosSizeC.class));
    }


    ComponentMapper<TexRegC> dm;
    ComponentMapper<PosSizeC> ps;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    @Wire
    SpriteBatch batch;

    @Wire
    SpriteCache cache;



    Vector3 lma;
    float lz;
    int cid = -1;

    boolean needrd = false;

    @Override
    protected void initialize() {
        lma = new Vector3();
    }

    @Override
    protected void processSystem() {

        if (!lma.equals(cam.position) || cid == -1 || lz != cam.zoom) {
            needrd = true;

          lma.set(cam.position);
            lz = cam.zoom;

      }

	  if (needrd) redraw();
	  cache.setProjectionMatrix(cam.combined);
	  cache.begin();

      cache.draw(cid);

      cache.end();

    }

    PosSizeC tPSC;
    public void redraw(){

        needrd = false;
        cache.clear();
        cache.beginCache();


        int e;
        for(int i = 0; i < getEntityIds().size(); i++){

            e = getEntityIds().get(i);
		tPSC = ps.get(e);

            if (cam.frustum.boundsInFrustum(tPSC.x, tPSC.y, 0, tPSC.w, tPSC.h, 0)) {

                cache.add(dm.get(e).d,tPSC.x,tPSC.y, tPSC.w,tPSC.h);

            }



        }

        cid = cache.endCache();

    }


    @Subscribe
    public void resized(ResizeEvent ev) {
        needrd = true;
    }

}
