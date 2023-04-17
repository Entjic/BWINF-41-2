package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class Solver {

    public int findPWUE(int number) {
        PancakeFlipper pancakeFlipper = new PancakeFlipper();
        PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);

        System.out.println("generating and solving pancake stacks");
        long preGen = System.currentTimeMillis();
        PWUENumberCalculator pwueNumberCalculator = new PWUENumberCalculator(pancakeSorter, pancakeFlipper);

        PancakeStackSortingResult result = pwueNumberCalculator.calcPWUE(number);
        long postGen = System.currentTimeMillis();
        int pwue = result.getFlippingOrder().getFlippingOperations().size();
        System.out.println("PWUE of number " + number + " is " + pwue);
        System.out.println("Example worst case pancake stack " + result.getPrevious());
        System.out.println("Solved " + result.getSolved());
        System.out.println("Flippings operations to solve pancake stack " + result.getFlippingOrder());
        System.out.println("Sorter map contains " + pancakeSorter.getMapEntryCount() + " entries");

        System.out.println("Timings report");
        System.out.println("Time spend finding PWUE nr " + (postGen - preGen) + " ms");
        // pancakeSorter.printStats();
        return pwue;
    }


    public void solveFile(int number, boolean useTestResources) {
        PancakeFlipper pancakeFlipper = new PancakeFlipper();
        PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);
        FileReader fileReader = new FileReader(useTestResources);
        PancakeStack pancakeStack = fileReader.read("pancake" + number);
        pancakeSorter.sort(pancakeStack, true);
    }

    private static final class RandomComparator<T> implements Comparator<T> {

        private final Map<T, Integer> map = new IdentityHashMap<>();
        private final Random random;

        public RandomComparator() {
            this(new Random());
        }

        public RandomComparator(Random random) {
            this.random = random;
        }

        @Override
        public int compare(T t1, T t2) {
            return Integer.compare(valueFor(t1), valueFor(t2));
        }

        private int valueFor(T t) {
            synchronized (map) {
                return map.computeIfAbsent(t, ignore -> random.nextInt());
            }
        }

    }


}
