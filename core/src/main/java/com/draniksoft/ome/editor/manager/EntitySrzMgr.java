package com.draniksoft.ome.editor.manager;

import com.artemis.Aspect;
import com.artemis.Manager;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.time.TimedC;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.support.actions.mapO.CreateNewMOA;
import com.draniksoft.ome.editor.support.actions.timed.AddTimeCA;
import com.draniksoft.ome.editor.support.actions.timed.AddTimedMoveA;
import com.draniksoft.ome.editor.support.actions.timed.AddTimedMoveCA;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.support.map_load.ProjectLoader;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class EntitySrzMgr extends Manager implements LoadSaveManager {

    private static final String tag = "EntitySrzManager";

    public static class ComponentNames {

        public static final String MapObject = "MO";
        public static final String TimedC = "TMC";
        public static final String TimedMoveC = "TMVC";

    }

    @Override
    public String getNode() {
        return "entities";
    }

    IntArray toS;
    int c = 0;

    @Override
    protected void initialize() {
        toS = new IntArray();
    }

    @Override
    public void loadL(JsonValue val, ProjectLoader l) {

        c = 0;
        toS.clear();

        if (val == null) return;

        for (int i = 0; i < val.size; i++) {

            JsonValue v = val.get(i);

            serializeEtty(v);
        }


    }



    @Override
    public boolean loadG(ProjectLoader l) {

        if (toS.size == 0) return true;

        int _e = toS.get(c);

        String dwbIU = world.getMapper(MObjectC.class).get(_e).dwbID;

        world.getMapper(DrawableC.class).get(_e).d =
                new TextureRegionDrawable(world.getSystem(DrawableMgr.class).getRegion(dwbIU));


        c++;
        return c >= toS.size - 1;


    }

    private void serializeEtty(JsonValue rootV) {

        int _e = -1;
        if (rootV.has(ComponentNames.MapObject)) {

            CreateNewMOA a = new CreateNewMOA();
            JsonValue lr = rootV.get(ComponentNames.MapObject);
            a.x = lr.getInt("x");
            a.y = lr.getInt("y");
            a.w = lr.getInt("w");
            a.h = lr.getInt("h");
            a.dwbID = lr.getString("dwbID");

            a.GFX_PRC = false;

            a._do(world);
            _e = a._e;
            a.destruct();

            toS.add(_e);
        } else {
            _e = world.create();
        }

        if (rootV.has(ComponentNames.TimedC)) {
            JsonValue lr = rootV.get(ComponentNames.TimedC);
            AddTimeCA a = new AddTimeCA();
            a._e = _e;
            a.se = lr.getInt("s");
            a.ee = lr.getInt("e");
            a.DISPATCH_EVENTS = false;
            a._do(world);
            a.destruct();
        }


        if (rootV.has(ComponentNames.TimedMoveC)) {
            JsonValue lr = rootV.get(ComponentNames.TimedMoveC);
            AddTimedMoveCA ac = new AddTimedMoveCA();
            ac._e = _e;
            ac._do(world);
            ac.destruct();

            for (JsonValue lv : lr) {
                AddTimedMoveA la = new AddTimedMoveA();
                la._e = _e;
                la.e = lv.getInt("e");
                la.s = lv.getInt("s");
                la.sx = lv.getInt("sx");
                la.sy = lv.getInt("sy");
                la.ex = lv.getInt("ex");
                la.ey = lv.getInt("ey");
                la._do(world);
                la.destruct();
            }

        }


    }

    public JsonValue deserializeEtty(int _e) {

        JsonValue v = new JsonValue(JsonValue.ValueType.object);

        if (world.getMapper(MObjectC.class).has(_e)) {

            JsonValue moV = new JsonValue(JsonValue.ValueType.object);
            MObjectC c = world.getMapper(MObjectC.class).get(_e);

            moV.addChild("x", JsonUtils.createIntV(c.x));
            moV.addChild("y", JsonUtils.createIntV(c.y));
            moV.addChild("w", JsonUtils.createIntV(c.w));
            moV.addChild("h", JsonUtils.createIntV(c.h));
            moV.addChild("dwbID", JsonUtils.createStringV(c.dwbID));

            v.addChild(ComponentNames.MapObject, moV);

            Gdx.app.debug(tag, "Added MO to composition");
        }

        if (world.getMapper(TimedC.class).has(_e)) {
            JsonValue lv = new JsonValue(JsonValue.ValueType.object);
            TimedC c = world.getMapper(TimedC.class).get(_e);
            lv.addChild("s", JsonUtils.createIntV(c.s));
            lv.addChild("e", JsonUtils.createIntV(c.e));

            v.addChild(lv);
        }

        if (world.getMapper(TimedMoveC.class).has(_e)) {

            JsonValue lr = new JsonValue(JsonValue.ValueType.array);

            for (MoveDesc d : world.getMapper(TimedMoveC.class).get(_e).a) {

                JsonValue lv = new JsonValue(JsonValue.ValueType.object);
                lv.addChild("s", JsonUtils.createIntV(d.s));
                lv.addChild("e", JsonUtils.createIntV(d.e));
                lv.addChild("sx", JsonUtils.createIntV(d.sx));
                lv.addChild("sy", JsonUtils.createIntV(d.sy));
                lv.addChild("ex", JsonUtils.createIntV(d.x));
                lv.addChild("ey", JsonUtils.createIntV(d.y));

                lr.addChild(lv);
            }

            v.addChild(lr);

        }

        return v;
    }

    @Override
    public Pair<String, JsonValue> save() {

        Gdx.app.debug(tag, "Saving");

        IntBag es = world.getAspectSubscriptionManager().get(Aspect.all(MObjectC.class)).getEntities();

        JsonValue r = new JsonValue(JsonValue.ValueType.array);

        for (int i = 0; i < es.size(); i++) {

            JsonValue oV = deserializeEtty(es.get(i));

            r.addChild(oV);

        }


        return Pair.createPair("entities", r);
    }
}
