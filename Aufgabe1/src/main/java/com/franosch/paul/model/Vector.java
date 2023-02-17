package com.franosch.paul.model;


public record Vector(double x, double y) {

    public static Vector of(Edge edge) {
        Point from = edge.from().point();
        Point to = edge.to().point();
        double x = to.x() - from.x();
        double y = to.y() - from.y();
        return new Vector(x, y);
    }


}
