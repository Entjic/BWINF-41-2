package com.franosch.paul;

import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.NearestNeighbourHeuristic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NearestNeighbourHeuristicTest {

    @Test
    public void solveSecond() {
        NearestNeighbourHeuristic nearestNeighbourHeuristic = new NearestNeighbourHeuristic();

        GraphGenerator graphGenerator = new GraphGenerator(5, true);
        Graph graph = graphGenerator.generateGraph();
        Node startNode = graphGenerator.findStartNode(graph);
        List<Node> solve = nearestNeighbourHeuristic.solve(graph, startNode);
        AngleCriteriaAsserter asserter = new AngleCriteriaAsserter();

        Assertions.assertTrue(asserter.fulfillsAngleCriteria(graph, solve));

    }


}
