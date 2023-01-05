package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.model.Point;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class Solver {

    private final int number;
    private final boolean useTestResources;

    public void solve() {
        FileReader fileReader = new FileReader(useTestResources);
        Set<Point> points = fileReader.read("wenigerkrumm" + number);
        Set<Node> nodes = this.calcNodes(points);
        Set<Edge> edges = this.calcEdges(nodes);

        Graph graph = new Graph(nodes, edges);

        for (final Node from : graph.getNodes()) {
            for (final Node to : graph.getNeighbours().get(from)) {
                System.out.println("Node " + from + " is neighbour to " + to + " with distance " + graph.getEdge(from, to).weight());
            }
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
