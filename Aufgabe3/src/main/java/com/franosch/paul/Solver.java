package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.FlippingOrderPancakeStackTuple;
import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class Solver {

    public int findPWUE(int number) {
        PancakeStackSorter pancakeSorter = new PancakeStackSorter();

        PancakeStackGenerator pancakeStackGenerator = new PancakeStackGenerator();
        Set<PancakeStack> pancakeStacks = pancakeStackGenerator.generateAllOfHeight(number);
        List<PancakeStackSortingResult> sorted = new ArrayList<>();
        for (final PancakeStack pancakeStack : pancakeStacks) {
            PancakeStackSortingResult result = pancakeSorter.sort(pancakeStack);
            sorted.add(result);
        }
        Collections.shuffle(sorted);
        PancakeStackSortingResult highest = this.findHighest(sorted);
        System.out.println("PWUE of number " + number + " is " + highest.getFlippingOrder().getFlippingOperations().size());
        System.out.println("Pancake stack " + Arrays.toString(highest.getPrevious().getPancakes()) + " -> " + Arrays.toString(highest.getSolved().getPancakes()));
        System.out.println("List of flipping operations " + highest.getFlippingOrder().getFlippingOperations());
        return highest.getFlippingOrder().getFlippingOperations().size();
    }


    public void solveFile(int number, boolean useTestResources) {
        PancakeStackSorter pancakeSorter = new PancakeStackSorter();
        FileReader fileReader = new FileReader(useTestResources);
        PancakeStack pancakeStack = fileReader.read("pancake" + number);
        pancakeSorter.sort(pancakeStack);
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
