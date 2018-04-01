package com.draniksoft.ome.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.main_menu.MainBase;

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
	  return Math.max(12, (int) (3 * (float) Math.cbrt(r)));
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


    public static Lwjgl3Graphics getGraphics() {
	  return (Lwjgl3Graphics) Gdx.graphics;
    }

    public static Lwjgl3Window getWindow() {
	  return getGraphics().getWindow();
    }

    public static Lwjgl3Application getApp() {
	  return (Lwjgl3Application) Gdx.app;
    }

    public static Lwjgl3Input getInput() {
	  return (Lwjgl3Input) Gdx.input;
    }


    public static Lwjgl3ApplicationConfiguration getEditorConfig() {

	  Gdx.input.setOnscreenKeyboardVisible(true);

	  Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
	  configuration.setTitle("OpenMapEditor");

	  configuration.setWindowedMode(640, 480);
	  configuration.setMaximized(true);

	  configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

	  configuration.setBackBufferConfig(8, 8, 8, 8, 0, 0, 1);

	  return configuration;

    }

    public static void openEditorWin() {

	  GU.getApp().newWindow(new MainBase(), GU.getEditorConfig());

	  GU.getWindow().closeWindow();

    }

    //



}
