// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2018T1, Assignment 3
 * Name: Matthew Corfiatis
 * Username: CorfiaMatt
 * ID: 300447277
 */

import ecs100.*;

/** Run ParameterisedShapesDrawer methods */
public class RunParameterisedShapesDrawer{

    public static void main(String[] arguments){
        ParameterisedShapesDrawer psd = new ParameterisedShapesDrawer ();
        UI.initialise();
        UI.addButton("Clear", UI::clearGraphics );
        UI.addButton("Multiple flags", psd::drawMultipleFlags );
        UI.addButton("3x3 grid of flags", psd::drawFlagGrid );
        UI.addButton("Quit", UI::quit );
        UI.setWindowSize(1000,600);
        UI.setDivider(0.);
    }
}
