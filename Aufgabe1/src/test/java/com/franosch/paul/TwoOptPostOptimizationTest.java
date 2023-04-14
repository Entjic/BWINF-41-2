package com.franosch.paul;

import com.franosch.paul.eval.AngleCriteriaAsserter;
import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.SolvingStrategy;
import com.franosch.paul.solver.nearest_neighbour.NearestNeighbourHeuristic;
import com.franosch.paul.solver.nearest_neighbour.next_edge.AngleCriteriaNextEdgeProvider;
import com.franosch.paul.solver.nearest_neighbour.next_edge.WeightedNextEdgeProvider;
import com.franosch.paul.solver.post_optimization.ParameterConfiguration;
import com.franosch.paul.solver.post_optimization.ParameterTestSuit;
import com.franosch.paul.solver.post_optimization.TwoOptPostOptimization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TwoOptPostOptimizationTest {


    @Test
    public void test() {
        NearestNeighbourHeuristic nearestNeighbourHeuristic = new NearestNeighbourHeuristic(
                new WeightedNextEdgeProvider(
                        new AngleCriteriaNextEdgeProvider()
                )
        );
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
                tour), 10000);

        AngleCriteriaAsserter angleCriteriaAsserter = new AngleCriteriaAsserter();

        Assertions.assertEquals(tour, optimized);
        Assertions.assertTrue(angleCriteriaAsserter.fulfillsAngleCriteria(graph, optimized));

    }

    @Test
    public void allStartingNodesNearestNeighbourHeuristicIsAlwaysBetterThanJustNearestNeighbourHeuristicTest(){
        TravelingSalesmanSolver solver = new TravelingSalesmanSolver();
        int[] testCasesThatAreSolvable = new int[]{1,2,3,4,6,7};

        for (final int testCase : testCasesThatAreSolvable) {
            System.out.println("test case " + testCase);
            double solvedByNearestNeighbourHeuristic = solver.solve(testCase, true,
                    SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
            double solvedByAllStartingNodesNearestNeighbourHeuristic = solver.solve(testCase, true,
                    SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
            Assertions.assertTrue(solvedByAllStartingNodesNearestNeighbourHeuristic <= solvedByNearestNeighbourHeuristic);
        }

    }


    @Test
    public void findGoodParameter() {
        ParameterTestSuit parameterTestSuit = new ParameterTestSuit();
        GraphGenerator graphGenerator = new GraphGenerator(6, true);
        final Graph graph = graphGenerator.generateGraph();
        final Node startNode = graphGenerator.findStartNode(graph);
        NearestNeighbourHeuristic nearestNeighbourHeuristic = new NearestNeighbourHeuristic(
                new WeightedNextEdgeProvider(
                        new AngleCriteriaNextEdgeProvider()
                )
        );
        final List<Node> solve = nearestNeighbourHeuristic.solve(graph, startNode);
        final ParameterConfiguration goodParameter =
                parameterTestSuit.findGoodParameter(graph, solve);

        System.out.println("best config " + goodParameter);
    }





}