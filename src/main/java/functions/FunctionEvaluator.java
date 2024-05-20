package functions;

public class FunctionEvaluator {
    public enum FunctionType {
        ABS, EQUISCUADRADA, RELLENO
    }

    public static double evaluate(FunctionType type, int x) {
        switch (type) {
            case ABS:
                return Math.abs((x - 5) / 2.0 + Math.sin(x));
            case EQUISCUADRADA:
                return Math.pow(x, 2);
            case RELLENO:
                return Math.sin(x) + Math.cos(x);
            default:
                throw new IllegalArgumentException("Unknown function type");
        }
    }

    public static double[] generateFunctionValues(FunctionType type, int start, int end) {
        double[] values = new double[end - start + 1];
        for (int i = start; i <= end; i++) {
            values[i - start] = evaluate(type, i);
        }
        return values;
    }
}

