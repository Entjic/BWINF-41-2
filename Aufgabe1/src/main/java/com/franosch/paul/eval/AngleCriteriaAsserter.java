package com.franosch.paul.eval;

import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.util.VectorCalculator;

import java.util.List;

public class AngleCriteriaAsserter {

    public boolean fulfillsAngleCriteria(Graph graph, List<Node> path) {
        for (int i = 1; i < path.size() - 1; i++) {
            Node first = path.get(i - 1);
            Node second = path.get(i);
            Node third = path.get(i + 1);
            Edge firstToSecond = graph.getEdge(first, second);
            Edge secondToThird = graph.getEdge(second, third);
            if (!VectorCalculator.matchesAngleCriteria(firstToSecond.vector(second), secondToThird.vector(second))) {
                return false;
            }
        }
        return true;
    }

}
