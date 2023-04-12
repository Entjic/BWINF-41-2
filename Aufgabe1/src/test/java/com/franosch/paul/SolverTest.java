package com.franosch.paul;

import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.nearest_neighbour.NearestNeighbourHeuristic;
import com.franosch.paul.solver.SolvingStrategy;
import com.franosch.paul.solver.nearest_neighbour.next_edge.AngleCriteriaNextEdgeProvider;
import com.franosch.paul.solver.nearest_neighbour.next_edge.WeightedNextEdgeProvider;
import com.franosch.paul.solver.post_optimization.ParameterConfiguration;
import com.franosch.paul.solver.post_optimization.ParameterTestSuit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SolverTest {

    private TravelingSalesmanSolver solver;

    @BeforeEach
    public void setUp() {
        solver = new TravelingSalesmanSolver();
    }

    @Test
    public void solveFirstByShortestEdgeFirst() {
        solver.solve(1, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/qifafudoli.css
    }

    @Test
    public void solveSecondByShortestEdgeFirst() {
        solver.solve(2, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/kefegobeja.css
    }

    @Test
    public void solveThirdByShortestEdgeFirst() {
        solver.solve(3, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/xayigarati.css
    }

    @Test
    public void solveFourthByShortestEdgeFirst() {
        solver.solve(4, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/kicemeyoga.css
    }

    @Test
    public void solveFifthByShortestEdgeFirst() { // TODO: 29.03.2023 prove this is not solvable
        solver.solve(5, true, SolvingStrategy.TRAVERSABLE_ORIENTED_WEIGHTED_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/aqigunotub.css
    }

    @Test
    public void solveSixthByShortestEdgeFirst() {
        solver.solve(6, true, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/joxezedezo.css
    }

    @Test
    public void solveSeventhByShortestEdgeFirst() {
        solver.solve(7, true, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/iyufokufak.css
    }

    @Test
    public void allStartingNodesNearestNeighbourHeuristicIsAlwaysBetterThanJustNearestNeighbourHeuristicTest(){

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
