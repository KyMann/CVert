package GUI.actions;

import GUI.Formats;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Kyle on 4/21/2017.
 */
public class ButtonAction implements ActionListener, ItemListener{

    private static int count;

    private TextField out;


    public ButtonAction() {count = 0;}

    @Override
    public void actionPerformed(ActionEvent ae) {

        count++;

        int n;
        try{n = Integer.parseInt(ae.getActionCommand());}
        catch(NumberFormatException e){

            System.out.print(count);

            n=1;}

        Toolkit tk = Toolkit.getDefaultToolkit();
        for (int i=0 ; i< n; i++) {tk.beep();}

    }

    public void itemStateChanged(ItemEvent ie) {
        if (ie.getStateChange() == ItemEvent.SELECTED) {
            String selectedFormat = (String) ie.getItem();
            Toolkit tk = Toolkit.getDefaultToolkit();
            try {
                for (Formats format : Formats.values()) {
                    if (format.getNumber() == ItemEvent.SELECTED ) {

                    }
                }

            }
            catch(Exception e) {
                tk.beep();
            }
        }
    }

}
