package plot;

import functions.GeneticAlgorithm;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.List;

import org.knowm.xchart.*;

import java.util.List;
import java.util.ArrayList;

public class Graphing {

    public void plotGraph(double[] functionValues, List<List<GeneticAlgorithm.Point>> allEvaluatedPoints, String title, String xAxisTitle, String yAxisTitle) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle).build();

        // Plot function values
        double[] xData = new double[functionValues.length];
        for (int i = 0; i < functionValues.length; i++) {
            xData[i] = i;
        }
        chart.addSeries("Function", xData, functionValues).setMarker(SeriesMarkers.NONE);

        // Plot evaluated points for each generation
        for (int gen = 0; gen < allEvaluatedPoints.size(); gen++) {
            List<GeneticAlgorithm.Point> points = allEvaluatedPoints.get(gen);
            double[] xPoints = new double[points.size()];
            double[] yPoints = new double[points.size()];
            for (int i = 0; i < points.size(); i++) {
                xPoints[i] = points.get(i).getX();
                yPoints[i] = points.get(i).getY();
            }
            chart.addSeries("Evaluated Points Gen " + (gen + 1), xPoints, yPoints).setMarker(SeriesMarkers.CIRCLE);
        }

        new SwingWrapper<>(chart).displayChart();
    }
}
