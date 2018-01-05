package com.draniksoft.ome.utils.cam;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;

public abstract class Target {

    public abstract void getPos(Vector2 out);

    public boolean freeOnReach = true;
    public boolean locked = false;
    public boolean dieOnDrag = true;

    public float treesHold = 10;
    public float alpha = 0.05f;

    public static final class PosTarget extends Target {

	  public Vector2 coords;

	  @Override
	  public void getPos(Vector2 out) {
		out.set(coords);
	  }
    }


    public static final class EntityPosTarget extends Target {

	  public int _e;
	  public PositionSystem ps;

	  @Override
	  public void getPos(Vector2 out) {

		ps.getCenterPosV(_e, out);

	  }


    }

    public static final class EntityPosWINCALCTarget extends Target {

	  public int _e;
	  public PositionSystem ps;
	  public UiSystem sys;

	  Viewport vpU;
	  Viewport vpW;

	  Vector2 tmpV = new Vector2();

	  public EntityPosWINCALCTarget(World w) {
		freeOnReach = false;
		ps = w.getSystem(PositionSystem.class);
		sys = w.getSystem(UiSystem.class);
		vpU = w.getInjector().getRegistered("ui_vp");
		vpW = w.getInjector().getRegistered("game_vp");

	  }

	  @Override
	  public void getPos(Vector2 out) {

		ps.getCenterPosV(_e, out);

	  }


    }

}
