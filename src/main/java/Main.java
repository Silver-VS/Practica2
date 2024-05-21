import functions.FunctionEvaluator;
import functions.GeneticAlgorithm;
import functions.Individual;
import plot.Graphing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal para ejecutar el programa de Algoritmos Genéticos.
 * Solicita al usuario los parámetros necesarios y ejecuta el algoritmo genético.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario la función a evaluar
        System.out.println("Seleccione la función a evaluar:");
        System.out.println("1. Función ABS");
        System.out.println("2. Función Cuadrada");
        System.out.println("3. Función Seno + Coseno");
        int functionChoice = scanner.nextInt();

        FunctionEvaluator.FunctionType functionType = switch (functionChoice) {
            case 1 -> FunctionEvaluator.FunctionType.ABS;
            case 2 -> FunctionEvaluator.FunctionType.EQUISCUADRADA;
            case 3 -> FunctionEvaluator.FunctionType.RELLENO;
            default -> throw new IllegalArgumentException("Elección de función inválida");
        };

        // Solicitar al usuario la probabilidad de cruza
        System.out.println("Ingrese la probabilidad de cruza (máximo 70%):");
        double crossoverRate = scanner.nextDouble();
        if (crossoverRate > 0.7) {
            System.out.println("Probabilidad de cruza inválida. Se establecerá en 70%.");
            crossoverRate = 0.7;
        }

        // Solicitar al usuario la probabilidad de mutación
        System.out.println("Ingrese la probabilidad de mutación (entre 0% y 30%):");
        double mutationRate = scanner.nextDouble();
        if (mutationRate > 0.3) {
            System.out.println("Probabilidad de mutación inválida. Se establecerá en 30%.");
            mutationRate = 0.3;
        }

        // Solicitar al usuario el número de generaciones
        System.out.println("Ingrese el número de generaciones a realizar:");
        int maxGenerations = scanner.nextInt();

        // Solicitar al usuario el intervalo numérico
        System.out.println("Ingrese el intervalo numérico mínimo:");
        int minInterval = scanner.nextInt();

        System.out.println("Ingrese el intervalo numérico máximo:");
        int maxInterval = scanner.nextInt();

        // Calcular el tamaño del individuo en su representación binaria
        int intervalRange = maxInterval - minInterval;
        int individualSize = (int) Math.ceil(Math.log(intervalRange) / Math.log(2));

        int populationSize = 20; // Puede ser ajustable si se desea

        // Crear instancia del algoritmo genético
        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, crossoverRate, mutationRate, maxGenerations, functionType, individualSize, minInterval, maxInterval);

        List<List<GeneticAlgorithm.Point>> allEvaluatedPoints = new ArrayList<>();

        // Ejecutar el algoritmo genético en ciclos de generaciones
        while (true) {
            ga.runAlgorithm(true, 1); // Maximizar por defecto
            allEvaluatedPoints.add(new ArrayList<>(ga.getEvaluatedPoints()));

            double[] functionValues = FunctionEvaluator.generateFunctionValues(functionType, minInterval, maxInterval);

            Graphing graphing = new Graphing();
            graphing.plotGraph(functionValues, allEvaluatedPoints, "Función y Puntos Evaluados", "X", "Y");

            System.out.println("¿Desea realizar otra generación? (sí/no)");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("sí")) {
                break;
            }
        }

        Individual best = ga.getBestIndividual();
        System.out.println("Mejor Individuo: " + best.getChromosome() + " Fitness: " + best.getFitness());
    }
}
