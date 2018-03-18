package com.draniksoft.ome.editor.manager;

import com.artemis.Aspect;
import com.artemis.EntitySubscription;
import com.artemis.Manager;
import com.artemis.io.KryoArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.srz.MapDimensC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.FUtills;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.*;

public class SerializationManager extends Manager implements LoadSaveManager {

    private static final String tag = "EntitySrzManager";

    KryoArtemisSerializer kryoBackend;

    final Object KRYO_LOCK = new Object();

    @Override
    protected void initialize() {

	  kryoBackend = new KryoArtemisSerializer(world);

	  final WorldSerializationManager manager = world.getSystem(WorldSerializationManager.class);
	  manager.setSerializer(kryoBackend);

	  //kryoBackend.getKryo().setAutoReset(true);

	  FUtills.registerBSrz(kryoBackend);
    }

    @Override
    public void save(ProjectSaver s) {

	  Gdx.app.debug(tag, "Starting srz");

	  final WorldSerializationManager manager = world.getSystem(WorldSerializationManager.class);

	  try {

		synchronized (KRYO_LOCK) {

		    OutputStream st;
		    File tF = new File(AppDO.I.F().getTmpDir().path() + "/etty.data");

		    if (!tF.exists()) tF.createNewFile();

		    Gdx.app.debug(tag, "Opened bytestream to " + "$TMP_DIR/etty.data");
		    st = new FileOutputStream(tF);

		    SaveFileFormat f = new SaveFileFormat(getEtty());

		    manager.save(st, f);

		    st.close();

		    Gdx.app.debug(tag, "Closed Serialize stream");

		}

	  } catch (Exception e) {
		Gdx.app.error(tag, "", e);
	  }
    }

    @Override
    public void load(ProjectLoader ld) {

	  try {

		synchronized (KRYO_LOCK) {

		    Gdx.app.debug(tag, "Starting load");

		    final WorldSerializationManager manager = world.getSystem(WorldSerializationManager.class);

		    final InputStream is = new FileInputStream(new File(AppDO.I.F().getTmpDir().path() + "/etty.data"));

		    manager.load(is, SaveFileFormat.class);

		    Gdx.app.debug(tag, "Finished file load");

		    prepareEtty();

		    Gdx.app.debug(tag, "Finished");


		}

	  } catch (Exception er) {

		Gdx.app.error(tag, "", er);

	  }

    }

    /*
    	Kryo commands
     */

    public void writeKryoData(String path, Object o) throws FileNotFoundException {
	  OutputStream st = new FileOutputStream(new File(path));
	  writeKryoData(new ByteBufferOutput(st), o);
    }

    public void writeKryoData(Output outp, Object o) {
	  synchronized (KRYO_LOCK) {
		kryoBackend.getKryo().writeObject(outp, o);
	  }
    }

    public <T> T loadKryoData(String path, Class<T> c) throws FileNotFoundException {
	  InputStream st = new FileInputStream(new File(path));
	  return loadKryoData(new ByteBufferInput(st), c);
    }

    public <T> T loadKryoData(Input input, Class<T> c) {
	  T res = null;
	  synchronized (KRYO_LOCK) {
		res = kryoBackend.getKryo().readObject(input, c);
	  }
	  return res;
    }

    private void prepareEtty() {

	  IntBag b = world.getAspectSubscriptionManager().get(Aspect.all(PathDescC.class)).getEntities();

	  for (int i = 0; i < b.size(); i++) {
		//world.getSystem(ObjTimeCalcSys.class).processEntityPathC(b.get(i));
	  }

    }


    public EntitySubscription getEtty() {

	  return world.getAspectSubscriptionManager().get(Aspect.all(MapDimensC.class));
    }





}
