package com.draniksoft.ome.editor.manager;

import com.artemis.Aspect;
import com.artemis.EntitySubscription;
import com.artemis.Manager;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.load.IntelligentLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class EntitySrzMgr extends Manager implements LoadSaveManager {

    private static final String tag = "EntitySrzManager";


    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {

        try {

            Gdx.app.debug(tag, "Starting srz");

            final WorldSerializationManager manager = new WorldSerializationManager();

            manager.setSerializer(new JsonArtemisSerializer(world));

            OutputStream st;
            File tF = new File(AppDO.I.F().getTmpDir().getAbsolutePath() + "/etty.bin");
            if (!tF.exists()) tF.createNewFile();
            st = new FileOutputStream(tF);

            manager.save(st, new SaveFileFormat(getEtty()));

            st.close();

            Gdx.app.debug(tag, "Closed Serialize stream");

        } catch (Exception e) {
            Gdx.app.error(tag, "", e);
        }
    }

    public EntitySubscription getEtty() {

        return world.getAspectSubscriptionManager().get(Aspect.all(MObjectC.class));
    }

    public static class ComponentNames {

        public static final String MapObject = "MO";
        public static final String TimedC = "TMC";
        public static final String TimedMoveC = "TMVC";

    }

}
