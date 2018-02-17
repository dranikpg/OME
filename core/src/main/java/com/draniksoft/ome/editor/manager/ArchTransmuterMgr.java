package com.draniksoft.ome.editor.manager;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Manager;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.gfx.TexRegC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.components.srz.DrawableSrcC;
import com.draniksoft.ome.editor.components.srz.MapDimensC;
import com.draniksoft.ome.editor.components.tps.MapC;
import com.draniksoft.ome.editor.components.tps.MapObjectC;

public class ArchTransmuterMgr extends Manager{

    public static class Codes{

        public static final int MAP_C = 1;
        public static final int BASE_MO = 10;


    }

    volatile IntMap<Archetype> types;

    public ArchTransmuterMgr(){

    }

    @Override
    protected void initialize() {
        types  = new IntMap<Archetype>();


        buildBaseArchTypes();
    }

    public void buildBaseArchTypes(){

        types.put(Codes.MAP_C,new ArchetypeBuilder().add(TexRegC.class)
                .add(MapC.class).add(PosSizeC.class).build(world));

	  types.put(Codes.BASE_MO, new ArchetypeBuilder()
		    .add(PosSizeC.class, MapDimensC.class).add(DrawableC.class, DrawableSrcC.class).
				add(MapObjectC.class)
		    .build(world));


    }

    public void registerArchType(){

    }

    public Archetype getArchType(int code) {
        return types.get(code);
    }

    public synchronized int build(int code) {
	  return world.create(types.get(code));
    }

}
