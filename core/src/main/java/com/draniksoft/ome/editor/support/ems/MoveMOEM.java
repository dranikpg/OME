package com.draniksoft.ome.editor.support.ems;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.support.ems.core.EditMode;
import com.draniksoft.ome.editor.support.input.MoveMOIC;
import com.draniksoft.ome.editor.support.input.back.StebIC;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.support.ui.util.WindowAgent;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import net.mostlyoriginal.api.event.common.EventSystem;

public class MoveMOEM implements EditMode {

    public final String tag = "MoveMOEM";

    MoveMOIC ic;

    World _w;
    int e = -1;

    boolean easyQ = false;

    @Override
    public void attached(World _w) {
        this._w = _w;

        ic = new MoveMOIC();


        e = _w.getSystem(EditorSystem.class).sel;

        if (e < 0) {
            Gdx.app.debug(tag, "Selection is null -> detaching");
            easyQ = true;
            _w.getSystem(EditorSystem.class).detachEditMode();
            return;
        }


        ic.setE(e);
        ic.setEm(this);


        _w.getSystem(OverlayRenderSys.class).removeRdrByPlace(new int[]{}, new int[]{OverlayPlaces.ENTITY_MAIN_BODY});
        _w.getSystem(InputSys.class).setMainIC(ic);
	  _w.getSystem(InputSys.class).setDefIC(new StebIC());

        _w.getSystem(EventSystem.class).registerEvents(this);

	  _w.getSystem(UiSystem.class).createBK();

	  _w.getSystem(UiSystem.class).openWin("move_mo_em", new WindowAgent() {

		@Override
		public void opened(BaseWinView vw) {

		}

		@Override
		public void closed() {

		}

	  });

    }

    @Override
    public void update() {


    }

    public void keyPressed(int t) {


        if (t == 0) selfDestroy();

        if (t == 1) {
            save();
        }

    }

    private void save() {

        Gdx.app.debug(tag, "Committing changes");

        _w.getSystem(EditorSystem.class).detachEditMode();

    }

    private void selfDestroy() {

        ic.setReactOnKill(false);
        _w.getSystem(EditorSystem.class).detachEditMode();

    }

    @Override
    public void detached() {

        if (easyQ) return;

	  _w.getSystem(UiSystem.class).inflateBK();
	  _w.getSystem(InputSys.class).restoreDef();

        _w.getSystem(InputSys.class).clearMainIC();

    }

    /*
    public void selChanged(SelectionChangeE time_e){
        selfDestroy();
    }
    */
    public void childKilled() {

        ic.setReactOnKill(true);
        _w.getSystem(EditorSystem.class).detachEditMode();

    }


}
