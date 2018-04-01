package com.draniksoft.ome.editor.systems.render.map;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.components.tps.MapC;
import com.draniksoft.ome.utils.Env;

public class MapRDebugSys extends IteratingSystem {

    public MapRDebugSys() {
	  super(Aspect.all(MapC.class, PosBoundsC.class));
    }

    @Wire(name = "game_cam")
    OrthographicCamera gameCam;

    @Wire
    ShapeRenderer rdr;


    ComponentMapper<PosBoundsC> psm;

    Color hc = new Color(.8f,.4f,.8f,1);
    Color rdrC = new Color(0.5f,1f,0.5f,1);
    Color frustumC = new Color(1, 0.5f, 0.5f, 1);

    boolean hiddedRender = false;


    @Override
    protected void initialize() {
        setEnabled(Env.GFX_DEBUG);
    }

    @Override
    protected void begin() {

        rdr.setProjectionMatrix(gameCam.combined);

        rdr.begin(ShapeRenderer.ShapeType.Line);

    }

    PosBoundsC tPSC;
    @Override
    protected void process(int e) {




    }

    @Override
    protected void end() {
        rdr.end();

    }


}

