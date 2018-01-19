package com.draniksoft.ome.editor.support.ems.base_em;

import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.support.actions.mapO.CreateNewMOA;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.ems.core.SimpleEditMode;
import com.draniksoft.ome.editor.support.input.base_mo.NewMOIC;
import com.draniksoft.ome.editor.support.render.base_mo.NewMORenderer;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.support.ui.util.WindowAgent;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.draniksoft.ome.utils.ESCUtils;
import com.draniksoft.ome.utils.FUtills;

public class NewMOEM extends SimpleEditMode {


    NewMOIC newLIC;
    NewMORenderer newRdr;

    @Override
    protected void on_attached() {

        newLIC = new NewMOIC();
        newRdr = new NewMORenderer();

        newLIC.setOwnerMode(this);
        newLIC.setRdr(newRdr);

	  defalteEnv();

	  ESCUtils.clearSelected(_w);

        _w.getSystem(InputSys.class).setMainIC(newLIC);
        _w.getSystem(OverlayRenderSys.class).addRdr(newRdr);

	  _w.getSystem(UiSystem.class).openWin("new_mo_em", new WindowAgent() {

		@Override
		public <T extends BaseWinView> void opened(T vw) {

		}


		@Override
		public void notifyClosing() {

		}

		@Override
		public void closed() {

		}
	  });
    }

    @Override
    public void update() {

    }

    @Override
    public void detached() {

	  _w.getSystem(InputSys.class).clearMainIC();
	  returnEnv();

    }



    public void createLoc(Vector2 tmp) {

	  CreateNewMOA a = new CreateNewMOA(tmp.x, tmp.y, 50, 50, FUtills.DrawablePrefix.P_SIMPLE_DW + "i_casB@mapTile@100");

        _w.getSystem(ActionSystem.class).exec(a);

        _w.getSystem(EditorSystem.class).detachEditMode();

    }


    // called when child is interrupted/detached
    public void inputStopped() {


        newLIC.ignoreDestruct = true;

        _w.getSystem(EditorSystem.class).detachEditMode();


    }

    @Override
    public int ID() {
        return EditModeDesc.IDS.newMO;
    }
}
