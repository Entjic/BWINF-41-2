package com.franosch.paul;

import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.nearest_neighbour.NearestNeighbourHeuristic;
import com.franosch.paul.solver.nearest_neighbour.next_edge.AngleCriteriaNextEdgeProvider;
import com.franosch.paul.solver.nearest_neighbour.next_edge.WeightedNextEdgeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NearestNeighbourHeuristicTest {

    @Test
    public void solveSecond() {
        NearestNeighbourHeuristic nearestNeighbourHeuristic = new NearestNeighbourHeuristic(
                new WeightedNextEdgeProvider(
                        new AngleCriteriaNextEdgeProvider()
                )
        );

        GraphGenerator graphGenerator = new GraphGenerator(5, true);
        Graph graph = graphGenerator.generateGraph();
        Node startNode = graphGenerator.findStartNode(graph);
        List<Node> solve = nearestNeighbourHeuristic.solve(graph, startNode);
        AngleCriteriaAsserter asserter = new AngleCriteriaAsserter();

        Assertions.assertTrue(asserter.fulfillsAngleCriteria(graph, solve));

    }


}
