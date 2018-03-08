package com.draniksoft.ome.editor.manager;

import com.artemis.Aspect;
import com.artemis.EntitySubscription;
import com.artemis.Manager;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.srz.MapDimensC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.JsonUtils;

import java.io.*;

public class
EntitySrzMgr extends Manager implements LoadSaveManager {

    private static final String tag = "EntitySrzManager";


    @Override
    public void save(ProjectSaver s) {

        try {

            Gdx.app.debug(tag, "Starting srz");

		final WorldSerializationManager manager = world.getSystem(WorldSerializationManager.class);
		JsonValue indexI = null;


		if (Env.E_JSON_SRZ) {
		    indexI = JsonUtils.createStringV("etty_T", "json");
		    manager.setSerializer(new JsonArtemisSerializer(world));
		    FUtills.registerJSrz((JsonArtemisSerializer) manager.getSerializer());
		} else {
		    indexI = JsonUtils.createStringV("etty_T", "bin");
		    manager.setSerializer(new KryoArtemisSerializer(world));
		    FUtills.registerBSrz((KryoArtemisSerializer) manager.getSerializer());
		}


		s.getIndexV().addChild(indexI);

            OutputStream st;
		File tF = new File(AppDO.I.F().getTmpDir().getAbsolutePath() + "/etty.data");
		if (!tF.exists()) tF.createNewFile();
		Gdx.app.debug(tag, "Opened bytestream to " + "$TMP_DIR/etty.data");
		st = new FileOutputStream(tF);

		SaveFileFormat f = new SaveFileFormat(getEtty());

		manager.save(st, f);

            st.close();
            Gdx.app.debug(tag, "Closed Serialize stream");


        } catch (Exception e) {
            Gdx.app.error(tag, "", e);
        }
    }

    @Override
    public void load(ProjectLoader ld) {

	  try {

		Gdx.app.debug(tag, "Starting load");

		final WorldSerializationManager manager = world.getSystem(WorldSerializationManager.class);

		if (ld.getIndexV().get("etty_T").asString().equals("json")) {
		    manager.setSerializer(new JsonArtemisSerializer(world));
		    FUtills.registerJSrz((JsonArtemisSerializer) manager.getSerializer());
		} else {
		    manager.setSerializer(new KryoArtemisSerializer(world));
		    FUtills.registerBSrz((KryoArtemisSerializer) manager.getSerializer());
		}

		final InputStream is = new FileInputStream(new File(AppDO.I.F().getTmpDir().getAbsolutePath() + "/etty.data"));

		manager.load(is, SaveFileFormat.class);

		Gdx.app.debug(tag, "Finished file load");

		prepareEtty();

		Gdx.app.debug(tag, "Finished");

	  } catch (Exception er) {

		Gdx.app.error(tag, "", er);

	  }

    }

    private void prepareEtty() {

	  IntBag b = world.getAspectSubscriptionManager().get(Aspect.all(PathDescC.class)).getEntities();

	  for (int i = 0; i < b.size(); i++) {
		//world.getSystem(ObjTimeCalcSys.class).processEntityPathC(b.get(i));
	  }

    }

    @Override
    protected void initialize() {
    }

    public EntitySubscription getEtty() {

	  return world.getAspectSubscriptionManager().get(Aspect.all(MapDimensC.class));
    }





}
