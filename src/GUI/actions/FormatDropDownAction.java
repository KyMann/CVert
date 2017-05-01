package GUI.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Kyle on 4/24/2017.
 */
public class FormatDropDownAction implements ActionListener, ItemListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: alter state within MainGUI to match selected
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //TODO:  check files provided in DropArea to ensure they are compatible
        //TODO: add in Status Bar Support
    }
}
