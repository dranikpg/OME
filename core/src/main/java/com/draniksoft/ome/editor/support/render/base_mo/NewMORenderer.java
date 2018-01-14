package com.draniksoft.ome.editor.support.render.base_mo;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.draniksoft.ome.editor.manager.drawable.SimpleAssMgr;
import com.draniksoft.ome.editor.support.render.core.OverlayRendererI;

public class NewMORenderer implements OverlayRendererI {

    private static final String tag = "NewMORenderer";

    TextureRegion d;
    World _w;

    float x = -1;
    float y = -1;

    int w;
    int h;

    public void setMousePos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private void checkD() {

	  TextureRegion r = _w.getSystem(SimpleAssMgr.class).getRegion("i_casB@mapTile@99");

        if (r == null) return;

        d = r;
        w = d.getRegionWidth();
        h = d.getRegionHeight();

	  Gdx.app.debug(tag, "Collected dwb_typevw");


    }

    @Override
    public void render(SpriteBatch b, OrthographicCamera c) {

    }

    @Override
    public void render(ShapeRenderer r, OrthographicCamera c) {

    }

    @Override
    public void added(World _w) {
	  this._w = _w;
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
