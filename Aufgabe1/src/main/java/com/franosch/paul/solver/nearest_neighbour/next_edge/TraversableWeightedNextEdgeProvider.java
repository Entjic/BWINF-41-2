package com.franosch.paul.solver.nearest_neighbour.next_edge;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class TraversableWeightedNextEdgeProvider {

    private final AngleCriteriaNextEdgeProvider nextEdgeProvider;


    public List<Edge> calcPossibleNext(Graph graph, Node current, Edge last, Set<Node> open) {
        List<Edge> possibleNext;

        if (last == null) {
            possibleNext = new ArrayList<>(graph.getNeighbouringEdges().get(current));
        } else {
            possibleNext = new ArrayList<>(this.nextEdgeProvider.getNext(graph, current, last));
        }

        possibleNext.sort((o1, o2) -> {
            Double weightA = o1.weight();
            Double weightB = o2.weight();

            Node nextA = o1.flip(current);
            Node nextB = o2.flip(current);

            Integer numberOfRoutesOverA = graph.getNumberOfRoutes().get(nextA);
            Integer numberOfRoutesOverB = graph.getNumberOfRoutes().get(nextB);

            return Double.compare(weightA * numberOfRoutesOverA, weightB * numberOfRoutesOverB);

        });

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
