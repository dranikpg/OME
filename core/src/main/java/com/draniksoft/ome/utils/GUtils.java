package com.draniksoft.ome.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.draniksoft.ome.main_menu.MainBase;

import javax.swing.*;
import java.io.File;
import java.nio.IntBuffer;

/**
 * Graphics/GLWF utils
 *
 *
 * */

public class GUtils {

    public static int maxTSize = 16000;

    public static ShapeRenderer sr;

    public static Lwjgl3Graphics getGraphics(){
        return (Lwjgl3Graphics) Gdx.graphics;
    }

    public static Lwjgl3Window getWindow(){
        return getGraphics().getWindow();
    }

    public static Lwjgl3Application getApp(){
        return (Lwjgl3Application) Gdx.app;
    }


    public static Lwjgl3ApplicationConfiguration getEditorConfig() {

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("OpenMapEditor");

        configuration.setWindowedMode(640, 480);
        configuration.setMaximized(true);

        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        configuration.setBackBufferConfig(8, 8, 8, 8, 16, 8, 2);

        return configuration;

    }

    public static class Colors {
	  public static Color PRIMARY = Color.BLUE;
	  public static Color PRIMARY_BRIGHT = Color.SKY;
	  public static Color PRIMARY_DARK = Color.NAVY;
	  public static Color ACCENT = Color.MAGENTA;
	  public static Color SECONDARY = Color.LIGHT_GRAY;
    }

    public static int fetchMaxTexSize() {
        IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
        Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);
        return  intBuffer.get();
    }


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

    /**
     * @param i   x start base_em
     * @param i1  y start base_em
     * @param i2  x end base_em
     * @param i3  y end base_em
     * @param data float array of size two to fetch data : first element - distance, second - the angle
     */

    static Vector2 tV = new Vector2();

    public static void calcLine(float i, float i1, float i2, float i3, float[] data) {

        tV.set(i, i1);

        float dst = tV.dst(i2, i3);

        float a = (float) (Math.atan2(i3 - i1, i2 - i) * 180f / Math.PI);

        data[0] = dst;
        data[1] = a;

    }


    void tmp() {


    }


    public static void openEditorWin() {

        GUtils.getApp().newWindow(new MainBase(), GUtils.getEditorConfig());

        GUtils.getWindow().closeWindow();

    }

    private static boolean nativeSwingGui = false;

    private static void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            reportError();
        }
    }

    private static void reportError() {


    }

    private static void checkSwingState() {
        System.out.println("Checking SWING state ...");
        if (!nativeSwingGui) setNativeLookAndFeel();
    }


    public static void showFileDialog(final JFileChooser ch, final Runnable r) {


        Runnable fr = new Runnable() {
            @Override
            public void run() {
                int returnValue = ch.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = ch.getSelectedFile();
                    FUtills.JF_FILE = selectedFile;
                    System.out.println(selectedFile.getName());
                }

                FUtills.JF_OPTION = returnValue;

                r.run();

                FUtills.JF_OPTION = -5;
                FUtills.JF_FILE = null;

            }
        };

        new Thread(fr).start();


    }

    public static JFileChooser createFileDialog() {
        checkSwingState();

        JFileChooser ch = new JFileChooser();

        return ch;
    }



}
