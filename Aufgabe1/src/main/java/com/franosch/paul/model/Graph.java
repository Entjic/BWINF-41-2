package com.franosch.paul.model;

import com.franosch.paul.util.VectorCalculator;
import lombok.Getter;

import java.util.*;

@Getter
public class Graph {

    private final Set<Node> nodes;
    private final Set<Edge> edges;
    private final Map<Node, Set<Node>> neighbours;
    private final Map<Node, Set<Edge>> neighbouringEdges;
    private final Map<Node, Integer> numberOfRoutes;

    public Graph(final Set<Node> nodes, final Set<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.neighbours = this.calcNeighbours(nodes, edges);
        this.neighbouringEdges = this.calcNeighbouringEdges(nodes, edges);
        this.numberOfRoutes = this.calcNumberOfRoutes();
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

    private Map<Node, Integer> calcNumberOfRoutes() {
        Map<Node, Integer> result = new HashMap<>();
        for (final Node node : this.nodes) {
            int i = this.countValidConnectionsOverNode(node);
            result.put(node, i);
        }
        return result;
    }

    private int countValidConnectionsOverNode(Node node) {
        int counter = 0;

        for (final Node from : this.getNodes().stream().sorted(Comparator.comparingInt(Node::id)).toList()) {
            if (from.equals(node)) {
                continue;
            }

            Edge edge = this.getEdge(from, node);

            int size = this.getNextSize(node, edge);
            counter += size;
        }
        return counter;
    }

    private int getNextSize(Node current, Edge last) {
        int counter = 0;
        for (final Edge next : this.getNeighbouringEdges().get(current)) {
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
