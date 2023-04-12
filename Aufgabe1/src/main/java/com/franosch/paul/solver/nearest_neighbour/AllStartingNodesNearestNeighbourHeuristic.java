package com.franosch.paul.solver.nearest_neighbour;

import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.Solver;
import com.franosch.paul.solver.SolvingStrategy;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AllStartingNodesNearestNeighbourHeuristic implements Solver {

    private final NearestNeighbourHeuristic nearestNeighbourHeuristic;
    private final SolutionEvaluator solutionEvaluator;

    @Override
    public List<Node> solve(final Graph graph, final Node start) {

        Map<Node, List<Node>> solved = new HashMap<>();

        for (final Node node : graph.getNodes()) {
            List<Node> solve = this.nearestNeighbourHeuristic.solve(graph, node);
            solved.put(node, solve);
        }

        Map.Entry<Node, List<Node>> best = this.findBest(graph, solved);

        Double evaluate = this.solutionEvaluator.evaluate(graph, best.getValue());
        System.out.println("all starting node nearest neighbour heuristic found best tour with length " + evaluate + " for starting node " + best.getKey());

        return best.getValue();
    }

    private Map.Entry<Node, List<Node>> findBest(Graph graph, Map<Node, List<Node>> map) {

        double min = Double.MAX_VALUE;
        Map.Entry<Node, List<Node>> currentBest = null;

        for (final Map.Entry<Node, List<Node>> nodeListEntry : map.entrySet()) {
            Double evaluate = this.solutionEvaluator.evaluate(graph, nodeListEntry.getValue());

            if (evaluate < min) {
                min = evaluate;
                currentBest = nodeListEntry;
            }

        }
        return currentBest;
    }

    @Override
    public SolvingStrategy getSolvingStrategy() {
        return SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC;
    }
}
