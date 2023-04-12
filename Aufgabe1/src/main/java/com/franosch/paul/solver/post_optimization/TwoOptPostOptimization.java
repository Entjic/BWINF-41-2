package com.franosch.paul.solver.post_optimization;

import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.util.VectorCalculator;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * swap cities using simulated annealing to allow worsening swaps
 * this hopefully evades local minima
 */
@RequiredArgsConstructor
public class TwoOptPostOptimization implements PostOptimization {

    private final static ParameterConfiguration DEFAULT_CONFIGURATION =
            new ParameterConfiguration(100.0,
                    0.9,
                    50,
                    100);

    private final SolutionEvaluator solutionEvaluator;
    private final double temperaturModifier;
    private final int improvingIterationsUntilCooling;
    private final int iterationsUntilCooling;
    private double temperatur;


    public TwoOptPostOptimization(SolutionEvaluator solutionEvaluator) {
        this(solutionEvaluator, DEFAULT_CONFIGURATION);
    }

    public TwoOptPostOptimization(SolutionEvaluator solutionEvaluator,
                                  ParameterConfiguration configuration) {
        this.solutionEvaluator = solutionEvaluator;
        this.temperatur = configuration.startingTemperature();
        this.temperaturModifier = configuration.temperaturModifier();
        this.improvingIterationsUntilCooling = configuration.improvingIterationsUntilCooling();
        this.iterationsUntilCooling = configuration.iterationsUntilCooling();
    }

    @Override
    public List<Node> optimize(final Graph graph, final List<Node> tour, long timeLimit) {
        // TODO: 29.03.2023 optimize data structure of tour

        List<Node> current = new ArrayList<>(tour);

        double currentLength = solutionEvaluator.evaluate(graph, current);

        int improvingIterations = 0;
        int iterations = 0;
        long stopAfterTimeLimit = System.currentTimeMillis() + timeLimit;

        while (System.currentTimeMillis() < stopAfterTimeLimit) {
            ResultType improved = this.improve(graph, current, stopAfterTimeLimit);
            // double nextLength = solutionEvaluator.evaluate(graph, current);
            // System.out.println("improvement of " + (currentLength - nextLength));
            // currentLength = nextLength;
            if (improvingIterations == improvingIterationsUntilCooling) {
                improvingIterations = 0;
                iterations = 0;
                this.reduceTemperatur();
            }
            if (iterations == iterationsUntilCooling) {
                improvingIterations = 0;
                iterations = 0;
                this.reduceTemperatur();
            }
            // System.out.println("t " + temperatur);
            if (improved.equals(ResultType.ZERO)) {
                continue;
            }
            iterations++;
            if (improved.equals(ResultType.BETTER)) {
                improvingIterations++;
            }
        }
        return current;
    }

    private ResultType improve(Graph graph, List<Node> current, long timeLimit) {
        while (System.currentTimeMillis() < timeLimit) {

            int first = this.randomInRange(1, current.size() - 5);
            int second = this.randomInRange(first + 3, current.size() - 3);
            Node prevFirstNode = current.get(first - 1);
            Node firstNode = current.get(first);
            Node postFirstNode = current.get(first + 1);
            Node postPostFirstNode = current.get(first + 2);

            Node prevSecondNode = current.get(second - 1);
            Node secondNode = current.get(second);
            Node postSecondNode = current.get(second + 1);
            Node postPostSecondNode = current.get(second + 2);

            Edge a = graph.getEdge(prevFirstNode, firstNode);
            Edge insertedEdgeOne = graph.getEdge(firstNode, secondNode);
            Edge insertedEdgeTwo = graph.getEdge(postFirstNode, postSecondNode);
            Edge d = graph.getEdge(prevSecondNode, secondNode);
            Edge e = graph.getEdge(postFirstNode, postPostFirstNode);
            Edge f = graph.getEdge(postSecondNode, postPostSecondNode);

            if (doesNotMatchAngleCriteria(a, insertedEdgeOne)) {
                continue;
            }

            if (doesNotMatchAngleCriteria(insertedEdgeOne, d)) {
                continue;
            }

            if (doesNotMatchAngleCriteria(e, insertedEdgeTwo)) {
                continue;
            }

            if (doesNotMatchAngleCriteria(insertedEdgeTwo, f)) {
                continue;
            }

            Edge b = graph.getEdge(secondNode, postSecondNode);
            Edge c = graph.getEdge(firstNode, postFirstNode);

            double difference = - c.weight() - b.weight() + insertedEdgeOne.weight() + insertedEdgeTwo.weight();

            if (difference < 0) {
                twoOptSwap(current, first, second);
                return ResultType.BETTER;
            }

            if (difference == 0) {
                continue;
            }

            if (allowWorseningSwap(difference)) {
                twoOptSwap(current, first, second);
                return ResultType.WORSE;
            }
        }
        return ResultType.ZERO;
    }

    private int randomInRange(int from, int to) {

        if (from > to) {
            throw new IllegalArgumentException("from " + from + " > to " + to);
        }

        if (from == to) {
            return from;
        }

        int out = (int) (Math.random() * (to - from));
        out += from;
        // System.out.println("out " + (out) + " in range " + from + " to " + to);
        return out;
    }

    private boolean doesNotMatchAngleCriteria(Edge a, Edge b) {
        if (a.from().equals(b.from())) {
            return ! VectorCalculator.matchesAngleCriteria(a.vector(a.from()), b.vector(a.from()));
        }
        if (a.to().equals(b.to())) {
            return ! VectorCalculator.matchesAngleCriteria(a.vector(a.to()), b.vector(a.to()));
        }
        if (a.to().equals(b.from())) {
            return ! VectorCalculator.matchesAngleCriteria(a.vector(a.to()), b.vector(a.to()));
        }
        if (a.from().equals(b.to())) {
            return ! VectorCalculator.matchesAngleCriteria(a.vector(a.from()), b.vector(a.from()));
        }
        throw new IllegalArgumentException();
    }

    /**
     * only call if swap would make the tour longer
     *
     * @param difference current length - prev length
     * @return true if swap is allowed event though it worsens the length of the tour
     */
    private boolean allowWorseningSwap(double difference) {
        double random = Math.random();
        double exp = Math.exp((- difference) / temperatur);

        return exp > random;
    }


    private void twoOptSwap(List<Node> tour, int first, int second) {
        List<Node> firstToSecond = tour.subList(first + 1, second + 1);

        Collections.reverse(firstToSecond);
    }

    private enum ResultType {
        ZERO,
        BETTER,
        WORSE
    }

    private void reduceTemperatur() {
        this.temperatur = temperaturModifier * temperatur;
    }

}
