package com.draniksoft.ome.editor.systems.render.obj;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.draniksoft.ome.editor.components.path.PathRenderC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.support.container.path.PathRTDesc;
import com.kotcrab.vis.ui.VisUI;

public class PathRenderSys extends IteratingSystem {

    private static final String tag = "PathRenderSys";

    public PathRenderSys() {
	  super(Aspect.all(PathRunTimeC.class, PathRenderC.class));
    }

    @Wire
    ShapeRenderer r;

    @Wire(name = "game_cam")
    OrthographicCamera cam;

    ComponentMapper<PathRunTimeC> rcM;

    @Override
    protected void begin() {
	  r.setProjectionMatrix(cam.combined);
	  r.setColor(VisUI.getSkin().getColor("primary"));
	  r.setAutoShapeType(true);
	  r.begin();
    }

    @Override
    protected void process(int e) {

	  PathRunTimeC c = rcM.get(e);

	  r.set(ShapeRenderer.ShapeType.Filled);

	  for (PathRTDesc d : c.p) {

		if (d == null || !d.rendering || d.ar.size < 2) continue;

		for (int i = 1; i < d.ar.size; i++) {

		    r.rectLine(d.ar.get(i - 1), d.ar.get(i), 10);

		}

	  }
    }

    @Override
    protected void end() {
	  r.end();
    }
}
