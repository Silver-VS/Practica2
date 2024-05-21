package functions;

/**
 * Clase para evaluar diferentes tipos de funciones.
 */
public class FunctionEvaluator {
    /**
     * Tipos de funciones disponibles.
     */
    public enum FunctionType {
        ABS, EQUISCUADRADA, RELLENO
    }

    /**
     * Evalúa una función dada un tipo de función y un valor x.
     *
     * @param type El tipo de función.
     * @param x El valor x a evaluar.
     * @return El resultado de la evaluación de la función.
     */
    public static double evaluate(FunctionType type, int x) {
        return switch (type) {
            case ABS -> Math.abs((x - 5) / 2.0 + Math.sin(x));
            case EQUISCUADRADA -> Math.pow(x, 2);
            case RELLENO -> Math.sin(x) + Math.cos(x);
            default -> throw new IllegalArgumentException("Tipo de función desconocido");
        };
    }

    /**
     * Genera valores de una función en un rango dado.
     *
     * @param type El tipo de función.
     * @param start El valor inicial del rango.
     * @param end El valor final del rango.
     * @return Un arreglo de valores de la función en el rango especificado.
     */
    public static double[] generateFunctionValues(FunctionType type, int start, int end) {
        double[] values = new double[end - start + 1];
        for (int i = start; i <= end; i++) {
            values[i - start] = evaluate(type, i);
        }
        return values;
    }
}
