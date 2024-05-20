import functions.FunctionEvaluator;
import functions.GeneticAlgorithm;
import functions.Individual;
import plot.Graphing;

import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the function to evaluate:");
        System.out.println("1. ABS function");
        System.out.println("2. Square function");
        System.out.println("3. Sine + Cosine function");
        int functionChoice = scanner.nextInt();

        FunctionEvaluator.FunctionType functionType;
        switch (functionChoice) {
            case 1:
                functionType = FunctionEvaluator.FunctionType.ABS;
                break;
            case 2:
                functionType = FunctionEvaluator.FunctionType.EQUISCUADRADA;
                break;
            case 3:
                functionType = FunctionEvaluator.FunctionType.RELLENO;
                break;
            default:
                throw new IllegalArgumentException("Invalid function choice");
        }

        System.out.println("Do you want to maximize or minimize the function?");
        System.out.println("1. Maximize");
        System.out.println("2. Minimize");
        int optimizeChoice = scanner.nextInt();

        boolean maximize = optimizeChoice == 1;

        int populationSize = 20;
        double crossoverRate = 0.7;
        //max 0.7%
        double mutationRate = 0.01;
        //entre 0 y 30 %
        int maxGenerations = 100;
        //tamaño del individuo pidiendo el intervalo de ejecucion
        //checar si son aptos para mutarse

        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, crossoverRate, mutationRate, maxGenerations, functionType);

        List<List<GeneticAlgorithm.Point>> allEvaluatedPoints = new ArrayList<>();

        while (true) {
            ga.runAlgorithm(maximize, 1);
            allEvaluatedPoints.add(new ArrayList<>(ga.getEvaluatedPoints()));

            double[] functionValues = FunctionEvaluator.generateFunctionValues(functionType, 0, 15);

            Graphing graphing = new Graphing();
            graphing.plotGraph(functionValues, allEvaluatedPoints, "Function and Evaluated Points", "X", "Y");

            System.out.println("Do you want to run another generation? (yes/no)");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("yes")) {
                break;
            }
        }

        Individual best = ga.getBestIndividual();
        System.out.println("Best Individual: " + best.getChromosome() + " Fitness: " + best.getFitness());
    }
}
