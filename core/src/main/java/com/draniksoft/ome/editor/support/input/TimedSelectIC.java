package com.draniksoft.ome.editor.support.input;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.components.supp.SelectionC;
import com.draniksoft.ome.editor.support.event.SelectionChangeE;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.utils.PUtils;
import net.mostlyoriginal.api.event.common.EventSystem;

public class TimedSelectIC implements InputController {

    final static String tag = "TimedSelectIC";

    float tdt = -1;

    /**
     * Removes main tool on selecting or deselecting something
     */
    boolean removeMain = false;

    float tl = 10;
    float pressT = .3f;

    Vector2 tV;
    IntArray es;

    World w;
    Viewport gameVP;
    Viewport uiVP;
    com.badlogic.gdx.physics.box2d.World phys;

    public TimedSelectIC(boolean removeMain) {
        this.removeMain = removeMain;
    }

    public TimedSelectIC() {
    }

    @Override
    public void init(World w) {
        this.w = w;

        gameVP = w.getInjector().getRegistered("game_vp");
        uiVP = w.getInjector().getRegistered("ui_vp");

        phys = w.getInjector().getRegistered(com.badlogic.gdx.physics.box2d.World.class);

        tV = new Vector2();
        es = new IntArray();

    }

    @Override
    public void destruct() {

    }


    private void processI() {

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();

        tV.set(x, y);

        Gdx.app.debug(tag, "TD AT " + tV.toString());

        tV = gameVP.unproject(tV);

        es.clear();
        phys.QueryAABB(new QueryCallback() {
                           @Override
                           public boolean reportFixture(Fixture fixture) {
                               es.add((Integer) fixture.getBody().getUserData());
                               return false;
                           }
                       }, (tV.x - tl) / PUtils.PPM, (tV.y - tl) / PUtils.PPM,
                (tV.x + tl) / PUtils.PPM, (tV.y + tl) / PUtils.PPM);


        IntBag sels = w.getAspectSubscriptionManager().get(Aspect.all(SelectionC.class)).getEntities();

        for (int i = 0; i < sels.size(); i++) {

            if (es.size > 0 && sels.get(i) == es.get(0)) return;

            w.getMapper(SelectionC.class).remove(sels.get(i));

            if (removeMain) w.getSystem(InputSys.class).setMainIC(null);

        }

        int e = -1;
        if (es.size > 0) {

            e = es.get(0);

            w.getMapper(SelectionC.class).create(e);

            if (removeMain) w.getSystem(InputSys.class).setMainIC(null);

        }

        SelectionChangeE ev = new SelectionChangeE();
        ev.old = sels.size() > 0 ? sels.get(0) : -1;
        ev.n = e;
        w.getSystem(EventSystem.class).dispatch(ev);

        Gdx.app.debug(tag, "Selection changed ");


    }

    @Override
    public void update() {
        if (tdt >= 0) {
            tdt += Gdx.graphics.getDeltaTime();
        }

        if (tdt > pressT) {
            Gdx.app.debug(tag, "Touch down");
            processI();
            tdt = -1;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tdt = 0;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        tdt = -1;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        tdt = -1;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
