package com.draniksoft.ome.editor.support.actions.mapO;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.manager.ArchTransmuterMgr;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.utils.ESCUtils;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.PUtils;

public class CreateNewMOA implements Action {

    public boolean GFX_PRC = true;

    private World _w;

    public float x, y;

    // center
    public float w, h;

    public String dwbID;


    public int _e;

    public CreateNewMOA(float x, float y, float w, float h, String dwbID) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dwbID = dwbID;
    }

    public CreateNewMOA() {
    }

    @Override
    public void invoke(World _w) {
        this._w = _w;
        _e = _w.getSystem(ArchTransmuterMgr.class).build(ArchTransmuterMgr.Codes.BASE_MO);

        MObjectC c = _w.getMapper(MObjectC.class).get(_e);
        c.setX((int) x);
        c.setY((int) y);
        c.setW((int) w);
        c.setH((int) h);
        c.setDwbData(dwbID);

        PosSizeC psc = _w.getMapper(PosSizeC.class).get(_e);
        psc.x = (int) (x - (w / 2));
        psc.y = (int) (y - (h / 2));
        psc.h = (int) h;
        psc.w = (int) w;

        PhysC physC = _w.getMapper(PhysC.class).get(_e);
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.position.set((x) / PUtils.PPM, (y) / PUtils.PPM);
        Body b = _w.getInjector().getRegistered(com.badlogic.gdx.physics.box2d.World.class).createBody(bd);
        b.setUserData(_e);
        PolygonShape pg = new PolygonShape();
        pg.setAsBox(w / 2 / PUtils.PPM, h / 2 / PUtils.PPM);
        FixtureDef fd = new FixtureDef();
        fd.shape = pg;
        b.createFixture(fd);
        pg.dispose();
        physC.b = b;

        if (!GFX_PRC) return;

        DrawableC dwC = _w.getMapper(DrawableC.class).get(_e);
	  dwC.d = FUtills.fetchDrawable(dwbID);


        String tag = "CreateNewMOA";
        Gdx.app.debug(tag, "Fully created MO with " + dwbID);
    }

    @Override
    public void undo(World w) {

        ESCUtils.removeSelectionBeforeRMV(_e, _w);
        _w.delete(_e);

    }


    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }

    @Override
    public String getSimpleConcl() {
        return "Created MO";
    }

    @Override
    public void destruct() {

    }
}
