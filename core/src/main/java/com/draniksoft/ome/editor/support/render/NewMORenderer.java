package com.draniksoft.ome.editor.support.render;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;

public class NewMORenderer implements OverlyRendererI {

    private static final String tag = "NewMORenderer";

    TextureRegion d;
    World _w;

    float x = -1;
    float y = -1;

    int w;
    int h;

    @Override
    public void render(int _e, SpriteBatch b, OrthographicCamera c) {

        if (d == null) {
            checkD();
            return;
        }

        if (x < 0 || y < 0) return;

        b.draw(d, x - w / 2, y - h / 2, w, h);

    }

    public void setMousePos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private void checkD() {

        TextureRegion r = _w.getSystem(DrawableMgr.class).getRegion("i_casB@mapTile@99");

        if (r == null) return;

        d = r;
        w = d.getRegionWidth();
        h = d.getRegionHeight();

        Gdx.app.debug(tag, "Collected drawable");


    }

    @Override
    public void added(World _w) {
        this._w = _w;
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
        return IDs.NewMOR;
    }
}
