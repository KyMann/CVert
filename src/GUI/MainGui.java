package GUI;

import GUI.actions.ButtonAction;

import javax.swing.*; //make sure that all components are from swing, awt components are non compatible

import java.awt.*; //some objects, listiners, and actions can come from awt
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Kyle on 4/21/2017.
 */
public class MainGui extends JApplet implements Runnable, DropTargetListener {
    //#Applets go into something normally a web page, Applications are desktop
    //#runnable guarantees it can be ran

    //--internal variables
    private  static final long serialVersionUID = 1L;
    private static final Dimension SCREENSIZE = new Dimension(500, 400);
    //#this object just keeps the window height
    private static final Dimension FRAMESIZE = new Dimension(400,300);
    //#this is for games - makes game 1/2 the size - smooths out movment
    private static JFrame frame;
    public static boolean isRunning = true; //to start the loop
    private static final String TITLE = "CVert";
    private DataFlavor targetFlavor; //flavor to use for transfer - can be adjusted?
     //TODO: expand DataFlavor into multiple types, currently image files
    protected boolean acceptableType; //indicates when data is acceptable

    //--component variables
    private JTextArea dragAndDrop;
    private JTextArea status;
    private JPanel pane = new JPanel(); //normally fed in to constructor, but I have no class to feed in from



    //--constructor
    public MainGui() throws HeadlessException{ //?what does headlessException do?
        super();
        DropTarget dropTarget = new DropTarget(pane, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
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

        frame = new JFrame();
        this.dragAndDrop = new JTextArea();
        this.dragAndDrop.setEditable(false);
        dragAndDrop.setSize(FRAMESIZE);
        add(dragAndDrop);
        this.dragAndDrop.setTransferHandler(tf);

    }

    //--these are all methods for dropTargetListener()
    //#most of these actions only run a debug line
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        DnDUtils.debugPrintln("dragEnter, drop action = "
                + DnDUtils.showActions(dtde.getDropAction()));

        checkTransferType(dtde);

        acceptOrRejectDrag(dtde);
    }

    @Override
    public void dragExit(DropTargetEvent dtde) {
        DnDUtils.debugPrintln("DropTarget DragExit");
    }
    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        DnDUtils.debugPrintln("DropTarget dragOver, drop action = "
                + DnDUtils.showActions(dtde.getDropAction()));

        acceptOrRejectDrag(dtde);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        DnDUtils.debugPrintln("DropTarget drop, drop action = " + DnDUtils.showActions(dtde.getDropAction()));

        acceptOrRejectDrag(dtde);
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            System.out.println("in drop");
            Transferable tr = dtde.getTransferable();

            DataFlavor[] flavors = tr.getTransferDataFlavors();
            System.out.println(flavors.length);
            for (int i=0; i < flavors.length; i++) {
                if (flavors[i].isFlavorJavaFileListType()) {
                    System.out.println("In if");
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    ArrayList list = (ArrayList) tr.getTransferData(flavors[i]);
                    for (int j=0; j < list.size(); j++) {
                        dragAndDrop.append(list.get(j) + "\n");
                    }
                    dtde.dropComplete(true);
                    return;
                }
                else if (flavors[i].isFlavorSerializedObjectType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    Object o = tr.getTransferData(flavors[i]);
                    dragAndDrop.append("Object:" + o.getClass());
                    dtde.dropComplete(true);
                    return;
                }
                else if (flavors[i].isRepresentationClassInputStream()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    dragAndDrop.read(new InputStreamReader((InputStream) tr.getTransferData(flavors[i])), "from system clipboard");
                    dtde.dropComplete(true);
                    return;
                }
            }
            dtde.rejectDrop();
        }
        catch (Exception e) {
            e.printStackTrace();
            dtde.rejectDrop();
        }
        //this block of drop code behaves similar to the one above, but without utilizing DnDUtils - but is already capable of accepting multiple data flavors
        if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
            //accept the drop and get transfer data
            dtde.acceptDrop(dtde.getDropAction());
            Transferable transferable = dtde.getTransferable();

            try {
                boolean result = dropComponent(transferable);

                dtde.dropComplete(result);
                DnDUtils.debugPrintln("Drop completed, success: "
                        + result);
            } catch (Exception e) {
                DnDUtils.debugPrintln("Exception while handling drop "
                        + e);
                dtde.dropComplete(false);
            }
        } else {
            DnDUtils.debugPrintln("drop target rejected drop");
            dtde.rejectDrop();
        }

    }

    //--internal methods for runnable
    public static void main(String[] args) {
        //applets that keep a frame need a main?
        MainGui main = new MainGui();
        main.setPreferredSize(SCREENSIZE);//?this is the one that is seting the drop window to the same size as the frame, and without it they both disappear...
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

        //--add them to the contentPane

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(status);
        contentPane.add(formatDropDown);
        contentPane.add(submitButton);

        //--assign actions - actions should be assigned so that they can be used in multiple places
        ButtonAction buttonAction = new ButtonAction();
        submitButton.addActionListener(buttonAction);
    }

    public void run() {
        while(isRunning) {
            //tick();
            //#games tend to use loops, applications are more event based - with each action spawning another event
            //try {Thread.sleep(50);} //this spaces our our refresh rate, otherwise we'd refresh as quickly as possible
            //catch(InterruptedException ie) {} //nothing can interrupt it yet
        }
    }

    public void stop() {
        isRunning = false;
    }

    //--methods to enable dropTargetListener file flavor verification
    protected boolean acceptOrRejectDrag(DropTargetDragEvent dtde) {
        int dropAction = dtde.getDropAction();
        int sourceActions = dtde.getSourceActions();
        boolean acceptedDrag = false;

        DnDUtils.debugPrintln("\tSource actions are "
                + DnDUtils.showActions(sourceActions)
                + ", drop action is "
                + DnDUtils.showActions(dropAction));

        //reject if object is transfered or the operations are inacceptable
        if(!acceptableType ||(sourceActions&DnDConstants.ACTION_COPY_OR_MOVE) ==0) {
            DnDUtils.debugPrintln("Drop target rejecting drag");
            dtde.rejectDrag();
        } else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
            //not offering copy or move - suggest a copy
            DnDUtils.debugPrintln("Drop target ovvering COPY");
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
            acceptedDrag = true;
        } else {
            //offering an acceptable operation: accept
            DnDUtils.debugPrintln("drop target accepting drag");
            dtde.acceptDrag(dropAction);
            acceptedDrag = true;
        }

        return acceptedDrag;
    }

    protected void checkTransferType(DropTargetDragEvent dtde) {
        //only accept a flavor that returns a Component
        boolean acceptableType = false;
        DataFlavor[] fl = dtde.getCurrentDataFlavors();
        for (int i = 0; i < fl.length; i++) {
            Class dataClass = fl[i].getRepresentationClass();

            if (Component.class.isAssignableFrom(dataClass)) {
                //this flavor returns a component - accept it.
                targetFlavor = fl[i];
                acceptableType = true;
                break;
            }
        }
        DnDUtils.debugPrintln("File type acceptable - " + acceptableType);
    }

    protected boolean dropComponent(Transferable transferable) throws IOException, UnsupportedFlavorException {
        Object o = transferable.getTransferData(targetFlavor);
        if (o instanceof  Component) {
            DnDUtils.debugPrintln("Dragged component class is "
                    + o.getClass().getName());
            pane.add((Component) o);
            pane.validate();
            return true;
        }
        return false;
    }


}
