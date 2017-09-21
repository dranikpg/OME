package com.draniksoft.ome.editor.manager;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.EntityTransmuter;
import com.artemis.Manager;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.gfx.TexRegC;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.time.TimedC;
import com.draniksoft.ome.editor.components.tps.LocationC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.components.tps.MapC;

public class ArchTransmuterMgr extends Manager{

    public static class Codes{

        public static final int MAP_C = 1;
        public static final int BASE_LOCATION = 2;
        public static final int TIMED_LOCATION = 3;


    }

    IntMap<Archetype> types;
    IntMap<EntityTransmuter> tmuters;

    public ArchTransmuterMgr(){

    }

    @Override
    protected void initialize() {
        types  = new IntMap<Archetype>();

        tmuters = new IntMap<EntityTransmuter>();

        buildBaseArchTypes();
    }

    public void buildBaseArchTypes(){

        types.put(Codes.MAP_C,new ArchetypeBuilder().add(TexRegC.class)
                .add(MapC.class).add(PosSizeC.class).build(world));

        types.put(Codes.BASE_LOCATION, new ArchetypeBuilder().add(PhysC.class)
                .add(PosSizeC.class).add(DrawableC.class)
                .add(LocationC.class).add(MObjectC.class)
                .build(world));

        types.put(Codes.TIMED_LOCATION, new ArchetypeBuilder().add(PhysC.class)
                .add(PosSizeC.class).add(DrawableC.class)
                .add(LocationC.class).add(MObjectC.class)
                .add(TimedC.class).build(world));

    }

    public void registerArchType(){

    }

    public Archetype getArchType(int code) {
        return types.get(code);
    }

    public int build(int code){
        return world.create(types.get(code));
    }

}
