package com.franosch.paul.eval;

import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;

import java.util.List;

public class SolutionEvaluator {

    public Double evaluate(Graph graph, List<Node> path) {
        Double cost = 0.0;
        final Node start = path.get(0);
        Node last = start;
        for (final Node node : path) {
            if (node.equals(start)) {
                continue;
            }
            cost += graph.getEdge(last, node).weight();
            last = node;
        }
        return cost;
    }

}
