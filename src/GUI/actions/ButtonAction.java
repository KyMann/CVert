package GUI.actions;

import GUI.Formats;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Kyle on 4/21/2017.
 */
public class ButtonAction implements ActionListener, ItemListener{


    private TextField out;


    public ButtonAction() {}

    @Override
    public void actionPerformed(ActionEvent ae) {
        //TODO: allow access to MainGui state - see destination format
        //TODO: use more libraries to convert files
        //TODO: enable status bar suport


        int n;
        try{n = Integer.parseInt(ae.getActionCommand());
        } catch(NumberFormatException e){
            System.out.print("Button Pressed");
            n=1;
        }

        Toolkit tk = Toolkit.getDefaultToolkit();
        for (int i=0 ; i< n; i++) {tk.beep();}

    }

    public void itemStateChanged(ItemEvent ie) {

    }
}
