package com.franosch.paul.solver;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.util.VectorCalculator;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class NearestNeighbourHeuristic implements Solver {


    private boolean solutionFound = false;

    private List<Node> solution = null;

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

        List<Edge> possibleNext = this.calcPossibleNext(graph, current, last, open);

        // System.out.println("possible next " + possibleNext);

        for (final Edge edge : possibleNext) {
            Node next = edge.flip(current);
            traverse(graph, new HashSet<>(open), next, edge, new ArrayList<>(path));
        }
    }

    private List<Edge> calcPossibleNext(Graph graph, Node current, Edge last, Set<Node> open) {
        List<Edge> possibleNext;

        if (last == null) {
            possibleNext = new ArrayList<>(graph.getNeighbouringEdges().get(current));
        } else {
            possibleNext = new ArrayList<>(this.getNext(graph, current, last));
        }

        possibleNext.sort(Comparator.comparingDouble(Edge::weight));

        List<Edge> result = new ArrayList<>();

        for (final Edge edge : possibleNext) {
            Node next = edge.flip(current);
            if (open.contains(next)) {
                result.add(edge);
            }
        }
        return result;

    }

    public Set<Edge> getNext(Graph graph, Node current, Edge last) {
        final Set<Edge> set = new HashSet<>();
        for (final Edge next : graph.getNeighbouringEdges().get(current)) {
            if (VectorCalculator.matchesAngleCriteria(last.vector(current), next.vector(current))) {
                set.add(next);
            }
        }
        return set;
    }

}
