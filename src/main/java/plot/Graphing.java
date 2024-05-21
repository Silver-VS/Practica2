package plot;

import functions.GeneticAlgorithm;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.List;

/**
 * Clase para graficar los resultados del Algoritmo Genético.
 */
public class Graphing {

    /**
     * Grafica los valores de una función y los puntos evaluados por el Algoritmo Genético.
     *
     * @param functionValues Los valores de la función a graficar.
     * @param allEvaluatedPoints Lista de listas de puntos evaluados en cada generación.
     * @param title El título de la gráfica.
     * @param xAxisTitle El título del eje X.
     * @param yAxisTitle El título del eje Y.
     */
    public void plotGraph(double[] functionValues, List<List<GeneticAlgorithm.Point>> allEvaluatedPoints, String title, String xAxisTitle, String yAxisTitle) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle).build();

        // Graficar valores de la función
        double[] xData = new double[functionValues.length];
        for (int i = 0; i < functionValues.length; i++) {
            xData[i] = i;
        }
        chart.addSeries("Función", xData, functionValues).setMarker(SeriesMarkers.NONE);

        // Graficar puntos evaluados para cada generación
        for (int gen = 0; gen < allEvaluatedPoints.size(); gen++) {
            List<GeneticAlgorithm.Point> points = allEvaluatedPoints.get(gen);
            double[] xPoints = new double[points.size()];
            double[] yPoints = new double[points.size()];
            for (int i = 0; i < points.size(); i++) {
                xPoints[i] = points.get(i).getX();
                yPoints[i] = points.get(i).getY();
            }
            PlotUtils.addSeriesToChart(chart, xPoints, yPoints, "Puntos Evaluados Gen " + (gen + 1));
        }

        new SwingWrapper<>(chart).displayChart();
    }
}
