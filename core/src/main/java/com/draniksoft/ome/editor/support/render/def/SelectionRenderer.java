package com.draniksoft.ome.editor.support.render.def;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.utils.FUtills;

public class SelectionRenderer implements OverlyRendererI {


    private final static String tag = "SelectionRenderer";

    World _w;

    NinePatchDrawable d;
    PosSizeC c;
    ComponentMapper<PosSizeC> pM;

    float bc = 20;

    float v = 0;


    @Override
    public void render(int _e, SpriteBatch b, OrthographicCamera cam) {

        if (d == null) {
            checkD();
            return;
        }

        if (_e == -1) return;

        c = pM.get(_e);

        float sh = c.h / bc * MathUtils.clamp(cam.zoom * 2, 0.5f, 20);
        float sw = c.w / bc * MathUtils.clamp(cam.zoom * 2, 0.5f, 20);

        v = Math.min(sh, sw);

        d.draw(b, c.x - v, c.y + c.h, c.w + v, sh);
        d.draw(b, c.x - v, c.y - sh, c.w + v, sh);

        d.draw(b, c.x + c.w, c.y - v, v, c.h + v);
        d.draw(b, c.x - v, c.y - v, v, c.h + v);


    }

    private void checkD() {

        d = (NinePatchDrawable) FUtills.fetchDrawable(FUtills.DrawablePrefix.P_SKINSRC + FUtills.DrawablePrefix.P_NINEPATCH + "BRDWB");

        Gdx.app.debug(tag, "Collected drawable");

    }

    @Override
    public void added(World _w) {
        this._w = _w;
        pM = _w.getMapper(PosSizeC.class);
    }

    @Override
    public void removed() {

    }

    @Override
    public void hidden() {

    }

    @Override
    public int[] getPos() {
        return new int[]{
                OverlayPlaces.ENTITY_MAIN_BODY
        };
    }

    @Override
    public int getId() {
        return IDs.SelR;
    }
}
