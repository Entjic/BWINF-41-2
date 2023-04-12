package com.franosch.paul.eval;

import com.franosch.paul.model.Node;

import java.util.List;

public class PathPrinter {

    public void print(List<Node> path) {

        StringBuilder stringBuilder = new StringBuilder("Path ");

        for (final Node node : path) {
            stringBuilder.append(node.id()).append("-");
        }

        System.out.println(stringBuilder.substring(0, stringBuilder.length() - 2));
    }

    public void printPoints(List<Node> path) {
        StringBuilder stringBuilder = new StringBuilder("Path ").append("\n");
        for (int i = 0; i < path.size() - 1; i++) {
            Node from = path.get(i);
            Node to = path.get(i + 1);

            stringBuilder.append(from.point().x())
                    .append(" ")
                    .append(from.point().y())
                    .append(" ")
                    .append(to.point().x())
                    .append(" ")
                    .append(to.point().y())
                    .append("\n");
        }

        System.out.println(stringBuilder);
    }

}
