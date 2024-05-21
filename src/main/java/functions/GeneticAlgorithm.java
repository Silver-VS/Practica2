package functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Clase que implementa un Algoritmo Genético.
 */
public class GeneticAlgorithm {
    private final int populationSize;
    private final double crossoverRate;
    private final double mutationRate;
    private final int maxGenerations;
    private Individual[] population;
    private final FunctionEvaluator.FunctionType functionType;
    private final List<Point> evaluatedPoints;
    private final int individualSize;
    private final int minInterval;
    private final int maxInterval;

    /**
     * Constructor para crear una instancia del Algoritmo Genético.
     *
     * @param populationSize Tamaño de la población.
     * @param crossoverRate Probabilidad de cruza.
     * @param mutationRate Probabilidad de mutación.
     * @param maxGenerations Número máximo de generaciones.
     * @param functionType Tipo de función a evaluar.
     * @param individualSize Tamaño del individuo en representación binaria.
     * @param minInterval Intervalo mínimo del rango.
     * @param maxInterval Intervalo máximo del rango.
     */
    public GeneticAlgorithm(int populationSize, double crossoverRate, double mutationRate, int maxGenerations, FunctionEvaluator.FunctionType functionType, int individualSize, int minInterval, int maxInterval) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.maxGenerations = maxGenerations;
        this.functionType = functionType;
        this.individualSize = individualSize;
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
        this.population = new Individual[populationSize];
        this.evaluatedPoints = new ArrayList<>();
        initializePopulation();
    }

    /**
     * Inicializa la población de individuos de manera aleatoria.
     */
    public void initializePopulation() {
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            StringBuilder chromosome = new StringBuilder();
            for (int j = 0; j < individualSize; j++) {
                chromosome.append(random.nextInt(2));
            }
            population[i] = new Individual(chromosome.toString(), functionType, minInterval, maxInterval);
            evaluatedPoints.add(new Point(decodeChromosome(chromosome.toString()), population[i].getFitness()));
        }
    }

    /**
     * Ejecuta el algoritmo genético para un número dado de generaciones.
     *
     * @param maximize Indica si se debe maximizar la función.
     * @param generations Número de generaciones a ejecutar.
     */
    public void runAlgorithm(boolean maximize, int generations) {
        for (int generation = 0; generation < generations; generation++) {
            Individual[] newPopulation = new Individual[populationSize];
            for (int i = 0; i < populationSize; i += 2) {
                Individual parent1 = selectParent();
                Individual parent2 = selectParent();
                Individual[] offspring = crossover(parent1, parent2);
                offspring[0].mutate(mutationRate);
                offspring[1].mutate(mutationRate);
                newPopulation[i] = offspring[0];
                newPopulation[i + 1] = offspring[1];
                evaluatedPoints.add(new Point(decodeChromosome(offspring[0].getChromosome()), offspring[0].getFitness()));
                evaluatedPoints.add(new Point(decodeChromosome(offspring[1].getChromosome()), offspring[1].getFitness()));
            }
            population = newPopulation;
        }
    }

    /**
     * Decodifica un cromosoma de binario a un valor entero dentro del intervalo.
     *
     * @param chromosome El cromosoma en representación binaria.
     * @return El valor entero decodificado del cromosoma.
     */
    private int decodeChromosome(String chromosome) {
        int value = Integer.parseInt(chromosome, 2);
        return minInterval + value * (maxInterval - minInterval) / ((int) Math.pow(2, individualSize) - 1);
    }

    /**
     * Selecciona un padre para la cruza usando selección proporcional al fitness.
     *
     * @return El individuo seleccionado como padre.
     */
    private Individual selectParent() {
        double totalFitness = Arrays.stream(population).mapToDouble(Individual::getFitness).sum();
        double rand = Math.random() * totalFitness;
        double cumulativeFitness = 0.0;
        for (Individual individual : population) {
            cumulativeFitness += individual.getFitness();
            if (cumulativeFitness >= rand) {
                return individual;
            }
        }
        return population[population.length - 1];  // No debería llegar aquí
    }

    /**
     * Aplica la cruza de un punto entre dos padres para generar dos hijos.
     *
     * @param parent1 El primer padre.
     * @param parent2 El segundo padre.
     * @return Un arreglo con los dos hijos generados.
     */
    private Individual[] crossover(Individual parent1, Individual parent2) {
        Random random = new Random();
        if (random.nextDouble() < crossoverRate) {
            int crossoverPoint = random.nextInt(parent1.getChromosome().length());
            String offspring1Chromosome = parent1.getChromosome().substring(0, crossoverPoint)
                    + parent2.getChromosome().substring(crossoverPoint);
            String offspring2Chromosome = parent2.getChromosome().substring(0, crossoverPoint)
                    + parent1.getChromosome().substring(crossoverPoint);
            return new Individual[]{new Individual(offspring1Chromosome, functionType, minInterval, maxInterval), new Individual(offspring2Chromosome, functionType, minInterval, maxInterval)};
        }
        return new Individual[]{new Individual(parent1.getChromosome(), functionType, minInterval, maxInterval), new Individual(parent2.getChromosome(), functionType, minInterval, maxInterval)};
    }

    /**
     * Obtiene el mejor individuo de la población actual.
     *
     * @return El mejor individuo de la población.
     */
    public Individual getBestIndividual() {
        return Arrays.stream(population).max((ind1, ind2) -> Double.compare(ind1.getFitness(), ind2.getFitness())).orElse(null);
    }

    /**
     * Obtiene los puntos evaluados durante la ejecución del algoritmo.
     *
     * @return Una lista de puntos evaluados.
     */
    public List<Point> getEvaluatedPoints() {
        return evaluatedPoints;
    }

    /**
     * Clase interna que representa un punto en el plano.
     */
    public static class Point {
        private final int x;
        private final double y;

        public Point(int x, double y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
