
import functions.FunctionEvaluator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionEvaluatorTest {

    @Test
    public void testEvaluateABS() {
        assertEquals(2.0, FunctionEvaluator.evaluate(FunctionEvaluator.FunctionType.ABS, 7), 0.0001);
    }

    @Test
    public void testEvaluateSquare() {
        assertEquals(49, FunctionEvaluator.evaluate(FunctionEvaluator.FunctionType.EQUISCUADRADA, 7), 0.0001);
    }

    @Test
    public void testEvaluateSineCosine() {
        assertEquals(1.3818, FunctionEvaluator.evaluate(FunctionEvaluator.FunctionType.RELLENO, 7), 0.0001);
    }

    @Test
    public void testGenerateFunctionValues() {
        double[] values = FunctionEvaluator.generateFunctionValues(FunctionEvaluator.FunctionType.ABS, 0, 10);
        assertEquals(11, values.length);
    }
}
