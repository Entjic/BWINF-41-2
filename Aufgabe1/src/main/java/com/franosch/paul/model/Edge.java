package com.franosch.paul.model;

import java.util.Objects;

public final class Edge {
    private final Node from;
    private final Node to;
    private final Double weight;
    private final Vector vectorFrom;
    private final Vector vectorTo;

    public Edge(Node from, Node to, Double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.vectorFrom = Vector.of(this);
        this.vectorTo = Vector.inverse(this.vectorFrom);
    }

    public Node flip(Node current) {
        if (from.equals(current)) return to;
        if (to.equals(current)) return from;
        throw new IllegalArgumentException("Node " + current.id() + " is not part of edge " + this + " and can therefor not be used to flip!");
    }

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
                ", vectorFrom=" + vectorFrom +
                ", vectorTo=" + vectorTo +
                '}';
    }

    public Node from() {
        return from;
    }

    public Node to() {
        return to;
    }

    public Double weight() {
        return weight;
    }

    public Vector vector(Node base) {
        if (base.equals(to())) {
            return this.vectorTo;
        }
        if (base.equals(from())) {
            return this.vectorFrom;
        }
        throw new IllegalArgumentException();
    }

}