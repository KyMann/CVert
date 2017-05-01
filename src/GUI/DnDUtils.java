package GUI;

import java.awt.dnd.DnDConstants;

/**
 * Created by Kyle on 4/30/2017.
 */


class DnDUtils {
//this is a class from www.java2s.com/Code/Java/Swing-JFC/PanelDropTarget.html
    //this class just turns action integers that dtde's contain into their English equilivant for Debugging statements
    public static String showActions(int action) {
        String actions = "";
        if ((action & (DnDConstants.ACTION_LINK | DnDConstants.ACTION_COPY_OR_MOVE)) == 0) {
            return "None";
        }
        if ((action & DnDConstants.ACTION_COPY) != 0) {
            actions += "Copy ";
        }
        if ((action & DnDConstants.ACTION_MOVE) != 0) {
            actions += "Move ";
        }
        if ((action & DnDConstants.ACTION_LINK) != 0) {
            actions += "Link";
        }

        return actions;
    }

    public static boolean isDebugEnabled() {return debugEnabled;}

    public static void debugPrintln(String debugString) {
        if (debugEnabled) {
            System.out.println(debugString);
        }
    }

    private static boolean debugEnabled = (System.getProperty("DnDExamples.debug") != null);
}