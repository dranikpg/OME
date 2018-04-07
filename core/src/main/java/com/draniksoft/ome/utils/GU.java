package com.draniksoft.ome.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;

import java.nio.IntBuffer;

/**
 * Graphics/GLWF utils
 *
 *
 * */

public class GU {

    // for const size drawables
    public static float CAM_SCALE = 1f;

    public static int MAX_TEX_SIZE = 16000;

    // res

    public static ShapeRenderer sr;

    public static TextureRAccesor whiteAC;

    public static TextureRegion WHITE() {
	  return whiteAC.acl();
    }



    public static int fetchMaxTexSize() {
        IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
        Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);
        return  intBuffer.get();

    }

    public static void initGLData() {
	  String tag = "GUTILS :: GL_DATA_FETCH ";

	  MAX_TEX_SIZE = fetchMaxTexSize() / 2;

	  Gdx.app.debug(tag, " Tex max size " + MAX_TEX_SIZE);

    }

    public static int CIRCLE_SEGMENTS(int r) {
	  return Math.max(8, (int) (8 * (float) Math.cbrt(r)));
	  // return 4;
    }

    public static float CAM_SCALE(float n, float x) {
	  return MathUtils.clamp(CAM_SCALE, n, x);
    }

    //

    public static TextureRegion[][] splitTITR(Texture t, int h, int w) {

        int yb = MathUtils.ceil(t.getHeight() * 1f / h);
        int xb = MathUtils.ceil(t.getWidth() * 1f / w);

        TextureRegion[][] r = new TextureRegion[yb][xb];

        for (int y = yb - 1; y >= 0; y--) {

            for (int x = 0; x < xb; x++) {

                r[y][x] = new TextureRegion(t, x * w, Math.max(0, t.getHeight() - (y + 1) * (h)), Math.min(w, t.getWidth() - x * w), (Math.min(t.getHeight() - (y + 1) * (h), 0) + h));

            }

        }

        return r;

    }

    //

}
