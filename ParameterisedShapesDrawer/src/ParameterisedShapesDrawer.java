// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2018T1, Assignment 3
 * Name: Matthew Corfiatis
 * Username: CorfiaMatt
 * ID: 300447277
 */


import ecs100.*;

import java.awt.*;

/** ParameterisedShapesDrawer: draws a series of parametretised shapes */
public class ParameterisedShapesDrawer{

    public static final double WIDTH = 200;
    public static final double HEIGHT = 133;

    private static FontMetrics fMetrics;

    public static final Flag[] flags = new Flag[]{
            new Flag("France", true, CHex("#230095"), Color.white, CHex("#E62D39")),
            new Flag("Italy", true, CHex("#1E9445"), Color.white, CHex("#C82D37")),
            new Flag("Ireland", true, CHex("#289D61"), Color.white, CHex("#F88C3D")),
            new Flag("Germany", false, Color.black, CHex("#D61600"), CHex("#F9D300")),
            new Flag("Lithuania", false, CHex("#F6BE00"), CHex("#186B43"), CHex("#BB2A2D")),
            new Flag("Luxembourg", false, CHex("#E62D39"), Color.white, CHex("#3A9CDE")),
            new Flag("Belgium", true, Color.black, CHex("#FAE042"), CHex("#ED2939")),
            new Flag("Nigeria", true, CHex("#008651"), Color.white, CHex("#008651")),
            new Flag("Armenia", true, CHex("#D21511"), CHex("#261FA0"), CHex("#EBAC00")),
    };

    /**   CORE
     * draws multiple flags made up of three equal size stripes by calling the
     * drawThreeColourFlag method, passing the appropriate arguments
     */
    public void drawMultipleFlags(){
        for(int i = 0; i < 3; i++)
            drawThreeColourFlag(((WIDTH + 20) * i) + 50, 50, WIDTH, HEIGHT, flags[i]);
    }

    /**   CORE
     * draws a three colour flag consisting of three equal-size stripes
     * at the given position
     * The stripes can be either vertical or horizontal
     */
    public void drawThreeColourFlag(double left, double top, double width, double height, Color[] colors, boolean vertical){
        for(int i = 0; i < 3; i++)
        {
            UI.setColor(colors[i]);
            if(vertical)
                UI.fillRect(left + (width / 3 * i), top, width / 3, height);
            else
                UI.fillRect(left, top + (height / 3 * i), width, height / 3);
        }
        UI.setColor(Color.black);
        UI.drawRect(left, top, width, height);
    }

    public void drawThreeColourFlag(double left, double top, double width, double height, Flag flag)
    {
        drawThreeColourFlag(left, top, width, height, flag.Colors, flag.Vertical);
    }

    /**   COMPLETION
     * print a 3x3 grid of nine different countries flag with their names
     *     centred below them
     * The grid must have both vertical and horizontal striped flags.
     */
    public void drawFlagGrid(){
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawThreeColourFlag(((WIDTH + 30)  * i) + 50, ((HEIGHT + 30)  * j) + 50, WIDTH, HEIGHT, flags[i + (3 * j)]);
                String name = flags[i + (3 * j)].Name;
                double xOffset = (WIDTH / 2) - (StrWidth(name) / 2);
                UI.setColor(Color.black);
                UI.drawString(name, ((WIDTH + 30)  * i) + 50 + xOffset, ((HEIGHT + 30)  * (j + 1)) + 35);
            }
        }
    }

    public static void drawTile(double x, double y,
                                double width, double height,
                                Color col1, Color col2,
                                Boolean cross, Boolean rotate90)
    {

    }

    private static Color CHex(String hex)
    {
        return Color.decode(hex);
    }

    private static int StrWidth(String txt)
    {
        if(fMetrics == null)
            fMetrics =  UI.getGraphics().getFontMetrics();
        return fMetrics.stringWidth(txt);
    }
}


