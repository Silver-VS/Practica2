package functions.selector;

import plot.graficarM;

public class relleno {
    public static void plotFunction(graficarM panelGrafico, int rango1, int rango2, int qm) {
        int cantidad = rango2 - rango1 + 1;
        double[][] datos = new double[cantidad][2];
        double resultado = qm == 0 ? Double.MAX_VALUE : Double.MIN_VALUE;
        int num = rango1;

        for (int i = rango1; i <= rango2; i++) {
            double sen = Math.sin(i);
            sen = Math.round(sen * 100) / 100.0;
            double evaluado = Math.abs((i - 5) / (2 + sen));
            evaluado = Math.round(evaluado * 100) / 100.0;
            datos[i - rango1][0] = i;
            datos[i - rango1][1] = evaluado;

            if (qm == 0) { // Minimize
                if (evaluado < resultado) {
                    resultado = evaluado;
                    num = i;
                }
            } else { // Maximize
                if (evaluado > resultado) {
                    resultado = evaluado;
                    num = i;
                }
            }
        }

        panelGrafico.grafico(datos);
        System.out.println("El " + (qm == 0 ? "mínimo" : "máximo") + " es: " + resultado + " del número " + num);
    }
}
