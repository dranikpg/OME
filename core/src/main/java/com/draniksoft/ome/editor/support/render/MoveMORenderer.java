package com.draniksoft.ome.editor.support.render;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.ems.MoveMOEM;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;

import static java.lang.Math.max;

public class MoveMORenderer implements OverlyRendererI {
    private static final String tag = "MoveMORenderer";

    MoveMOEM em;
    int e;

    World _w;
    DrawableMgr dwmg;

    Drawable dwb;
    TransformDrawable lineD;

    float w, h;
    float x, y;

    float basecoef = 20;
    float coef = basecoef;
    float maxcoefCH = 10;
    Interpolation ipol = Interpolation.circle;

    float lineS;

    float pulse = 1 / 4f;
    float curP = 0;

    @Override
    public void render(int _e, SpriteBatch b, OrthographicCamera c) {

        if (dwb != null)
            dwb.draw(b, x - w / 2, y - h / 2, w, h);

        if (lineD == null) {
            checkDwb();
            return;
        }

        curP += Gdx.graphics.getDeltaTime();
        if (curP > pulse) {
            curP = 0;
        }

        coef = basecoef + ipol.apply(0, maxcoefCH, curP / pulse);

        drawLines(b);

    }

    private void drawLines(SpriteBatch b) {

        float _x = x - w / 2;
        float _y = y - h / 2;


        lineS = max(w / coef, h / coef);


        lineD.draw(b, _x - lineS, _y - lineS, w + 2 * lineS, lineS);
        lineD.draw(b, _x - lineS, _y + h, w + 2 * lineS, lineS);
        lineD.draw(b, _x - lineS, _y, lineS, h);
        lineD.draw(b, _x + w, _y, lineS, h);

    }

    private void checkDwb() {


        if (dwmg.containsAtlas("overlay_r_base")) {

            TextureRegion r = dwmg.getRegion("overlay_r_base@hp");

            if (r == null) return;

            lineD = new TextureRegionDrawable(r);

        }

    }

    @Override
    public void added(World _w) {

        this._w = _w;

        dwmg = _w.getSystem(DrawableMgr.class);

        if (e < 0) return;

        PosSizeC c = _w.getMapper(PosSizeC.class).get(e);
        w = c.w;
        h = c.h;


        dwb = _w.getMapper(DrawableC.class).get(e).d;


    }


    public void setMousePos(float x, float y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void removed() {

    }

    @Override
    public void hidden() {

    }

    @Override
    public int[] getPos() {
        return new int[]{OverlayPlaces.ENTITY_MAIN_BODY};
    }

    @Override
    public int getId() {
        return IDs.MoveMOR;
    }

    public void setEm(MoveMOEM em) {
        this.em = em;
    }

    public void setE(int e) {
        this.e = e;
    }


    public float getPX() {
        return x;
    }

    public float getPY() {
        return y;
    }
}
