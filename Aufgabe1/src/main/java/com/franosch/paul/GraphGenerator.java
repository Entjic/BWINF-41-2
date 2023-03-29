package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.model.Point;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class GraphGenerator {


    private final int number;
    private final boolean useTestResources;


    public Graph generateGraph(){
        FileReader fileReader = new FileReader(useTestResources);
        List<Point> points = fileReader.read("wenigerkrumm" + number);
        Set<Node> nodes = this.calcNodes(points);
        Set<Edge> edges = this.calcEdges(nodes);

        System.out.println("nodes " + nodes.size() + " edges " + edges.size());

        return new Graph(nodes, edges);
    }

    public Node findStartNode(Graph graph){
        return graph.getNodes().stream().filter(node -> node.id() == 0).findAny().orElseThrow();
    }


    private Set<Node> calcNodes(List<Point> points) {
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
