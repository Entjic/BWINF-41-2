package com.franosch.paul.model;

import com.franosch.paul.util.VectorCalculator;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class Graph {

    private final Set<Node> nodes;
    private final Set<Edge> edges;
    private final Map<Node, Set<Node>> neighbours;
    private final Map<Node, Set<Edge>> neighbouringEdges;

    public Graph(final Set<Node> nodes, final Set<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.neighbours = this.calcNeighbours(nodes, edges);
        this.neighbouringEdges = this.calcNeighbouringEdges(nodes, edges);
    }

    private Map<Node, Set<Edge>> calcNeighbouringEdges(final Set<Node> nodes, final Set<Edge> edges) {
        Map<Node, Set<Edge>> map = new HashMap<>();
        for (final Node node : nodes) {
            Set<Edge> set = new HashSet<>();
            for (final Edge edge : edges) {
                if (edge.from().equals(node) || edge.to().equals(node)) {
                    set.add(edge);
                }
            }
            map.put(node, set);
        }
        return map;
    }

    private Map<Node, Set<Node>> calcNeighbours(Set<Node> nodes, Set<Edge> edges) {
        Map<Node, Set<Node>> map = new HashMap<>();
        for (Node node : nodes) {
            Set<Node> set = new HashSet<>();
            for (Edge edge : edges) {
                if (edge.from().equals(node)) {
                    set.add(edge.to());
                }
            }
            map.put(node, set);
        }
        return map;
    }

    public Edge getEdge(Node from, Node to) {
        for (final Edge edge : this.getNeighbouringEdges().get(from)) {
            if (edge.from().equals(from) && edge.to().equals(to) || edge.to().equals(from) && edge.from().equals(to)) {
                return edge;
            }
        }
        System.out.println("smth went wrong finding an edge between node " + from + " and " + to);
        return null;
    }

    public Node getNodeById(int id) {
        return this.nodes.stream().filter(node -> node.id() == id).findAny().orElseThrow();
    }


}
