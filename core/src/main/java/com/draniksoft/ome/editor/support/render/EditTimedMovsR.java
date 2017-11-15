package com.draniksoft.ome.editor.support.render;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.manager.drawable.SimpleDrawableMgr;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.ems.timed.EditTimedMovsEM;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class EditTimedMovsR implements OverlyRendererI {


    EditTimedMovsEM em;
    private Array<MoveDesc> arr;

    TransformDrawable _d;
    TransformDrawable _d2;

    World _w;

    Vector2 v;
    Pair<Float, Float> p;

    Vector2 lastP;

    float[] tmpd;

    float h = 10f;

    @Override
    public void render(int _e, SpriteBatch batch, OrthographicCamera c) {

        if (_d == null || _d2 == null) {
            fetchD();
            return;
        }

        lastP.set(p.getElement0(), p.getElement1());

        int sx, sy;

        int i = 0;
        for (MoveDesc d : arr) {

            if (d.start_x == -1) {
                sx = (int) lastP.x;
                sy = (int) lastP.y;
            } else {
                sx = d.start_x;
                sy = d.start_y;
            }

		GUtils.calcLine(sx, sy, d.end_x, d.end_y, tmpd);

            TransformDrawable __d;

            if (i == em.selI) {
                __d = _d2;
            } else {
                __d = _d;
            }
            __d.draw(batch, sx, sy, 0, 5, tmpd[0], h, 1 + (h / (tmpd[0] * 2)), 1, tmpd[1]);


            lastP.set(d.end_x, d.end_y);

            i++;
        }

        if (em.newM) {

		GUtils.calcLine(lastP.x, lastP.y, em.px, em.py, tmpd);


            _d.draw(batch, lastP.x, lastP.y, 0, 5, tmpd[0], h, 1, 1, tmpd[1]);

        }


    }

    private void fetchD() {

	  if (_w.getSystem(SimpleDrawableMgr.class).hasAtlas("overlay_r_base", true)) {

		TextureRegion r = _w.getSystem(SimpleDrawableMgr.class).getRegion("overlay_r_base@hp");
		_d = new TextureRegionDrawable(r);

		TextureRegion r2 = _w.getSystem(SimpleDrawableMgr.class).getRegion("overlay_r_base@n_way");

            _d2 = new TextureRegionDrawable(r2);


        }

    }

    @Override
    public void added(World _w) {

        this._w = _w;

        v = new Vector2();
        tmpd = new float[]{0, 0};

        lastP = new Vector2();

        p = em.getEPos();


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
        return IDs.EditTimedMOR;
    }

    public void setEm(EditTimedMovsEM em) {
        this.em = em;
    }

    public void setArr(Array<MoveDesc> arr) {
        this.arr = arr;
    }
}
