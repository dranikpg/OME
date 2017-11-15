package com.draniksoft.ome.editor.systems.pos;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.utils.PUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class PhysicsSys extends BaseEntitySystem {

    @Wire
    World pw;

    ComponentMapper<PhysC> pM;
    ComponentMapper<PosSizeC> p2M;

    int passA = 10;
    int passS = passA;


    public PhysicsSys() {
        super(Aspect.all(PhysC.class));
    }

    @Override
    protected void initialize() {

        getSubscription().addSubscriptionListener(new EntitySubscription.SubscriptionListener() {
            @Override
            public void inserted(IntBag entities) {

            }

            @Override
            public void removed(IntBag es) {

                for (int i = 0; i < es.size(); i++) {
                    pw.destroyBody(pM.get(es.get(i)).b);
                }

            }
        });

    }

    @Override
    protected void processSystem() {

        if (passS > 0) {
            passS--;
            return;
        }

        pw.step(Gdx.graphics.getDeltaTime(), 2, 2);

        passS = passA;
    }

    // returns physics pos in map coords
    public Pair<Float, Float> getPhysPos(int _e) {

        if (!pM.has(_e)) return null;

        Body b = pM.get(_e).b;

        return Pair.createPair(b.getPosition().x * PUtils.PPM, b.getPosition().y * PUtils.PPM);

    }

    public void setCentricPos(float x, float y, int _e) {
	  if (pM.has(_e)) {

		pM.get(_e).b.setTransform(x / PUtils.PPM, y / PUtils.PPM, pM.get(_e).b.getAngle());

	  }
    }

    // positions in the center
    public void setSyncCentricPos(float x, float y, int _e) {

	  setCentricPos(x, y, _e);

	  if (p2M.has(_e)) {
		PosSizeC c = p2M.get(_e);
		c.x = (int) x - c.w / 2;
		c.y = (int) y - c.h / 2;
	  }


    }

    public void createBodyFromSPos(int x, int y, int _e) {

	  PosSizeC pc = p2M.get(_e);

	  PhysC c = pM.create(_e);

	  BodyDef bd = new BodyDef();
	  bd.type = BodyDef.BodyType.KinematicBody;
	  bd.position.set((x) / PUtils.PPM, (y) / PUtils.PPM);
	  Body b = world.getInjector().getRegistered(com.badlogic.gdx.physics.box2d.World.class).createBody(bd);
	  b.setUserData(_e);
	  PolygonShape pg = new PolygonShape();
	  pg.setAsBox(pc.w / 2 / PUtils.PPM, pc.h / 2 / PUtils.PPM);
	  FixtureDef fd = new FixtureDef();
	  fd.shape = pg;
	  b.createFixture(fd);
	  pg.dispose();
	  c.b = b;

	  setCentricPos(pc.x + pc.w / 2f, pc.y + pc.h / 2f, _e);


    }

    public void saveMOSyncPos(float x, float y, int _e) {

	  setSyncCentricPos(x, y, _e);

        if (!world.getMapper(MObjectC.class).has(_e)) return;

        MObjectC c = world.getMapper(MObjectC.class).get(_e);
	  c.x = (int) x - c.w / 2;
	  c.y = (int) y - c.h / 2;

    }

    public void syncPos(int _e) {

	  setSyncCentricPos(getPhysPos(_e).getElement0(), getPhysPos(_e).getElement1(), _e);

    }
}
