package com.franosch.paul.model;

import java.util.Objects;

public record Edge(Node from, Node to, Double weight) {

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Edge edge = (Edge) o;
        return (from.equals(edge.from) && to.equals(edge.to) || from.equals(edge.to) && to.equals(edge.from)) && weight.equals(edge.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", weight=" + weight +
                '}';
    }
}