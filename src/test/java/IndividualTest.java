
import functions.FunctionEvaluator;
import functions.Individual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IndividualTest {

    @Test
    public void testCalculateFitnessABS() {
        Individual individual = new Individual("0111",  FunctionEvaluator.FunctionType.ABS, 0, 15);
        double expectedFitness = FunctionEvaluator.evaluate(FunctionEvaluator.FunctionType.ABS, 7);
        assertEquals(expectedFitness, individual.getFitness(), 0.0001);
    }

    @Test
    public void testMutate() {
        Individual individual = new Individual("0000", FunctionEvaluator.FunctionType.ABS, 0, 15);
        individual.mutate(1.0); // Force mutation
        assertEquals("1111", individual.getChromosome());
    }
}
