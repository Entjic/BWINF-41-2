package com.franosch.paul.solver.post_optimization;

import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.model.Vector;
import com.franosch.paul.util.VectorCalculator;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * swap cities using simulated annealing to allow worsening swaps
 */
@RequiredArgsConstructor
public class TwoOptPostOptimization implements PostOptimization {

    private final SolutionEvaluator solutionEvaluator;

    private double temperatur = 200.0;

    private final static double temperaturModifier = 0.95;
    private final static int improvingIterationsUntilCooling = 3;
    private final static int iterationsUntilCooling = 5;

    @Override
    public List<Node> optimize(final Graph graph, final List<Node> tour) {
        // TODO: 29.03.2023 optimize data structure of tour

        List<Node> current = new ArrayList<>(tour);

        double currentLength = solutionEvaluator.evaluate(graph, current);

        boolean improved = true;

        while (improved) {
            improved = this.improve(graph, current);
            currentLength = solutionEvaluator.evaluate(graph, current);
        }
        return current;
    }

    private boolean improve(Graph graph, List<Node> current) {
        for (int first = 1; first < current.size() - 2; first++) {
            for (int second = first + 4; second < current.size() - 2; second++) {

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

                double difference = -c.weight() - b.weight() + insertedEdgeOne.weight() + insertedEdgeTwo.weight();

                if (difference < 0) {
                    twoOptSwap(current, first, second);
                    return true;
                }

                if(allowWorseningSwap(difference)){

                    return false;
                }

            }
        }
        return false;
    }


    private boolean doesNotMatchAngleCriteria(Edge a, Edge b) {
        if (a.from().equals(b.from())) {
            return !VectorCalculator.matchesAngleCriteria(a.vector(a.from()), b.vector(a.from()));
        }
        if (a.to().equals(b.to())) {
            return !VectorCalculator.matchesAngleCriteria(a.vector(a.to()), b.vector(a.to()));
        }
        if (a.to().equals(b.from())) {
            return !VectorCalculator.matchesAngleCriteria(a.vector(a.to()), b.vector(a.to()));
        }
        if (a.from().equals(b.to())) {
            return !VectorCalculator.matchesAngleCriteria(a.vector(a.from()), b.vector(a.from()));
        }
        throw new IllegalArgumentException();
    }


    private void twoOptSwap(List<Node> tour, int first, int second) {
        List<Node> firstToSecond = tour.subList(first + 1, second + 1);

        Collections.reverse(firstToSecond);
    }

    /**
     * only call if swap would make the tour longer
     *
     * @param difference current length - prev length
     * @return true if swap is allowed event though it worsens the length of the tour
     */
    private boolean allowWorseningSwap(double difference) {
        double random = Math.random();
        double exp = Math.exp((-difference) / temperatur);

        return exp > random;
    }

    private void reduceTemperatur() {
        this.temperatur = temperaturModifier * temperatur;
    }

}
