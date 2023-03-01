package com.franosch.paul.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FlippingOrder implements Cloneable {
    @Getter
    private final List<Byte> flippingOperations = new ArrayList<>();

    public FlippingOrder add(Byte integer) {
        this.flippingOperations.add(integer);
        return this;
    }

    public FlippingOrder append(FlippingOrder flippingOrder) {
        FlippingOrder copy = new FlippingOrder();
        copy.getFlippingOperations().addAll(this.getFlippingOperations());
        copy.getFlippingOperations().addAll(flippingOrder.getFlippingOperations());
        return copy;
    }

    @Override
    public FlippingOrder clone() {
        FlippingOrder clone = new FlippingOrder();
        clone.getFlippingOperations().addAll(this.getFlippingOperations());
        return clone;
    }

    @Override
    public String toString() {
        return "FlippingOrder{" +
                "flippingOperations=" + flippingOperations +
                '}';
    }
}
