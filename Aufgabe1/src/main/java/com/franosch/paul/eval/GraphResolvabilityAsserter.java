package com.franosch.paul.eval;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.util.VectorCalculator;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@RequiredArgsConstructor
public class GraphResolvabilityAsserter {


    public boolean isSolvable(Graph graph) {
        for (final Node node : graph.getNodes().stream().sorted(Comparator.comparingInt(Node::id)).toList()) {
            boolean nodeSolvable = this.isNodeSolvable(graph, node);
            if (!nodeSolvable) {
                return false;
            }
        }
        return true;
    }

    private boolean isNodeSolvable(Graph graph, Node to) {
        for (final Node from : graph.getNodes().stream().sorted(Comparator.comparingInt(Node::id)).toList()) {
            if (from.equals(to)) {
                continue;
            }

            Edge edge = graph.getEdge(from, to);

            int size = this.getNextSize(graph, to, edge);
            if (size == 0) {
                continue;
            }
            // System.out.println("node " + to.id() + " has " + size + " possible next edges from [" + edge.to().id() + "-" + edge.from().id() + "]");
            return true;
        }
        return false;
    }

    private int getNextSize(Graph graph, Node current, Edge last) {
        int counter = 0;
        for (final Edge next : graph.getNeighbouringEdges().get(current)) {
            if (next.equals(last)) {
                continue;
            }
            if (VectorCalculator.matchesAngleCriteria(last.vector(current), next.vector(current))) {
                counter += 1;
            }
        }
        return counter;
    }

}
