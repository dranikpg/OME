package com.draniksoft.ome.editor.esc_utils;

import com.artemis.BaseSystem;
import com.artemis.InvocationStrategy;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.draniksoft.ome.support.load.IntelligentLoader;

import java.util.HashMap;

public class OmeStrategy extends InvocationStrategy {

    private static final String tag = "OmeStrategy";

    public static int MS_PERF_TREESHOLD = 1;
    GLProfiler pf;


    @Wire(name = "engine_l")
    IntelligentLoader l;

    HashMap<Integer, Integer> glCapM;
    HashMap<Class, Integer> sysT;

    boolean profiling = false;
    boolean __profiling = false;
    boolean onePass = true;

    boolean fpsNotific = true;

    int frameC = 5;
    int frame_c = frameC;


    @Override
    protected void initialize() {
	  glCapM = new HashMap<Integer, Integer>();
	  sysT = new HashMap<Class, Integer>();
    }

    public HashMap<Class, Integer> getSysT() {
	  return sysT;
    }

    public void fetchUpdtProfileD() {
	  profiling = true;
    }

    public void constProfile() {
	  profiling = true;
	  onePass = false;
    }

    public void stopConstProf() {
	  profiling = false;
	  onePass = true;
    }

    public boolean isProfiling() {
	  return profiling;
    }

    public boolean isConstProfiling() {
	  return profiling & !onePass;
    }

    public int getGLProfileP(int code) {
	  if (!glCapM.containsKey(code)) return -1;
	  return glCapM.get(code);
    }

    @Override
    protected void process() {

	  if (pf == null) pf = new GLProfiler(Gdx.graphics);

	  if (profiling) __profiling = true;

	  if (profiling) {
		pf.enable();
	  } else {
		pf.disable();
	  }

	  if (__profiling) {
		sysT.clear();
	  }


        BaseSystem[] systemsData = systems.getData();

        for (int i = 0, s = systems.size(); s > i; i++) {
            if (disabled.get(i))
                continue;

            updateEntityStates();

		long ms = 0;
		if (__profiling) ms = System.currentTimeMillis();

            systemsData[i].process();

		if (__profiling) {
		    long dif = System.currentTimeMillis() - ms;
		    if (dif > MS_PERF_TREESHOLD) {
			  sysT.put(systemsData[i].getClass(), (int) dif);
		    }
		}
	  }

        updateEntityStates();

	  if (__profiling) {
		glCapM.put(GL_PROFILE_PROP.GL_CLASS, pf.getCalls());
		glCapM.put(GL_PROFILE_PROP.DRAW_CALLS, pf.getDrawCalls());
		glCapM.put(GL_PROFILE_PROP.TEXS_BIND, pf.getTextureBindings());
		glCapM.put(GL_PROFILE_PROP.SHADER_SWITCHES, pf.getShaderSwitches());
	  }

	  if (profiling) pf.reset();

	  if (__profiling && onePass) profiling = false;

    }

    public static class GL_PROFILE_PROP {
	  public static final int DRAW_CALLS = 1;
	  public static final int GL_CLASS = 2;
	  public static final int TEXS_BIND = 3;
	  public static final int SHADER_SWITCHES = 4;

    }
}
