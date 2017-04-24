package GUI;

import GUI.actions.ButtonAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kyle on 4/21/2017.
 */
public class MainGui extends JApplet implements Runnable {
    //#Applets go into something normally a web page, Applications are desktop
    //#runnable guarantees it can be ran

    //--internal variables
    private  static final long serialVersionUID = 1L;
    public static final Dimension SCREENSIZE = new Dimension(800, 600);
    //#this object just keeps the window height
    public static final Dimension FRAMESIZE = new Dimension(400,300);
    //#this is for games - makes game 1/2 the size - smooths out movment
    public static JFrame frame;
    public static boolean isRunning = true; //to start the loop
    public static final String TITLE = "CVert";


    //--component variables
    public JTextArea dragAndDrop;
    private TextArea status;
    public Image screen;

    public MainGui() {
        setPreferredSize(SCREENSIZE); //#here we add size to object
        frame = new JFrame();
    }

    public static void main(String[] args) {
        //?I'm not certain that applets need a main?
        MainGui main = new MainGui();
        //frame.setSize(SCREENSIZE); // size should be passed in with object
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.add(main); //#here we add the object we're making to the applet
        frame.pack(); //#pack makes size fit the contents we passed in
        frame.setVisible(true);
        main.start();
    }

    public void start() {
        init();
        new Thread(this).start(); //#because this implements Runnable, this runs it
    }

    public void init() {

        //--construct fields
        this.dragAndDrop = new JTextArea("", 30,40 );
        this.dragAndDrop.setEditable(false);
        this.status = new TextArea("Status", 5,40, 3);
        this.status.setEditable(false);
        Choice formatDropDown = new Choice();
        for (Formats format : Formats.values()) {
            formatDropDown.addItem(format.name());
        }
        Button submitButton = new Button("Convert");

        //--add them to the this. - the layout?
        this.add(dragAndDrop);
        this.add(status);
        add(formatDropDown);
        this.add(submitButton);

        //--assign actions - actions should be assigned so that they can be used in multiple places
        ButtonAction buttonAction = new ButtonAction();
        submitButton.addActionListener(buttonAction);


        screen = createVolatileImage(FRAMESIZE.width, FRAMESIZE.height); //#VolatileImage means the image only exists in RAM (as opposed to HDD)
    }


    public void run() {

        //init(); //? Does run normally call back to init? or is that just a game thing?
        while(isRunning) {
            //tick();
            //render();
            //#games tend to use loops, applications are more event based - with each action spawning another event
            //try {Thread.sleep(50);} //this spaces our our refresh rate, otherwise we'd refresh as quickly as possible
            //catch(InterruptedException ie) {} //nothing can interrupt it yet
        }
    }

    public void stop() {
        isRunning = false;
    }
}
