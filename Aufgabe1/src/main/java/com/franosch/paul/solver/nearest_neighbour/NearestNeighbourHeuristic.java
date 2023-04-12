package com.franosch.paul.solver.nearest_neighbour;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.Solver;
import com.franosch.paul.solver.SolvingStrategy;
import com.franosch.paul.solver.nearest_neighbour.next_edge.WeightedNextEdgeProvider;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class NearestNeighbourHeuristic implements Solver {


    private boolean solutionFound = false;

    private List<Node> solution = null;

    private final WeightedNextEdgeProvider weightedNextEdgeProvider;

    @Override
    public List<Node> solve(final Graph graph, Node start) {
        traverse(graph, start);

        return solution;
    }

    @Override
    public SolvingStrategy getSolvingStrategy() {
        return SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC;
    }


    private void traverse(Graph graph, Node start) {
        this.traverse(graph, new HashSet<>(graph.getNodes()), start, null, new ArrayList<>());
    }

    private void traverse(Graph graph, Set<Node> open, Node current, Edge last, List<Node> path) {
        if (solutionFound) {
            return;
        }
        path.add(current);
        open.remove(current);
        if (open.isEmpty()) {
            solutionFound = true;
            solution = path;
            return;
        }

        // System.out.println("nodes unvisited " + open.size());
        // System.out.println("current node " + current.id());

        List<Edge> possibleNext = this.weightedNextEdgeProvider.calcPossibleNext(graph, current, last, open);

        // System.out.println("possible next " + possibleNext);

        for (final Edge edge : possibleNext) {
            Node next = edge.flip(current);
            traverse(graph, new HashSet<>(open), next, edge, new ArrayList<>(path));
        }
    }


}
