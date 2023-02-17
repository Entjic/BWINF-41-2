package com.franosch.paul.model;

import java.util.ArrayList;
import java.util.List;

public class FlippingOrder implements Cloneable {
    private final List<Integer> list = new ArrayList<>();

    public void add(Integer integer) {
        this.list.add(integer);
    }

    public List<Integer> getFlippingOperations() {
        return this.list;
    }

    @Override
    public FlippingOrder clone() {
        FlippingOrder clone = new FlippingOrder();
        clone.getFlippingOperations().addAll(this.getFlippingOperations());
        return clone;
    }
}
