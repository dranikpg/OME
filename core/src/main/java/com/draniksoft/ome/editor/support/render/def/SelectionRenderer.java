package com.draniksoft.ome.editor.support.render.def;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;

public class SelectionRenderer implements OverlyRendererI {


    private final static String tag = "SelectionRenderer";

    World _w;

    TransformDrawable d;
    PosSizeC c;
    ComponentMapper<PosSizeC> pM;

    float bc = 20;


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
        d.draw(b, c.x - sw, c.y + c.h, c.w + 2 * sw, sh);
        d.draw(b, c.x - sw, c.y - sh, c.w + 2 * sw, sh);
        d.draw(b, c.x + c.w, c.y, sw, c.h);
        d.draw(b, c.x - sw, c.y, sw, c.h);


    }

    private void checkD() {

        TextureRegion r = _w.getSystem(DrawableMgr.class).getRegion("overlay_r_base@hp");

        if (r == null) return;

        d = new TextureRegionDrawable(r);

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
