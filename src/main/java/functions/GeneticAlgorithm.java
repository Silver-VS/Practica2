package functions;

import java.util.Arrays;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;


public class GeneticAlgorithm {
    private final int populationSize;
    private final double crossoverRate;
    private final double mutationRate;
    private final int maxGenerations;
    private Individual[] population;
    private FunctionEvaluator.FunctionType functionType;
    private List<Point> evaluatedPoints;

    public GeneticAlgorithm(int populationSize, double crossoverRate, double mutationRate, int maxGenerations, FunctionEvaluator.FunctionType functionType) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.maxGenerations = maxGenerations;
        this.functionType = functionType;
        this.population = new Individual[populationSize];
        this.evaluatedPoints = new ArrayList<>();
        initializePopulation();
    }

    public void initializePopulation() {
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            StringBuilder chromosome = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                chromosome.append(random.nextInt(2));
            }
            population[i] = new Individual(chromosome.toString(), functionType);
            evaluatedPoints.add(new Point(Integer.parseInt(chromosome.toString(), 2), population[i].getFitness()));
        }
    }

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
                evaluatedPoints.add(new Point(Integer.parseInt(offspring[0].getChromosome(), 2), offspring[0].getFitness()));
                evaluatedPoints.add(new Point(Integer.parseInt(offspring[1].getChromosome(), 2), offspring[1].getFitness()));
            }
            population = newPopulation;
        }
    }

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
        return population[population.length - 1];  // Should not reach here
    }

    private Individual[] crossover(Individual parent1, Individual parent2) {
        Random random = new Random();
        if (random.nextDouble() < crossoverRate) {
            int crossoverPoint = random.nextInt(parent1.getChromosome().length());
            String offspring1Chromosome = parent1.getChromosome().substring(0, crossoverPoint)
                    + parent2.getChromosome().substring(crossoverPoint);
            String offspring2Chromosome = parent2.getChromosome().substring(0, crossoverPoint)
                    + parent1.getChromosome().substring(crossoverPoint);
            return new Individual[]{new Individual(offspring1Chromosome, functionType), new Individual(offspring2Chromosome, functionType)};
        }
        return new Individual[]{new Individual(parent1.getChromosome(), functionType), new Individual(parent2.getChromosome(), functionType)};
    }

    public Individual getBestIndividual() {
        return Arrays.stream(population).max((ind1, ind2) -> Double.compare(ind1.getFitness(), ind2.getFitness())).orElse(null);
    }

    public List<Point> getEvaluatedPoints() {
        return evaluatedPoints;
    }

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
