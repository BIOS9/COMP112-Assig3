// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2018T1, Assignment 3
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

import java.awt.*;
import java.util.*;

/** The program contains several methods for analysing the readings of the temperature levels over the course of a day.
 *  There are several things about the temperature levels that a user may be interested in: 
 *    The average temperature level.
 *    How the temperatures rose and fell over the day.
 *    The maximum and the minimum temperature levels during the day.
 */
public class TemperatureAnalyser{

    static final double GRAPH_HEIGHT = 600;
    static final double GRAPH_WIDTH = 800;
    static final double GRAPH_X = 50;
    static final double GRAPH_Y = 50;
    private static FontMetrics fMetrics;

    /* analyse reads a sequence of temperature levels from the user and prints out
     *    average, maximum, and minimum level and plots all the levels
     *    by calling appropriate methods
     */
    public void analyse(){
        UI.clearPanes();
        ArrayList<Double> listOfNumbers = UI.askNumbers("Enter levels, end with 'done': ");
        if (listOfNumbers.size() != 0) {
            this.printAverage(listOfNumbers);
            this.plotLevels(listOfNumbers);

            UI.printf("Maximum level was:  %f\n", this.maximumOfList(listOfNumbers));
            UI.printf("Minimum level was:  %f\n", this.minimumOfList(listOfNumbers));
        }
        else {
            UI.println("No readings");
        }
    }

    /** Print the average level
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the sum, which 
     *     needs to be initialised to an appropriate value.
     *  CORE
     */
    public void printAverage(ArrayList<Double> listOfNumbers) {
        UI.printf("The average temperature is: %.2f\u00b0%n", Mean(listOfNumbers));
    }

    private double Mean(ArrayList<Double> listOfNumbers)
    {
        double sum = 0;
        for(Double d : listOfNumbers)
            sum += d;
        sum /= listOfNumbers.size();
        return sum;
    }

    /**
     * Plot a bar graph of the sequence of levels,
     * using narrow rectangles whose heights are equal to the level.
     * [Core]
     *   - Draws a horizontal line for the x-axis (or baseline) without any labels.
     *   - Any level greater than 400 should be plotted as if it were just 400, putting an
     *         asterisk ("*") above it to show that it has been cut off.
     * [Completion]
     *   - The graph should also have labels on the axes, roughly every 50 pixels.
     *   - The graph should also draw negative temperature levels correctly.
     *   - Scale the y-axis and the bars so that the largest numbers and the smallest just fit on the graph.
     *     The numbers on the y axis should reflect the scaling.
     *   - Scale the x-axis so that all the bars fit in the window.
     * [Challenge]
     *   - Display labels 9the value) on the bar. The number should be in white, centered in the bar.
     */
    public void plotLevels(ArrayList<Double> listOfNumbers) {
        MedianReturn tempMed = Median(listOfNumbers);
        double median = tempMed.Median;
        boolean exactMedian = !tempMed.AveragedMedian;
        double closestMedian = 0;
        double mean = Mean(listOfNumbers);

        UI.println("Median value was: " + median);

        if(!exactMedian) {
            closestMedian = Closest(median, listOfNumbers);
            UI.println("Value closest to median was: " + closestMedian);
        }

        double stdDev = RMS(listOfNumbers);
        UI.println("Standard deviation value was: " + stdDev);

        double graphHeight = GRAPH_HEIGHT / 2;

        double max = maximumOfList(listOfNumbers);
        double min = minimumOfList(listOfNumbers);

        max = (max > 400) ? 400 : max;
        min = (min < -400) ? -400 : min;

        int count = listOfNumbers.size();
        double xUnit = GRAPH_WIDTH / count;
        double yUnit = graphHeight / Math.max(Math.abs(max), Math.abs(min));

        for(Double i = -max; i <= max; i++)
        {
            if(i % 50 == 0) {
                String txt = String.valueOf(i.intValue());
                UI.drawString(txt, GRAPH_X - StrWidth(txt) - 10, (GRAPH_HEIGHT / 2) + GRAPH_Y - (i * yUnit));
            }
        }

        for(int i = 0; i < count; i++)
        {
            double value = listOfNumbers.get(i);
            boolean truncated = false;
            if(value > 400) {
                value = 400;
                truncated = true;
            }
            else if(value < -400)
            {
                value = -400;
                truncated = true;
            }

            String num = listOfNumbers.get(i).toString();
            double barY = 0;
            double barH = 0;
            double truncY = 0;

            if(value > 0)
            {
                barY = (yUnit * max) + GRAPH_Y - (value * yUnit);
                barH = (yUnit * max) - barY + GRAPH_Y;
                truncY = barY - 5;
            }
            else
            {
                barY = (yUnit * max) + GRAPH_Y;
                barH = -(value * yUnit);
                truncY = barY + barH + 15;
            }

            if(listOfNumbers.get(i) == median)
                UI.setColor(Color.blue);
            else if(listOfNumbers.get(i) == closestMedian)
                UI.setColor(Color.green.darker());
            else
                UI.setColor(Color.black);
            UI.fillRect(
                    (i * xUnit) + GRAPH_X,
                    barY,
                    xUnit * 0.9,
                    barH);

            UI.drawString(String.valueOf(i), (i * xUnit) + GRAPH_X + (xUnit * 0.45) - (StrWidth(String.valueOf(i)) / 2), GRAPH_HEIGHT + GRAPH_Y + 25);

            UI.setColor(Color.orange.darker());
            double meanY = (yUnit * max) + GRAPH_Y - (mean * yUnit);
            UI.drawLine(GRAPH_X,
                    meanY,
                    GRAPH_X + GRAPH_WIDTH,
                    meanY);
            UI.drawString("Average", GRAPH_X + GRAPH_WIDTH + 10, meanY);

            double RMSY = (yUnit * max) + GRAPH_Y - (stdDev * yUnit);
            UI.setColor(Color.red.darker());
            UI.drawLine(GRAPH_X,
                    meanY + RMSY,
                    GRAPH_X + GRAPH_WIDTH,
                    meanY + RMSY);
            UI.drawString("RMS/Std Deviation", GRAPH_X + GRAPH_WIDTH + 10, meanY + RMSY);
            UI.drawLine(GRAPH_X,
                    meanY - RMSY,
                    GRAPH_X + GRAPH_WIDTH,
                    meanY - RMSY);
            UI.drawString("RMS/Std Deviation", GRAPH_X + GRAPH_WIDTH + 10, meanY - RMSY);

            double labelY = barY + (barH / 2) + (FontHeight() / 2);
            double labelX = (i * xUnit) + GRAPH_X + (xUnit * 0.45) - (StrWidth(num) / 2);

            UI.setColor(Color.white);
            UI.drawString(num, labelX, labelY);

            if(truncated) {
                labelX = (i * xUnit) + GRAPH_X + (xUnit * 0.45) - (StrWidth("*") / 2);
                UI.setColor(Color.black);
                UI.drawString("*", labelX, truncY);
            }
        }

        UI.setColor(Color.red);
        UI.drawRect(GRAPH_X, GRAPH_Y, GRAPH_WIDTH, GRAPH_HEIGHT);
        UI.println("Finished plotting");
    }

    /** Find and return the maximum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the maximum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double maximumOfList(ArrayList<Double> listOfNumbers) {
        double max = Double.MIN_VALUE;
        for(double d : listOfNumbers)
            if(max < d) max = d;
        return max;
    }

    /** Find and return the minimum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the minimum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double minimumOfList(ArrayList<Double> listOfNumbers) {
        double min = Double.MAX_VALUE;
        for(double d : listOfNumbers)
            if(min > d) min = d;
        return min;
    }

    class MedianReturn
    {
        public double Median;
        public boolean AveragedMedian;

        public MedianReturn(double m, boolean am)
        {
            Median = m;
            AveragedMedian = am;
        }
    }

    private MedianReturn Median(ArrayList<Double> listOfNumbers) {
        ArrayList<Double> nums = new ArrayList<>(listOfNumbers);
        Collections.sort(nums);
        if(nums.size() % 2 == 0)
            return new MedianReturn((nums.get(nums.size() / 2) + nums.get((nums.size() / 2) + 1)) / 2, true);
        else
            return new MedianReturn(nums.get((nums.size() / 2) + 1), false);
    }

    private double RMS(ArrayList<Double> listOfNumbers)
    {
        double mean = Mean(listOfNumbers);
        double MS = 0;
        for(double d : listOfNumbers)
            MS += Math.pow(d - mean, 2);
        return Math.sqrt(MS / listOfNumbers.size());
    }

    private double Closest(double val, ArrayList<Double> listOfNumbers)
    {
        double closest = 0;
        double difference = Double.MAX_VALUE;
        for(double d : listOfNumbers)
            if(Math.abs(val - d) < difference)
            {
                difference = Math.abs(val - d);
                closest = d;
            }
        return closest;
    }

    private static int StrWidth(String txt)
    {
        if(fMetrics == null)
            fMetrics =  UI.getGraphics().getFontMetrics();
        return fMetrics.stringWidth(txt);
    }

    private static int FontHeight()
    {
        if(fMetrics == null)
            fMetrics =  UI.getGraphics().getFontMetrics();
        return fMetrics.getFont().getSize();
    }
}
