package com.franosch.paul.solver.post_optimization;

import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterTestSuit {


    public Map.Entry<ParameterConfiguration, Double> findGoodParameter(Graph graph, List<Node> path) {
        final Map<ParameterConfiguration, Double> results = new HashMap<>();

        SolutionEvaluator solutionEvaluator = new SolutionEvaluator();
        System.out.println("naiv solution " + solutionEvaluator.evaluate(graph, path));

        for (double temperatur = 100.0; temperatur <= 500.0; temperatur += 100) { // 5
            System.out.println("Temperatur " + temperatur);
            for (double temperaturModifier = 0.9; temperaturModifier < 1; temperaturModifier += 0.02) { // 5
                for (int successfulCoolingPeriod = 1; successfulCoolingPeriod < 20; successfulCoolingPeriod += 5) { // 4
                    for (int coolingPeriod = successfulCoolingPeriod; coolingPeriod < 20; coolingPeriod += 5) { // 4
                        ParameterConfiguration parameterConfiguration = new ParameterConfiguration(temperatur,
                                temperaturModifier, successfulCoolingPeriod, coolingPeriod);

                        TwoOptPostOptimization twoOptPostOptimization = new TwoOptPostOptimization(solutionEvaluator);

                        final List<Node> optimize = twoOptPostOptimization.optimize(graph, path);
                        final Double evaluate = solutionEvaluator.evaluate(graph, optimize);
                        System.out.println(parameterConfiguration);
                        System.out.println(evaluate);
                        System.out.println(optimize);

                        results.put(parameterConfiguration, evaluate);

                    }
                }
            }
        }
        System.out.println("naiv solution " + solutionEvaluator.evaluate(graph, path));
        final Map.Entry<ParameterConfiguration, Double> best = findBest(results);
        System.out.println("best solution " + best);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/tmp/results.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(results);
            FileOutputStream fileOutputStream = new FileOutputStream("/tmp/best.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeDouble(best.getValue());
            objectOutputStream.writeObject(best.getKey());
        } catch (IOException e) {
            e.printStackTrace();
            return best;
        }
        return best;
    }

    private Map.Entry<ParameterConfiguration, Double> findBest(Map<ParameterConfiguration, Double> map) {
        return map.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .get();
    }

}
