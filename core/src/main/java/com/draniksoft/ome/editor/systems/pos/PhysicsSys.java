package com.draniksoft.ome.editor.systems.pos;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsSys extends BaseSystem {

    @Wire
    World pw;

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
}
