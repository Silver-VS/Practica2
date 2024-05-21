package functions;

/**
 * Clase que representa a un individuo en la población.
 */
public class Individual {
    private String chromosome;
    private double fitness;
    private final FunctionEvaluator.FunctionType functionType;
    private final int minInterval;
    private final int maxInterval;

    /**
     * Constructor para crear un individuo.
     *
     * @param chromosome El cromosoma del individuo en representación binaria.
     * @param functionType El tipo de función a evaluar.
     * @param minInterval El intervalo mínimo del rango.
     * @param maxInterval El intervalo máximo del rango.
     */
    public Individual(String chromosome, FunctionEvaluator.FunctionType functionType, int minInterval, int maxInterval) {
        this.chromosome = chromosome;
        this.functionType = functionType;
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
        this.fitness = calculateFitness();
    }

    public String getChromosome() {
        return chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    /**
     * Calcula la aptitud (fitness) del individuo basado en su cromosoma.
     *
     * @return La aptitud del individuo.
     */
    private double calculateFitness() {
        int x = decodeChromosome();
        return FunctionEvaluator.evaluate(functionType, x);
    }

    /**
     * Decodifica el cromosoma de binario a un valor entero dentro del intervalo.
     *
     * @return El valor entero decodificado del cromosoma.
     */
    private int decodeChromosome() {
        int value = Integer.parseInt(chromosome, 2);
        return minInterval + value * (maxInterval - minInterval) / ((int) Math.pow(2, chromosome.length()) - 1);
    }

    /**
     * Aplica mutación al cromosoma del individuo con una probabilidad dada.
     *
     * @param mutationRate La probabilidad de mutación.
     */
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
