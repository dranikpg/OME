package com.draniksoft.ome.editor.support.render;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;

public class SelectionRenderer implements OverlyRendererI {

    public static final int id = 101;

    private final static String tag = "SelectionRenderer";

    World _w;

    TransformDrawable d;
    PosSizeC c;
    ComponentMapper<PosSizeC> pM;


    @Override
    public void render(int _e, SpriteBatch b, Camera cam) {

        if (d == null) {
            checkD();
            return;
        }

        if (_e == -1) return;

        c = pM.get(_e);

        d.draw(b, c.x, c.y + c.h / 2, c.w, 10);


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
        return id;
    }
}
