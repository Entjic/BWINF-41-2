package com.franosch.paul.eval;

import com.franosch.paul.model.Node;

import java.util.List;

public class PathPrinter {

    public String generatePathString(List<Node> path){
        StringBuilder stringBuilder = new StringBuilder("Path ");

        for (final Node node : path) {
            stringBuilder.append(node.id()).append("-");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    public void print(List<Node> path) {
        System.out.println(this.generatePathString(path));
    }

    public String generatePointsString(List<Node> path){
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

        return stringBuilder.toString();
    }

    public void printPoints(List<Node> path) {
        String string = this.generatePointsString(path);
        System.out.println(string);
    }

}
