package functions;

public class Individual {
    private String chromosome;
    private double fitness;
    private FunctionEvaluator.FunctionType functionType;

    public Individual(String chromosome, FunctionEvaluator.FunctionType functionType) {
        this.chromosome = chromosome;
        this.functionType = functionType;
        this.fitness = calculateFitness();
    }

    public String getChromosome() {
        return chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    private double calculateFitness() {
        int x = Integer.parseInt(chromosome, 2);
        return FunctionEvaluator.evaluate(functionType, x);
    }

    public void mutate(double mutationRate) {
        StringBuilder newChromosome = new StringBuilder(chromosome);
        for (int i = 0; i < chromosome.length(); i++) {
            if (Math.random() < mutationRate) {
                newChromosome.setCharAt(i, chromosome.charAt(i) == '0' ? '1' : '0');
            }
        }
        chromosome = newChromosome.toString();
        fitness = calculateFitness();
    }
}
