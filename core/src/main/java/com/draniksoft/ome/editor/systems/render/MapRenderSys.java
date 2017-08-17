package com.draniksoft.ome.editor.systems.render;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector3;
import com.draniksoft.ome.editor.components.MapC;
import com.draniksoft.ome.editor.components.PosSizeC;
import com.draniksoft.ome.editor.components.TexRegC;

public class MapRenderSys extends BaseEntitySystem {

    public static final String tag = "MapRenderSystem";

    public MapRenderSys() {
        super(Aspect.all(MapC.class, TexRegC.class, PosSizeC.class));
    }


    ComponentMapper<TexRegC> dm;
    ComponentMapper<PosSizeC> psm;


    @Wire
    OrthographicCamera cam;

    @Wire
    SpriteBatch batch;

    @Wire
    SpriteCache cache;



    Vector3 lma;
    int cid = -1;

    @Override
    protected void initialize() {
        lma = new Vector3();
    }

    @Override
    protected void processSystem() {

      if(!lma.equals(cam.position) || cid == -1){
          Gdx.app.debug(tag,"Redrawing");

          redraw();

          lma.set(cam.position);

      }

      cache.setProjectionMatrix(cam.combined);
      cache.begin();

      cache.draw(cid);

      cache.end();

    }


    PosSizeC tPSC;
    public void redraw(){


        cache.clear();
        cache.beginCache();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        int e;
        for(int i = 0; i < getEntityIds().size(); i++){

            e = getEntityIds().get(i);

            tPSC = psm.get(e);
            if(cam.frustum.boundsInFrustum(tPSC.x,tPSC.y,0,tPSC.w/2f, tPSC.h/2f,0)){

                cache.add(dm.get(e).d,tPSC.x,tPSC.y, tPSC.w,tPSC.h);

            }



        }

        cid = cache.endCache();

        batch.end();


    }


}