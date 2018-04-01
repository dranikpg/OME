package com.draniksoft.ome.editor.base_gfx.batchables;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.cyphercove.gdx.flexbatch.Batchable;
import com.cyphercove.gdx.flexbatch.utils.AttributeOffsets;
import com.cyphercove.gdx.flexbatch.utils.RenderContextAccumulator;
import com.draniksoft.ome.utils.GU;

public class DynamicColorCircle extends Batchable {

    public int x, y;
    public int radius;
    public float color;
    public float seg = 20;


    // usually white
    private TextureRegion rg;

    @Override
    protected boolean prepareContext(RenderContextAccumulator renderContext, int rv, int ri) {
	  // +2 jff !
	  rg = GU.WHITE();
	  return renderContext.setTextureUnit(rg.getTexture(), 0) || rv < seg + 2 || ri < seg + 2;
    }

    @Override
    protected void addVertexAttributes(Array<VertexAttribute> attributes) {

    }

    @Override
    protected int getNumberOfTextures() {
	  return 1;
    }

    @Override
    public void refresh() {

    }

    @Override
    protected int apply(float[] vs, int startingIndex, AttributeOffsets offsets, int vertexSize) {

	  int i = startingIndex;

	  vs[i++] = x;
	  vs[i++] = y;
	  vs[i++] = color;
	  vs[i++] = rg.getU();
	  vs[i++] = rg.getV();

	  float basea = (float) (Math.PI * 2 / seg);
	  float angle = basea;

	  for (int j = 0; j < seg; j++) {
		float cx = radius * MathUtils.cos(angle);
		float cy = radius * MathUtils.sin(angle);

		angle += basea;

		vs[i++] = cx + x;
		vs[i++] = cy + y;
		vs[i++] = color;
		vs[i++] = rg.getU2();
		vs[i++] = rg.getV2();
	  }

	  return (int) (seg + 1);
    }


    @Override
    protected int apply(short[] tris, int si, short fv) {

	  for (int j = 1; j < seg; j++) {
		tris[si++] = fv;
		tris[si++] = (short) (fv + j);
		tris[si++] = (short) (fv + j + 1);
	  }

	  tris[si++] = fv;
	  tris[si++] = (short) (fv + seg);
	  tris[si] = (short) (fv + 1);

	  return (int) seg * 3;
    }

    @Override
    public void reset() {

    }

}
