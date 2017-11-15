package com.draniksoft.ome.editor.support.render.def;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.manager.drawable.SimpleDrawableMgr;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class PathRenderer implements OverlyRendererI {

    private static final String tag = "PathRenderer";

    World _w;

    TransformDrawable _d;
    ComponentMapper<TimedMoveC> cM;

    Vector2 tmpV;
    float[] rdata = {0f, 0f};

    @Override
    public void render(int _e, SpriteBatch b, OrthographicCamera c) {

        if (_d == null) {
            checkD();
            return;
        }

        if (_e == -1) return;

        if (!cM.has(_e)) return;

        renderLines(_e, cM.get(_e).a, b);

    }

    float tmpd[] = new float[]{0, 0};
    Vector2 lastP = new Vector2();
    Vector2 v = new Vector2();

    float h = 10f;

    private void renderLines(int _e, Array<MoveDesc> a, SpriteBatch b) {

        float sx, sy;
	  Pair<Float, Float> ep = _w.getSystem(PhysicsSys.class).getPhysPos(_e);

        lastP.set(ep.getElement0(), ep.getElement1());

        for (MoveDesc d : a) {

            if (d.start_x < 0) {
                sx = (int) lastP.x;
                sy = (int) lastP.y;
            } else {
                sx = d.start_x;
                sy = d.start_y;
            }

		GUtils.calcLine(sx, sy, d.end_x, d.end_y, tmpd);

            _d.draw(b, sx, sy, 0, 5, tmpd[0], h, 1 + (h / (tmpd[0] * 2)), 1, tmpd[1]);

            lastP.set(d.end_x, d.end_y);

        }

    }

    private void checkD() {

	  TextureRegion r = _w.getSystem(SimpleDrawableMgr.class).getRegion("i_ehr@hp");

        if (r == null) return;

        _d = new TextureRegionDrawable(r);

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
        return new int[]{OverlayPlaces.PATH, OverlayPlaces.ENTITY_MAIN_BODY};
    }

    @Override
    public int getId() {
        return IDs.PathR;
    }
}
