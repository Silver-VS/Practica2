
import functions.FunctionEvaluator;
import functions.GeneticAlgorithm;
import functions.Individual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GeneticAlgorithmTest {

    private GeneticAlgorithm ga;

    @BeforeEach
    public void setUp() {
        ga = new GeneticAlgorithm(10, 0.7, 0.01, 50, FunctionEvaluator.FunctionType.ABS, 4, 0, 15);
    }

    @Test
    public void testInitializePopulation() {
        List<GeneticAlgorithm.Point> points = ga.getEvaluatedPoints();
        assertNotNull(points);
        assertFalse(points.isEmpty());
    }

    @Test
    public void testRunAlgorithm() {
        ga.runAlgorithm(true, 1);
        Individual best = ga.getBestIndividual();
        assertNotNull(best);
        assertTrue(best.getFitness() > 0);
    }
}
