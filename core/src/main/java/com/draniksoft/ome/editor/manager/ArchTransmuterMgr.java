package com.draniksoft.ome.editor.manager;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.EntityTransmuter;
import com.artemis.Manager;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.MapC;
import com.draniksoft.ome.editor.components.PosSizeC;
import com.draniksoft.ome.editor.components.TexRegC;

public class ArchTransmuterMgr extends Manager{

    public static class Codes{

        public static final int MAP_C = 1;

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

    }

    public void registerArchType(){

    }

    public void getArchType(int code){

    }

    public int build(int code){
        return world.create(types.get(code));
    }

}
