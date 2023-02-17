package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.model.Point;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class Solver {

    private final int number;
    private final boolean useTestResources;

    private boolean solutionFound = false;

    public void solve() {
        FileReader fileReader = new FileReader(useTestResources);
        Set<Point> points = fileReader.read("wenigerkrumm" + number);
        Set<Node> nodes = this.calcNodes(points);
        Set<Edge> edges = this.calcEdges(nodes);

        System.out.println("nodes " + nodes.size() + " edges " + edges.size());

        Graph graph = new Graph(nodes, edges);

        Node start = graph.getNodes().stream().filter(node -> node.id() == 0).findAny().get();

        traverse(graph, start);
    }

    private Double evaluate(Graph graph, List<Node> path) {
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

    private void traverse(Graph graph, Node start) {
        Edge randomSetElement = getRandomSetElement(graph.getNeighbouringEdges().get(start));
        this.traverse(graph, new HashSet<>(graph.getNodes()), start, randomSetElement, new ArrayList<>());
    }

    private void traverse(Graph graph, Set<Node> open, Node current, Edge last, List<Node> path) {
        if (solutionFound) {
            return;
        }
        path.add(current);
        open.remove(current);
        if (open.isEmpty()) {
            solutionFound = true;
            System.out.println(evaluate(graph, path));
            return;
        }
        List<Edge> possibleNext = new ArrayList<>(graph.getNext(current, last));
        possibleNext.sort(Comparator.comparingDouble(Edge::weight));
        for (final Edge edge : possibleNext) {
            Node next = edge.flip(current);
            if (!open.contains(next)) {
                continue;
            }
            traverse(graph, new HashSet<>(open), next, edge, new ArrayList<>(path));
        }
    }


    private Set<Node> calcNodes(Set<Point> points) {
        Set<Node> nodes = new HashSet<>();
        int i = 0;
        for (final Point point : points) {
            nodes.add(new Node(i, point));
            i++;
        }
        return nodes;
    }

    private <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }


    private Set<Edge> calcEdges(Set<Node> nodes) {
        Set<Edge> edges = new HashSet<>();
        Set<Node> current = new HashSet<>(nodes);
        for (final Node from : nodes) {
            current.remove(from);
            for (final Node to : current) {
                Edge edge = new Edge(from, to, this.length(from.point(), to.point()));
                edges.add(edge);
            }
        }
        return edges;
    }

    private Double length(Point a, Point b) {
        return Math.sqrt((a.x() - b.x()) * (a.x() - b.x()) + (a.y() - b.y()) * (a.y() - b.y()));
    }


}
