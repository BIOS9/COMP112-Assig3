// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2018T1, Assignment 3
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

public class RunTemperatureAnalyser{

    public static void main(String[] arguments) {
        TemperatureAnalyser ta = new TemperatureAnalyser();
        UI.initialise();
        UI.addButton("Analyse", ta::analyse );
        UI.addButton("Quit", UI::quit );
    }
}
