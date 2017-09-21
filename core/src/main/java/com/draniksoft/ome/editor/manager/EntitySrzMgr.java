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
import com.draniksoft.ome.editor.components.tps.LocationC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.map_load.ProjectLoader;
import com.draniksoft.ome.editor.support.actions.loc.CreateLocationA;
import com.draniksoft.ome.utils.JsonUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class EntitySrzMgr extends Manager implements LoadSaveManager {

    private static final String tag = "EntitySrzManager";

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


        toS.clear();

        if (val == null) return;

        for (int i = 0; i < val.size; i++) {

            JsonValue v = val.get(i);

            int x = v.getInt("x");
            int y = v.getInt("y");
            int w = v.getInt("w");
            int h = v.getInt("h");
            String dwbIU = v.getString("dwbIU");

            String n = v.getString("name", "NULL");


            CreateLocationA a = new CreateLocationA(x, y, w, h);
            a.name = n;
            a.dwbIU = dwbIU;
            a.GFX_W = false;
            a._do(world);

            toS.add(a.e);


            if (v.has("s") && v.has("e")) {

                int s = v.getInt("s");
                int e = v.getInt("e");

                TimedC tc = world.getMapper(TimedC.class).get(a.e);
                tc.s = s;
                tc.e = e;
            }


        }


    }

    @Override
    public boolean loadG(ProjectLoader l) {

        if (toS.size == 0) return true;

        int _e = toS.get(c);

        String dwbIU = world.getMapper(MObjectC.class).get(_e).dwbIU;

        world.getMapper(DrawableC.class).get(_e).d =
                new TextureRegionDrawable(world.getSystem(DrawableMgr.class).getRegion(dwbIU));


        c++;
        return c >= toS.size;


    }

    @Override
    public Pair<String, JsonValue> save() {

        Gdx.app.debug(tag, "Saving");

        IntBag es = world.getAspectSubscriptionManager().get(Aspect.all(MObjectC.class)).getEntities();

        JsonValue r = new JsonValue(JsonValue.ValueType.array);

        for (int i = 0; i < es.size(); i++) {

            MObjectC moC = world.getMapper(MObjectC.class).get(es.get(i));

            JsonValue moV = new JsonValue(JsonValue.ValueType.object);

            moV.addChild(JsonUtils.createIntV("x", moC.x));
            moV.addChild(JsonUtils.createIntV("y", moC.y));
            moV.addChild(JsonUtils.createIntV("w", moC.w));
            moV.addChild(JsonUtils.createIntV("h", moC.h));
            moV.addChild(JsonUtils.createStringV("dwbIU", moC.dwbIU));

            moV.addChild("name", JsonUtils.createStringV(world.getMapper(LocationC.class).get(es.get(i)).name));

            if (world.getMapper(TimedC.class).has(es.get(i))) {

                TimedC tC = world.getMapper(TimedC.class).get(es.get(i));

                moV.addChild("s", JsonUtils.createIntV(tC.s));
                moV.addChild("e", JsonUtils.createIntV(tC.e));

            }

            r.addChild(moV);

        }


        return Pair.createPair("entities", r);
    }
}
