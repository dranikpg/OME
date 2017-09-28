package com.draniksoft.ome.editor.support.render;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.utils.GUtils;

public class PathRenderer implements OverlyRendererI {

    private static final String tag = "PathRenderer";

    public static final int id = 102;

    World _w;

    TransformDrawable d;
    TimedMoveC c;
    ComponentMapper<TimedMoveC> cM;

    Vector2 tmpV;
    float[] rdata = {0f, 0f};

    @Override
    public void render(int _e, SpriteBatch b, Camera c) {

        if (d == null) {
            checkD();
            return;
        }

        if (_e == -1) return;

        if (!cM.has(_e)) return;

        renderLines(cM.get(_e).a, b);

    }

    private void renderLines(Array<MoveDesc> a, SpriteBatch b) {

        for (MoveDesc md : a) {

            GUtils.calcLine(md.sx, md.sy, md.x, md.y, rdata, tmpV);

            d.draw(b, md.sx, md.sy, 0, 0, rdata[0], 10, 1, 1, rdata[1]);

        }

    }

    private void checkD() {

        TextureRegion r = _w.getSystem(DrawableMgr.class).getRegion("i_ehr@hp");

        if (r == null) return;

        d = new TextureRegionDrawable(r);

        Gdx.app.debug(tag, "Collected drawable");

    }

    @Override
    public void added(World _w) {
        this._w = _w;

        cM = _w.getMapper(TimedMoveC.class);
        tmpV = new Vector2();
    }

    @Override
    public void removed() {

    }

    @Override
    public void hidden() {

    }

    @Override
    public int[] getPos() {
        return new int[0];
    }

    @Override
    public int getId() {
        return id;
    }
}
