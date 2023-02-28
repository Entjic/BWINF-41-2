package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class Solver {

    public int findPWUE(int number) {
        PancakeFlipper pancakeFlipper = new PancakeFlipper();
        PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);

        PancakeStackGenerator pancakeStackGenerator = new PancakeStackGenerator();
        System.out.println("generating pancake stacks");
        long preGen = System.currentTimeMillis();
        Set<PancakeStack> pancakeStacks = pancakeStackGenerator.generateAllOfHeight(number);
        long postGen = System.currentTimeMillis();
        System.out.println("sorting pancake stacks");
        List<PancakeStackSortingResult> sorted = new ArrayList<>();
        for (final PancakeStack pancakeStack : pancakeStacks) {
            PancakeStackSortingResult result = pancakeSorter.sort(pancakeStack);
            sorted.add(result);
        }
        int highestOperations = this.findHighest(sorted).getFlippingOrder().getFlippingOperations().size();
        sorted.stream().filter(result -> {
            int size = result.getFlippingOrder().getFlippingOperations().size();
            return size == highestOperations;
        }).forEach(result -> System.out.println("Pancake stack "
                + result.getPrevious().getPancakes() + " -> " + result.getSolved().getPancakes()
                + " operations " + result.getFlippingOrder().getFlippingOperations()));
        long postSort = System.currentTimeMillis();
        long count = sorted.stream().filter(result -> {
            int size = result.getFlippingOrder().getFlippingOperations().size();
            return size == highestOperations;
        }).count();
        System.out.println("PWUE of number " + number + " is " + highestOperations);
        System.out.println("There are " + count + " worst case stacks");

        System.out.println("Timings report");
        System.out.println("Time spend finding PWUE nr " + (postSort - preGen));
        System.out.println("Time spend generating pancake stacks " + (postGen - preGen));
        System.out.println("Time spend sorting pancake stacks " + (postSort - postGen));

        return highestOperations;
    }


    public void solveFile(int number, boolean useTestResources) {
        PancakeFlipper pancakeFlipper = new PancakeFlipper();
        PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);
        FileReader fileReader = new FileReader(useTestResources);
        PancakeStack pancakeStack = fileReader.read("pancake" + number);
        pancakeSorter.sort(pancakeStack, true);
    }

    private PancakeStackSortingResult findHighest(Collection<PancakeStackSortingResult> set) {
        int smallestValue = Integer.MIN_VALUE;
        PancakeStackSortingResult highest = null;
        for (final PancakeStackSortingResult flippingOrderPancakeStackTuple : set) {
            FlippingOrder flippingOrder = flippingOrderPancakeStackTuple.getFlippingOrder();
            if (flippingOrder.getFlippingOperations().size() > smallestValue) {
                smallestValue = flippingOrder.getFlippingOperations().size();
                highest = flippingOrderPancakeStackTuple;
            }
        }
        return highest;
    }


}
