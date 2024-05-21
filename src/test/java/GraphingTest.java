import functions.GeneticAlgorithm;
import functions.FunctionEvaluator;
import org.junit.jupiter.api.Test;
import plot.Graphing;

import java.util.ArrayList;
import java.util.List;

public class GraphingTest {

    @Test
    public void testPlotGraph() {
        double[] functionValues = FunctionEvaluator.generateFunctionValues(FunctionEvaluator.FunctionType.ABS, 0, 15);
        List<GeneticAlgorithm.Point> points = new ArrayList<>();
        points.add(new GeneticAlgorithm.Point(7, FunctionEvaluator.evaluate(FunctionEvaluator.FunctionType.ABS, 7)));

        List<List<GeneticAlgorithm.Point>> allEvaluatedPoints = new ArrayList<>();
        allEvaluatedPoints.add(points);

        Graphing graphing = new Graphing();
        graphing.plotGraph(functionValues, allEvaluatedPoints, "Test Plot", "X Axis", "Y Axis");
    }
}
