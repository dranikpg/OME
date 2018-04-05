package com.draniksoft.ome.editor.ui.insp;

public class DrawableView  /*extends BaseWinView implements InspView.InspectorManagable */ {
/*
    private static final String tag = "DrawableView";

    @LmlActor("root")
    VisTable root;

    @LmlActor("btn")
    VisImageButton btn;

    int e;

    @Override
    public Actor get() {
	  return root;
    }
    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

	  btn.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    Gdx.app.debug(tag, "TD");
		    if (e < 0) return;
		    final int le = e; // local copy of e, before close

		    _w.getSystem(UiSystem.class).openWin("dwb_edit_vw", new WindowAgent() {
			  @Override
			  public <T extends BaseWinView> void opened(T vw) {
				EditDwbView v = (EditDwbView) vw;
				((EditDwbView) vw).ifor(new EditDwbView.EntityDwbHandler(le));
			  }

			  @Override
			  public void notifyClosing() {
				_w.getSystem(UiSystem.class).openInspector(le);
			  }

			  @Override
			  public void closed() {

			  }
		    });
		}
	  });

    }

    @Override
    public void closed() {
	  super.closed();
	  e = -1;
    }

    @Override
    public void initFor(int e) {
	  this.e = e;
	  DrawableC c = _w.getMapper(DrawableC.class).get(e);
	  btn.getImage().setDrawable(GdxCompatibleDrawable.from(c.d));
    }*/
}
