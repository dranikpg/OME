package com.draniksoft.ome.editor.support.render.def;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class SelectionRenderer implements OverlyRendererI {

    public int e;
    PosSizeC _c;
    ComponentMapper<PosSizeC> cM;

    PositionSystem pS;
    Pair<Integer, Integer> center;

    @Override
    public void render(SpriteBatch b, OrthographicCamera c) {

    }

    @Override
    public void render(ShapeRenderer r, OrthographicCamera c) {

	  _c = cM.get(e);

	  r.setColor(GUtils.Colors.PRIMARY);

	  // TODO better renderer

	  center = pS.getCenterP(e);
	  r.circle(center.getElement0(), center.getElement1(), Math.max(_c.w, _c.h) / 2, 20);

    }

    @Override
    public void added(World _w) {
	  pS = _w.getSystem(PositionSystem.class);
	  cM = _w.getMapper(PosSizeC.class);
    }

    @Override
    public int[] getPos() {
	  return new int[0];
    }

    @Override
    public int getId() {
	  return 0;
    }
}
