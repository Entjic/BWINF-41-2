package com.franosch.paul;

import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackData;
import com.franosch.paul.model.PancakeStackSortingResult;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class PancakeStackSorter {
    private final PancakeFlipper pancakeFlipper;
    private final PancakeFlippingOrderApplier pancakeFlippingOrderApplier;

    private final Map<PancakeStackData, FlippingOrder> flippingOrderMap = new HashMap<>();

    public int getMapEntryCount() {
        return this.flippingOrderMap.size();
    }


    public PancakeStackSortingResult sort(PancakeStack pancakeStack) {
        FlippingOrder flippingOrder = this.optimalFlippingOrder(pancakeStack, new FlippingOrder());
        PancakeStack solved = this.pancakeFlippingOrderApplier.apply(pancakeStack.clone(), flippingOrder);
        return PancakeStackSortingResult.of(flippingOrder, solved, pancakeStack);
    }

    public PancakeStackSortingResult sort(PancakeStack pancakeStack, boolean debugPrints) {
        if (!debugPrints) {
            return this.sort(pancakeStack);
        }
        System.out.println("Input pancake stack " + pancakeStack.getNormalizedPancakes());
        FlippingOrder flippingOrder = this.optimalFlippingOrder(pancakeStack, new FlippingOrder());
        System.out.println("Shortest order of flipping operations with length " + flippingOrder.getFlippingOperations().size());
        System.out.println("Order of operations " + flippingOrder.getFlippingOperations());
        PancakeStack solved = this.pancakeFlippingOrderApplier.apply(pancakeStack, flippingOrder);
        System.out.println("Resulting pancake stack " + solved);
        System.out.println("Map entry count " + flippingOrderMap.size());
        return PancakeStackSortingResult.of(flippingOrder, solved, pancakeStack);
    }

    private FlippingOrder optimalFlippingOrder(PancakeStack pancakeStack, FlippingOrder flippingOrder) {
        if (pancakeStack.isSolved()) {
            return flippingOrder;
        }
        if (this.flippingOrderMap.containsKey(pancakeStack.getNormalizedPancakes())) {
            return flippingOrder.append(this.flippingOrderMap.get(pancakeStack.getNormalizedPancakes()));
        }
        Set<FlippingOrder> options = new HashSet<>();
        for (byte i = 0; i < pancakeStack.getNormalizedPancakes().getPancakes().length; i++) {
            PancakeStack clonedPancakeStack = pancakeStack.clone();
            this.pancakeFlipper.flip(clonedPancakeStack, i);
            FlippingOrder clonedFlippingOrder = flippingOrder.clone();
            clonedFlippingOrder.add(i);
            options.add(this.optimalFlippingOrder(clonedPancakeStack, clonedFlippingOrder));
        }

        FlippingOrder best = findBest(options);

        this.flippingOrderMap.put(pancakeStack.getNormalizedPancakes(),
                slice(calcSlicingStart(best, pancakeStack),
                        best));
        return best;
    }

    private FlippingOrder findBest(Set<FlippingOrder> flippingOrders) {
        int length = Integer.MAX_VALUE;
        FlippingOrder best = null;
        for (FlippingOrder flippingOrder : flippingOrders) {
            if (flippingOrder.getFlippingOperations().size() < length) {
                length = flippingOrder.getFlippingOperations().size();
                best = flippingOrder;
            }
        }
        return best;
    }

    private FlippingOrder slice(int from, FlippingOrder flippingOrder) {
        if (from == 0) {
            return flippingOrder.clone();
        }
        FlippingOrder result = new FlippingOrder();
        for (int i = from; i < flippingOrder.getFlippingOperations().size(); i++) {
            result.add(flippingOrder.getFlippingOperations().get(i));
        }
        return result;
    }

    private int calcSlicingStart(FlippingOrder flippingOrder, PancakeStack pancakeStack) {
        for (int i = flippingOrder.getFlippingOperations().size() - 1; i >= 0; i--) {
            PancakeStack applied = this
                    .pancakeFlippingOrderApplier
                    .apply(pancakeStack.clone(), slice(i, flippingOrder));
            if (applied.isSolved()) {
                return i;
            }
        }
        throw new IllegalStateException();
    }
}
