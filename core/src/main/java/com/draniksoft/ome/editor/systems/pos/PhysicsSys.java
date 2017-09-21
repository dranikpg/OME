package com.draniksoft.ome.editor.systems.pos;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.utils.PUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class PhysicsSys extends BaseSystem {

    @Wire
    World pw;

    ComponentMapper<PhysC> pM;
    ComponentMapper<PosSizeC> p2M;

    int passA = 10;
    int passS = passA;

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
    public Pair<Float, Float> getPos(int _e) {

        if (!pM.has(_e)) return null;

        Body b = pM.get(_e).b;

        return Pair.createPair(b.getPosition().x * PUtils.PPM, b.getPosition().y * PUtils.PPM);

    }

    // positions in the center
    public void setSyncPos(float x, float y, int _e) {

        if (pM.has(_e)) {

            pM.get(_e).b.setTransform(x / PUtils.PPM, y / PUtils.PPM, pM.get(_e).b.getAngle());

        }

        if (p2M.has(_e)) {

            PosSizeC c = p2M.get(_e);
            c.x = (int) x - c.w / 2;
            c.y = (int) y - c.h / 2;

        }


    }

    public void syncPos(int _e) {

        setSyncPos(getPos(_e).getElement0(), getPos(_e).getElement1(), _e);

    }
}
