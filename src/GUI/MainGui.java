package GUI;

import GUI.actions.ButtonAction;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kyle on 4/21/2017.
 */
public class MainGui extends JApplet implements Runnable, DropTargetListener {
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
    private JTextArea status;
    //public Image screen;


    //constructor
    public MainGui() throws HeadlessException{ //?what does headlessException do?
        super();
        TransferHandler tf = new TransferHandler() {

            @Override
            public boolean canImport(TransferSupport arguments) { //? To Do: what do all these Functions do?
                for (DataFlavor df: arguments.getDataFlavors() ) {
                    if (df.isFlavorJavaFileListType() ) {
                        return true;
                    }
                }
                //else it's not a file
                return false;
            }

            @Override
            public boolean importData(TransferSupport arguments) {
                try {
                    @SuppressWarnings("unchecked")
                        ArrayList<File> fileList = (ArrayList<File>) arguments.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                        //now we check that there aren't multiple files selected
                        if (fileList.size()== 1) {
                            File myFile = fileList.get(0);
                            dragAndDrop.setText(myFile.toString());
                        }
                }
                catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return super.importData(arguments);
            }
        };

        //-------
        setPreferredSize(SCREENSIZE); //#here we add size to object
        frame = new JFrame();
        this.dragAndDrop = new JTextArea();
        this.dragAndDrop.setEditable(false);
        add(dragAndDrop);
        this.dragAndDrop.setTransferHandler(tf);
    }

    public static void main(String[] args) {
        //?I'm not certain that applets need a main?
        MainGui main = new MainGui();
        frame.setSize(SCREENSIZE); // size should be passed in with object
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.getContentPane().add(main); //#here we add the object we're making to the applet
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

        this.status = new JTextArea("Status", 5,40);
        this.status.setEditable(false);
        JComboBox<String> formatDropDown = new JComboBox();
        for (Formats format : Formats.values()) {
            formatDropDown.addItem(format.name());
        }
        JButton submitButton = new JButton("Convert");

        //--add them to the this. - the layout?

        Container containerPane = getContentPane();
        containerPane.add(status);
        containerPane.add(formatDropDown);
        containerPane.add(submitButton);

        //--assign actions - actions should be assigned so that they can be used in multiple places
        ButtonAction buttonAction = new ButtonAction();
        submitButton.addActionListener(buttonAction);


        //screen = createVolatileImage(FRAMESIZE.width, FRAMESIZE.height); //#VolatileImage means the image only exists in RAM (as opposed to HDD)
    }


    public void run() {

        init(); //? Does run normally call back to init? or is that just a game thing?
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

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragExit(DropTargetEvent dte) {

    }

    @Override
    public void drop(DropTargetDropEvent dtde) {

    }
}
