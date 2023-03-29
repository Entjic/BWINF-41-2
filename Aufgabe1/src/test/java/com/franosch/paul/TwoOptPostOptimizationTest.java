package com.franosch.paul;

import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.NearestNeighbourHeuristic;
import com.franosch.paul.solver.post_optimization.TwoOptPostOptimization;
import com.franosch.paul.util.VectorCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TwoOptPostOptimizationTest {


    @Test
    public void test() {
        NearestNeighbourHeuristic nearestNeighbourHeuristic = new NearestNeighbourHeuristic();
        GraphGenerator graphGenerator = new GraphGenerator(0, true);
        Graph graph = graphGenerator.generateGraph();
        Node start = graphGenerator.findStartNode(graph);


        Node zero = graph.getNodeById(0);
        Node one = graph.getNodeById(1);
        Node two = graph.getNodeById(2);
        Node three = graph.getNodeById(3);
        Node four = graph.getNodeById(4);
        Node five = graph.getNodeById(5);

        List<Node> solve = nearestNeighbourHeuristic.solve(graph, start);

        Assertions.assertEquals(List.of(zero, one, two, three, four, five), solve);
    }

    @Test
    public void testAngleCriteria() {

        TwoOptPostOptimization twoOptPostOptimization = new TwoOptPostOptimization(new SolutionEvaluator());
        GraphGenerator graphGenerator = new GraphGenerator(0, true);
        Graph graph = graphGenerator.generateGraph();

        Node zero = graph.getNodeById(0);
        Node one = graph.getNodeById(1);
        Node two = graph.getNodeById(2);
        Node three = graph.getNodeById(3);
        Node four = graph.getNodeById(4);
        Node five = graph.getNodeById(5);
        Node six = graph.getNodeById(6);
        Node seven = graph.getNodeById(7);
        List<Node> tour = List.of(zero, one, two, three, four, five, six, seven);
        List<Node> optimized = twoOptPostOptimization.optimize(graph, new ArrayList<>(
                tour));

        AngleCriteriaAsserter angleCriteriaAsserter = new AngleCriteriaAsserter();

        Assertions.assertEquals(tour, optimized);
        Assertions.assertTrue(angleCriteriaAsserter.fulfillsAngleCriteria(graph, optimized));

    }



}