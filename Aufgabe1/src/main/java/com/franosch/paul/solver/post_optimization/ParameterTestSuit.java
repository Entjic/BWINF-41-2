package com.franosch.paul.solver.post_optimization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.franosch.paul.eval.PathPrinter;
import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.io.File;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ParameterTestSuit {


    public ParameterConfiguration findGoodParameter(Graph graph, List<Node> path) {

        SolutionEvaluator solutionEvaluator = new SolutionEvaluator();
        System.out.println("naiv solution " + solutionEvaluator.evaluate(graph, path));
        PathPrinter pathPrinter = new PathPrinter();
        pathPrinter.print(path);
        pathPrinter.printPoints(path);
        Set<ParameterConfiguration> parameterConfigurations = this.generateAllParameterConfigurations();

        final int iterations = 5;
        final long timeLimit = 10000;

        System.out.println("iterations " + iterations);
        System.out.println("length " + parameterConfigurations.size());
        System.out.println("iterations * length " + iterations * parameterConfigurations.size());

        final int cores = Runtime.getRuntime().availableProcessors();

        System.out.println("available cores " + cores);

        double seconds = iterations * parameterConfigurations.size() * timeLimit / 1000.0;
        System.out.println("estimated time in seconds without multithreading " + seconds);
        System.out.println("estimated time in minutes without multithreading " + seconds / 60.0);
        System.out.println("estimated time in hours without multithreading " + seconds / 3600.0);

        seconds = seconds / cores;

        System.out.println("estimated time in seconds using multithreading " + seconds);
        System.out.println("estimated time in minutes using multithreading " + seconds / 60.0);
        System.out.println("estimated time in hours using multithreading " + seconds / 3600.0);


        final Map<ParameterConfiguration, ParameterTestResult> results = Flux.fromIterable(parameterConfigurations)
                .parallel()
                .flatMap(parameterConfiguration -> Mono.fromCallable(() -> {
                    // System.out.println("now testing " + parameterConfiguration);
                    ParameterTestResult result = test(parameterConfiguration, solutionEvaluator, graph, path, iterations, timeLimit);
                    // System.out.println("finished testing " + parameterConfiguration);
                    return Tuples.of(parameterConfiguration, result);
                }).subscribeOn(Schedulers.parallel()))
                .sequential()
                .collectMap(Tuple2::getT1, Tuple2::getT2)
                .block();

        String date = Instant.now().toString();

        this.saveBestResults(results, graph, date);

        return this.findBestByAverage(results, 1).stream().findAny().map(Map.Entry::getKey).orElseThrow();
    }

    @SneakyThrows
    private void saveBestResults(Map<ParameterConfiguration, ParameterTestResult> map, Graph graph, String date) {

        int amount = map.size() > 3 ? 3 : 1;

        Set<Map.Entry<ParameterConfiguration, ParameterTestResult>> bestByAverage = this.findBestByAverage(map, amount);

        Set<Map.Entry<ParameterConfiguration, ParameterTestResult>> bestByMin = this.findBestByMin(map, amount);

        Set<Map.Entry<ParameterConfiguration, ParameterTestResult>> bestByLowestMax = this.findBestByLowestMax(map, amount);

        for (final Map.Entry<ParameterConfiguration, ParameterTestResult> byAverage : bestByAverage) {
            System.out.println("BEST BY AVERAGE " + byAverage.getKey().toString() + " -> " + byAverage.getValue().average());
        }

        for (final Map.Entry<ParameterConfiguration, ParameterTestResult> byMin : bestByMin) {
            System.out.println("BEST BY MIN " + byMin.getKey().toString() + " -> " + byMin.getValue().min());
        }

        for (final Map.Entry<ParameterConfiguration, ParameterTestResult> byLowestMax : bestByLowestMax) {
            System.out.println("BEST BY LOWEST MAX " + byLowestMax.getKey().toString() + " -> " + byLowestMax.getValue().max());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        File bestByAverageFile = this.createFile("bestByAverage", date);
        File bestByMinFile = this.createFile("bestByMin", date);
        File bestByLowestMaxFile = this.createFile("bestByLowestMax", date);
        File bestByMinPathFile = this.createFile("bestByMinPath", date);
        File resultsFile = this.createFile("results", date);
        objectWriter.writeValue(resultsFile, map);
        objectWriter.writeValue(bestByAverageFile, bestByAverage);
        objectWriter.writeValue(bestByMinFile, bestByMin);
        writeBestSolutionToFile(map, graph, objectWriter, bestByMinPathFile);
        objectWriter.writeValue(bestByLowestMaxFile, bestByLowestMax);

    }

    @SneakyThrows
    private void writeBestSolutionToFile(final Map<ParameterConfiguration, ParameterTestResult> map,
                                         final Graph graph,
                                         final ObjectWriter objectWriter,
                                         final File bestByMinPathFile) {
        PathPrinter pathPrinter = new PathPrinter();
        SolutionEvaluator solutionEvaluator = new SolutionEvaluator();


        List<Node> path = this.findBestByMin(map, 1).stream().map(parameterConfigurationParameterTestResultEntry ->
                        parameterConfigurationParameterTestResultEntry.getValue().routes())
                .flatMap(Set::stream)
                .max(Comparator.comparingDouble(value ->
                        solutionEvaluator.evaluate(graph, value)))
                .orElseThrow();


        objectWriter.writeValue(bestByMinPathFile, pathPrinter.generatePathString(path) + "\n\n"
                + pathPrinter.generatePointsString(path));
    }

    private File createFile(String name, String date) {

        String directory = new File("").getAbsolutePath() + "/tmp" + date + "/";

        new File(directory).mkdirs();

        return new File(directory + name + ".json");
    }


    private Set<ParameterConfiguration> generateAllParameterConfigurations() {

        Set<ParameterConfiguration> set = new HashSet<>();

        for (double temperatur = 100; temperatur <= 800; temperatur += 50) { // 14
            for (double temperaturModifier = 0.9; temperaturModifier <= 0.95; temperaturModifier += 0.005) { // 10
                for (int successfulCoolingPeriod = 5; successfulCoolingPeriod <= 15; successfulCoolingPeriod += 5) { // 3
                    for (int coolingPeriod = successfulCoolingPeriod; coolingPeriod <= 20; coolingPeriod += 5) { // 2
                        ParameterConfiguration parameterConfiguration = new ParameterConfiguration(temperatur,
                                temperaturModifier, successfulCoolingPeriod, coolingPeriod);

                        set.add(parameterConfiguration);

                    }
                }
            }
        }

        return set;
    }

    private ParameterTestResult test(ParameterConfiguration parameterConfiguration, SolutionEvaluator solutionEvaluator,
                                     Graph graph, List<Node> path, int iterations, long timeLimit) {
        if (iterations <= 0) {
            throw new IllegalArgumentException();
        }
        Set<Double> doubles = new HashSet<>();
        Set<List<Node>> routes = new HashSet<>();
        for (int i = 0; i < iterations; i++) {
            List<Node> testResult = this.test(parameterConfiguration, solutionEvaluator, graph, path, timeLimit);
            doubles.add(solutionEvaluator.evaluate(graph, testResult));
            routes.add(testResult);
        }
        // System.out.println(doubles);
        double min = doubles.stream().min(Double::compareTo).orElseThrow();
        // System.out.println("min " + min);
        double max = doubles.stream().max(Double::compareTo).orElseThrow();
        // System.out.println("max " + max);
        double average = doubles.stream().mapToDouble(Number::doubleValue).average().orElseThrow();
        // System.out.println("average " + average);

        return new ParameterTestResult(min, max, average, routes);
    }

    private List<Node> test(ParameterConfiguration parameterConfiguration, SolutionEvaluator solutionEvaluator,
                            Graph graph, List<Node> path, long timeLimit) {

        TwoOptPostOptimization twoOptPostOptimization = new TwoOptPostOptimization(solutionEvaluator, parameterConfiguration);
        return twoOptPostOptimization.optimize(graph, path, timeLimit);
    }

    private Set<Map.Entry<ParameterConfiguration, ParameterTestResult>> findBestByAverage(Map<ParameterConfiguration, ParameterTestResult> input, int amount) {
        return input.entrySet().stream().sorted(Comparator.comparing(parameterConfigurationParameterTestResultEntry ->
                        parameterConfigurationParameterTestResultEntry.getValue().average))
                .limit(amount).collect(Collectors.toSet());
    }

    private Set<Map.Entry<ParameterConfiguration, ParameterTestResult>> findBestByMin(Map<ParameterConfiguration, ParameterTestResult> input, int amount) {
        return input.entrySet().stream().sorted(Comparator.comparing(parameterConfigurationParameterTestResultEntry ->
                        parameterConfigurationParameterTestResultEntry.getValue().min))
                .limit(amount).collect(Collectors.toSet());
    }

    private Set<Map.Entry<ParameterConfiguration, ParameterTestResult>> findBestByLowestMax(Map<ParameterConfiguration, ParameterTestResult> input, int amount) {


        List<Map.Entry<ParameterConfiguration, ParameterTestResult>> list = new ArrayList<>(input.entrySet());

        list.sort(Comparator.comparingDouble(value -> value.getValue().max()));
        Collections.reverse(list);

        return new HashSet<>(list.subList(list.size() - amount, list.size()));
    }


    public record ParameterTestResult(double min, double max, double average, Set<List<Node>> routes) {
    }


}
