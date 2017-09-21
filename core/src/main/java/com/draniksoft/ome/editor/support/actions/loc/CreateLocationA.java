package com.draniksoft.ome.editor.support.actions.loc;

import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.selection.FLabelC;
import com.draniksoft.ome.editor.components.tps.LocationC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.manager.ArchTransmuterMgr;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.utils.PUtils;

public class CreateLocationA implements Action {

    public int x, y;
    public int w = 40;
    public int h = 40;

    public int e;

    public boolean GFX_W = true;

    public String name;
    public String dwbIU = "i_casB@mapTile@100";

    public CreateLocationA(int x, int y, String texN) {
        this.x = x - w / 2;
        this.y = y - h / 2;
        this.name = texN;
    }


    public CreateLocationA(int x, int y, int w, int h) {
        this.x = x - w / 2;
        this.y = y - h / 2;
        this.w = w;
        this.h = h;
        this.name = name;
    }

    @Override
    public void _do(World _w) {

        e = _w.getSystem(ArchTransmuterMgr.class).build(ArchTransmuterMgr.Codes.BASE_LOCATION);

        LocationC loc = _w.getMapper(LocationC.class).get(e);
        loc.name = name;

        MObjectC moc = _w.getMapper(MObjectC.class).get(e);
        moc.x = x + (w / 2);
        moc.y = y + (h / 2);
        moc.w = w;
        moc.h = h;
        moc.dwbIU = dwbIU;

        PosSizeC pc = _w.getMapper(PosSizeC.class).get(e);
        pc.x = x;
        pc.y = y;
        pc.w = w;
        pc.h = h;

        DrawableC dc = _w.getMapper(DrawableC.class).get(e);
        if (GFX_W)
            dc.d = new TextureRegionDrawable(_w.getSystem(DrawableMgr.class).getRegion(dwbIU));

        FLabelC lc = _w.getMapper(FLabelC.class).create(e);
        lc.lid = -1;
        lc.txt = name;


        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.position.set((x + (w / 2)) / PUtils.PPM, (y + (h / 2)) / PUtils.PPM);

        Body b = _w.getInjector().getRegistered(com.badlogic.gdx.physics.box2d.World.class).createBody(bd);
        b.setUserData(e);

        PolygonShape pg = new PolygonShape();
        pg.setAsBox(w / 2 / PUtils.PPM, h / 2 / PUtils.PPM);

        FixtureDef fd = new FixtureDef();
        fd.shape = pg;

        b.createFixture(fd);
        pg.dispose();

        PhysC bc = _w.getMapper(PhysC.class).get(e);
        bc.b = b;

    }

    @Override
    public void _undo(World _w) {

        _w.getInjector().getRegistered(com.badlogic.gdx.physics.box2d.World.class).destroyBody(
                _w.getMapper(PhysC.class).get(e).b);

        _w.delete(e);


    }

    public int getE() {
        return e;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }
}
