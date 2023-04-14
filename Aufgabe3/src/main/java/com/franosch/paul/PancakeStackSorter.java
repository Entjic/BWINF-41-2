package com.franosch.paul;

import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackData;
import com.franosch.paul.model.PancakeStackSortingResult;

import java.util.*;

public class PancakeStackSorter {
    private final PancakeFlipper pancakeFlipper;
    private final PancakeFlippingOrderApplier pancakeFlippingOrderApplier;

    private final Map<PancakeStackData, FlippingOrder> flippingOrderMap = new HashMap<>();
    private final Map<Integer, Long> factorial = new HashMap<>();

    private final static int PURGE_MAP_CYCLE = 100000;
    private int counter = 0;

    public PancakeStackSorter(final PancakeFlipper pancakeFlipper, final PancakeFlippingOrderApplier pancakeFlippingOrderApplier) {
        this.pancakeFlipper = pancakeFlipper;
        this.pancakeFlippingOrderApplier = pancakeFlippingOrderApplier;

        for (int i = 2; i < 15; i++) {
            factorial.put(i, this.factorial(i));
        }

    }

    public int getMapEntryCount() {
        return this.flippingOrderMap.size();
    }


    public PancakeStackSortingResult sort(PancakeStack pancakeStack) {
        FlippingOrder flippingOrder = this.optimalFlippingOrder(pancakeStack, new FlippingOrder());
        PancakeStack solved = this.pancakeFlippingOrderApplier.apply(pancakeStack.clone(), flippingOrder);
        // System.out.println(getMapEntryCount());
        if (counter++ == PURGE_MAP_CYCLE) {
            clearLowerLevelsOfMap(pancakeStack.getPancakes().getPancakes().length);
            counter = 0;
        }
        return PancakeStackSortingResult.of(flippingOrder, solved, pancakeStack);
    }

    public void clearLowerLevelsOfMap(int length) {
        List<Integer> lengthsToRemove = new ArrayList<>();
        for (final Map.Entry<Integer, Integer> entry : this.countPancakeStackEntriesByLength().entrySet()) {
            Integer key = entry.getKey();
            if (key == length) continue;
            if (entry.getValue() == calcNumberOfDifferentPancakeStacks(key)) {
                lengthsToRemove.add(key);
            }
        }
        if (lengthsToRemove.size() == 0) return;
        Integer biggest = this.max(lengthsToRemove);
        lengthsToRemove.remove(biggest);
        lengthsToRemove.add(length);
        for (final PancakeStackData pancakeStackData : new HashSet<>(flippingOrderMap.keySet())) {
            if (lengthsToRemove.contains(pancakeStackData.getPancakes().length)) {
                flippingOrderMap.remove(pancakeStackData);
            }
        }
    }

    private int max(Collection<Integer> collection) {
        int max = Integer.MIN_VALUE;
        for (final Integer integer : collection) {
            if (integer > max) {
                max = integer;
            }
        }
        return max;
    }

    private long calcNumberOfDifferentPancakeStacks(int size) {
        return this.factorial.get(size) - 1;
    }

    private long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }

    public void printStats() {
        Map<Integer, Integer> map = countPancakeStackEntriesByLength();

        for (final Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            System.out.println("There are " + integerIntegerEntry.getValue() + " entries for keys with length " + integerIntegerEntry.getKey());
        }
    }

    private Map<Integer, Integer> countPancakeStackEntriesByLength() {
        Map<Integer, Integer> map = new HashMap<>();

        for (final PancakeStackData pancakeStackData : flippingOrderMap.keySet()) {
            Integer integer = map.getOrDefault(pancakeStackData.getPancakes().length, 0);
            integer++;
            map.put(pancakeStackData.getPancakes().length, integer);
        }
        return map;
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
