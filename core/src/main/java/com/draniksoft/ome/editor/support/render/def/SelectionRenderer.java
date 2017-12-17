package com.draniksoft.ome.editor.support.render.def;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.utils.struct.Pair;
import com.kotcrab.vis.ui.VisUI;

public class SelectionRenderer implements OverlyRendererI {

    public int e;

    PosSizeC _c;
    ComponentMapper<PosSizeC> cM;

    PositionSystem pS;
    Pair<Integer, Integer> center;

    @Override
    public void render(SpriteBatch b, OrthographicCamera c) {

    }

    float defW = 5;
    float w = 0;
    float s = 0;

    @Override
    public void render(ShapeRenderer r, OrthographicCamera c) {

	  _c = cM.get(e);

	  if (c == null) return;

	  r.set(ShapeRenderer.ShapeType.Filled);

	  r.setColor(VisUI.getSkin().getColor("primary"));

	  w = defW * MathUtils.clamp(c.zoom, 0.1f, 2f);

	  float s = w * 1 / 4;

	  r.rect(_c.x - w - s, _c.y - s - w, w, _c.h + 2 * s + 2 * w); // left

	  r.rect(_c.x + _c.w + s, _c.y - w - s, w, _c.h + 2 * s + 2 * w); // right

	  r.rect(_c.x - s, _c.y - s - w, _c.w + 2 * s, w); // bottom

	  r.rect(_c.x - s, _c.y + _c.h + s, _c.w + 2 * s, w); // top

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
	  return IDs.SelR;
    }
}
