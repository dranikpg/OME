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
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.support.event.workflow.ReleaseDataE;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.JsonUtils;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.io.*;

public class EntitySrzMgr extends Manager implements LoadSaveManager {

    private static final String tag = "EntitySrzManager";


    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {

        try {

            Gdx.app.debug(tag, "Starting srz");

		final WorldSerializationManager manager = world.getSystem(WorldSerializationManager.class);
		JsonValue indexI = null;

		if (Env.E_JSON_SRZ) {
		    indexI = JsonUtils.createStringV("etty_T", "json");
		    manager.setSerializer(new JsonArtemisSerializer(world));
		} else {
		    indexI = JsonUtils.createStringV("etty_T", "bin");
		    manager.setSerializer(new KryoArtemisSerializer(world));
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
    public void load(IntelligentLoader il, ProjectLoader ld) {

	  try {

		Gdx.app.debug(tag, "Starting load");

		final WorldSerializationManager manager = world.getSystem(WorldSerializationManager.class);

		if (ld.getIndexV().get("etty_T").asString().equals("json"))
		    manager.setSerializer(new JsonArtemisSerializer(world));
		else
		    manager.setSerializer(new KryoArtemisSerializer(world));

		final InputStream is = new FileInputStream(new File(AppDO.I.F().getTmpDir().getAbsolutePath() + "/etty.data"));

		manager.load(is, SaveFileFormat.class);

		Gdx.app.debug(tag, "Finished");


	  } catch (Exception er) {

		Gdx.app.error(tag, "", er);

	  }

    }

    @Override
    protected void initialize() {
	  world.getSystem(EventSystem.class).registerEvents(this);
    }

    public EntitySubscription getEtty() {

        return world.getAspectSubscriptionManager().get(Aspect.all(MObjectC.class));
    }


    @Subscribe
    public void releaseData(ReleaseDataE ev) {

	  IntBag b = world.getAspectSubscriptionManager().get(Aspect.all(MObjectC.class)).getEntities();

	  for (int e : b.getData()) {

		//ESCUtils.removeSelectionBeforeRMV(e, world);

		world.delete(e);

	  }

    }

}
