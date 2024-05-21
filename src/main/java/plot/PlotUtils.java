package plot;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * Utilidades para graficar resultados.
 * Esta clase contiene métodos auxiliares para graficar puntos en un gráfico.
 */
public class PlotUtils {
    // Si tienes métodos específicos en esta clase, inclúyelos aquí
    // Ejemplo de un método auxiliar de graficación:

    /**
     * Método auxiliar para agregar datos de puntos a un gráfico.
     *
     * @param chart El gráfico al que se agregarán los puntos.
     * @param xData Los datos de las coordenadas X.
     * @param yData Los datos de las coordenadas Y.
     * @param seriesName El nombre de la serie de datos.
     */
    public static void addSeriesToChart(XYChart chart, double[] xData, double[] yData, String seriesName) {
        chart.addSeries(seriesName, xData, yData).setMarker(SeriesMarkers.CIRCLE);
    }
}
