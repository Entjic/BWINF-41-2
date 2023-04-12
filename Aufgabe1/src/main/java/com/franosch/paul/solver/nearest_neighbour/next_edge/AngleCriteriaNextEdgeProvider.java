package com.franosch.paul.solver.nearest_neighbour.next_edge;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.util.VectorCalculator;

import java.util.HashSet;
import java.util.Set;

public class AngleCriteriaNextEdgeProvider {


    public Set<Edge> getNext(Graph graph, Node current, Edge last) {
        final Set<Edge> set = new HashSet<>();
        for (final Edge next : graph.getNeighbouringEdges().get(current)) {
            if (last.equals(next)) {
                continue;
            }
            if (VectorCalculator.matchesAngleCriteria(last.vector(current), next.vector(current))) {
                set.add(next);
            }
        }
        return set;
    }



}
