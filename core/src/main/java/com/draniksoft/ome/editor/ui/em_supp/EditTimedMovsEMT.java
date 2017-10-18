package com.draniksoft.ome.editor.ui.em_supp;

import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.ems.timed.EditTimedMovsEM;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import dint.Dint;

public class EditTimedMovsEMT extends VisTable {

    private static final String tag = "EditTimedMovsEMT";

    SimpleListAdapter<MoveDesc> a;
    ListView<MoveDesc> listV;

    EditTimedMovsEM em;

    Array<MoveDesc> ar;

    public EditTimedMovsEMT(final Array<MoveDesc> ar, final EditTimedMovsEM em) {

        this.em = em;

        a = new SimpleListAdapter<MoveDesc>(ar) {

            @Override
            protected VisTable createView(final MoveDesc item) {
                VisTable t = new VisTable();

                final VisTextField f1 = new VisTextField();
                final VisTextField f2 = new VisTextField();

                f1.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());
                f2.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());

                f1.setText("" + item.time_s);
                f2.setText("" + item.time_e);

                f1.setTextFieldListener(new VisTextField.TextFieldListener() {
                    @Override
                    public void keyTyped(VisTextField textField, char c) {
                        if (c == '\n') getStage().setKeyboardFocus(null);
                        try {

                            save(f1, f2, item);

                        } catch (Exception e) {
                        }
                    }
                });
                f2.setTextFieldListener(new VisTextField.TextFieldListener() {
                    @Override
                    public void keyTyped(VisTextField textField, char c) {
                        if (c == '\n') getStage().setKeyboardFocus(null);
                        try {
                            save(f1, f2, item);
                        } catch (Exception e) {
                        }
                    }
                });


                t.add("Start");
                t.add(f1).padLeft(5);

                t.add("End").padLeft(20);
                t.add(f2).padLeft(5);


                return t;
            }


            private void save(VisTextField f1, VisTextField f2, MoveDesc d) {

                int i1 = Integer.parseInt(f1.getText());
                int i2 = Integer.parseInt(f2.getText());

                if (i1 < 100)
                    d.time_s = Dint.create(Integer.parseInt(f1.getText()), 0, 0);
                else
                    d.time_s = i1;

                if (i2 < 100)
                    d.time_e = Dint.create(Integer.parseInt(f2.getText()), 0, 0);
                else
                    d.time_e = i2;
            }


        };

        listV = new ListView<MoveDesc>(a);

        a.setSelectionMode(AbstractListAdapter.SelectionMode.SINGLE);

        a.setItemClickListener(new ListView.ItemClickListener<MoveDesc>() {
            @Override
            public void clicked(MoveDesc item) {
                em.setSelI(ar.indexOf(item, true));
            }
        });

        add(listV.getMainTable()).expand().fill();

        setSize(300, 300);
    }


    public void renewSel(int s) {

        a.getSelectionManager().deselectAll();

        if (s == -1) return;

        a.getSelectionManager().select(a.get(s));


    }

    public void addedI() {

        a.itemsChanged();

    }


}
