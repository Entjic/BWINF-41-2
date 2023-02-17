package com.franosch.paul;

import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.FlippingOrderPancakeStackTuple;
import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PancakeStackSorter {

    public PancakeStackSortingResult sort(PancakeStack pancakeStack) {
        Set<FlippingOrderPancakeStackTuple> flippingOrders = new HashSet<>();
        this.solveRecursively(pancakeStack, 0, new FlippingOrder(), flippingOrders);
        FlippingOrderPancakeStackTuple smallest = this.findSmallest(flippingOrders);
        return PancakeStackSortingResult.of(smallest.getFlippingOrder(), smallest.getSolved(), pancakeStack);
    }

    public PancakeStackSortingResult sort(PancakeStack pancakeStack, boolean debugPrints) {
        if (!debugPrints) {
            return this.sort(pancakeStack);
        }
        System.out.println("Input pancake stack " + Arrays.toString(pancakeStack.getPancakes()));
        Set<FlippingOrderPancakeStackTuple> flippingOrders = new HashSet<>();
        this.solveRecursively(pancakeStack, 0, new FlippingOrder(), flippingOrders);
        FlippingOrderPancakeStackTuple smallest = this.findSmallest(flippingOrders);
        System.out.println("Shortest order of flipping operations with length " + smallest.getFlippingOrder().getFlippingOperations().size());
        System.out.println("Order of operations " + smallest.getFlippingOrder().getFlippingOperations());
        System.out.println("Resulting pancake stack " + Arrays.toString(smallest.getSolved().getPancakes()));
        return PancakeStackSortingResult.of(smallest.getFlippingOrder(), smallest.getSolved(), pancakeStack);
    }


    private void solveRecursively(PancakeStack pancakeStack, int counter, FlippingOrder flippingOrder, Set<FlippingOrderPancakeStackTuple> flippingOrders) {

        if (pancakeStack.isSolved()) {
            flippingOrders.add(FlippingOrderPancakeStackTuple.of(flippingOrder, pancakeStack));
            return;
        }

        for (int i = 0; i < pancakeStack.getPancakes().length; i++) {
            PancakeStack clonedPancakeStack = pancakeStack.clone();
            clonedPancakeStack.flip(i);
            FlippingOrder clonedFlippingOrder = flippingOrder.clone();
            clonedFlippingOrder.add(i);
            this.solveRecursively(clonedPancakeStack, counter + 1, clonedFlippingOrder, flippingOrders);
        }
    }

    private FlippingOrderPancakeStackTuple findSmallest(Set<FlippingOrderPancakeStackTuple> set) {
        int smallestValue = Integer.MAX_VALUE;
        FlippingOrderPancakeStackTuple smallest = null;
        for (final FlippingOrderPancakeStackTuple flippingOrderPancakeStackTuple : set) {
            FlippingOrder flippingOrder = flippingOrderPancakeStackTuple.getFlippingOrder();
            if (flippingOrder.getFlippingOperations().size() < smallestValue) {
                smallestValue = flippingOrder.getFlippingOperations().size();
                smallest = flippingOrderPancakeStackTuple;
            }
        }
        return smallest;
    }
}
