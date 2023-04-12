package com.franosch.paul.solver.nearest_neighbour.next_edge;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class WeightedNextEdgeProvider {

    private final AngleCriteriaNextEdgeProvider nextEdgeProvider;

    public List<Edge> calcPossibleNext(Graph graph, Node current, Edge last, Set<Node> open) {
        List<Edge> possibleNext;

        if (last == null) {
            possibleNext = new ArrayList<>(graph.getNeighbouringEdges().get(current));
        } else {
            possibleNext = new ArrayList<>(this.nextEdgeProvider.getNext(graph, current, last));
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


}
