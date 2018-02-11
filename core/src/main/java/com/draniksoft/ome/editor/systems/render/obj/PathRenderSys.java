package com.draniksoft.ome.editor.systems.render.obj;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.components.path.PathRenderC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.struct.path.runtime.Path;
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

	  for (Path p : c.ar) {
		for (Vector2 pt : p.pts) {
		    r.circle(pt.x, pt.y, 5, 10);
		}

	  }

    }

    @Override
    protected void end() {
	  r.end();
    }
}
