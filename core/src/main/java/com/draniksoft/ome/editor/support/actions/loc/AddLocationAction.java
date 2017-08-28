package com.draniksoft.ome.editor.support.actions.loc;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.manager.ArchTransmuterMgr;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.utils.PUtils;

public class AddLocationAction implements Action {

    int x, y;
    int w = 40;
    int h = 40;

    int e;

    String name;

    public AddLocationAction(int x, int y, String name) {
        this.x = x - w / 2;
        this.y = y - h / 2;
        this.name = name;
    }

    public AddLocationAction(int x, int y, int w, int h, String name) {
        this.x = x - w / 2;
        this.y = y - h / 2;
        this.w = w;
        this.h = h;
        this.name = name;
    }

    @Override
    public void _do(World _w) {

        e = _w.getSystem(ArchTransmuterMgr.class).build(ArchTransmuterMgr.Codes.BASE_LOCATION);

        PosSizeC pc = _w.getMapper(PosSizeC.class).get(e);
        pc.x = x;
        pc.y = y;
        pc.w = w;
        pc.h = h;

        DrawableC dc = _w.getMapper(DrawableC.class).get(e);
        dc.d = new TextureRegionDrawable(new TextureRegion(_w.getSystem(DrawableMgr.class).t));

        /*FLabelC lc = _w.getMapper(FLabelC.class).create(e);
        lc.txt = name;
        */

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

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }
}
