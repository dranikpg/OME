package com.draniksoft.ome.editor.systems.time;

import com.artemis.Aspect;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.components.pos.PosBoundsC;
import com.draniksoft.ome.editor.components.pos.PosC;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.utils.iface.Awaitable;
import net.mostlyoriginal.api.event.common.Subscribe;
import net.mostlyoriginal.api.system.core.SpreadProcessingSystem;

public class ObjTimeCalcSys extends SpreadProcessingSystem {

    final static String tag = "ObjTimeCalcSys";


    public ObjTimeCalcSys() {
	  super(Aspect.all(PosC.class, PosBoundsC.class), 1 / 20f);
	  tmpV = new Vector2();
    }

    Vector2 tmpV;

    @Override
    protected void initialize() {
        setEnabled(false);
	  world.getSystem(OmeEventSystem.class).registerEvents(this);
    }

    @Override
    protected void process(int e) {
        int t = world.getSystem(TimeMgr.class).getTime();

    }


    @Subscribe
    public void modeChanged(ModeChangeE e) {
        if (e instanceof ModeChangeE.ShowQuitEvent) {
		new RestoreSHWThread().start();
		setEnabled(false);
        } else if (e instanceof ModeChangeE.ShowEnterEvent) {
            setEnabled(true);
        } else if (e instanceof ModeChangeE.ShowRequestEvent) {
            reload((Awaitable) e);
        }
    }

    private void reload(Awaitable aw) {
        aw.await();
        InitOnTimeThread t = new InitOnTimeThread();

        t.t = world.getSystem(TimeMgr.class).getTime();
        t.aw = aw;

        t.start();
    }

    private class RestoreSHWThread extends Thread {

	  public RestoreSHWThread() {
		setName("ObjTimeCalcSys :: RELOAD");
	  }

	  @Override
	  public void run() {
		IntBag b = ObjTimeCalcSys.this.getEntityIds();
		for (int i = 0; i < b.size(); i++) {
		    world.getSystem(PositionSystem.class).resetPos(b.get(i));
		}
	  }
    }

    private class InitOnTimeThread extends Thread {
        public int t;
        public Awaitable aw;

        public InitOnTimeThread() {
            setName("ObjTimeCalcSys :: RELOAD");
        }

        @Override
        public void run() {
            Gdx.app.debug(tag + " :: LoadThread", "Started entity preprocessing");

            Gdx.app.debug(tag + " :: LoadThread", "Finished");
            aw.ready();

        }
    }

    /*
        Online Entity processing
     */



}
