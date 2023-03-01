package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.FlippingOrder;
import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class Solver {

    public int findPWUE(int number) {
        PancakeFlipper pancakeFlipper = new PancakeFlipper();
        PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);

        PancakeStackGenerator pancakeStackGenerator = new PancakeStackGenerator();
        System.out.println("generating and solving pancake stacks");
        long preGen = System.currentTimeMillis();
        Set<PancakeStackSortingResult> sorted = pancakeStackGenerator
                .generateAllOfHeightAndApply(number,
                        pancakeSorter::sort);
        long postGen = System.currentTimeMillis();
        int highestOperations = sorted
                .stream()
                .findFirst()
                .orElseThrow(IllegalStateException::new)
                .getFlippingOrder()
                .getFlippingOperations()
                .size();
        System.out.println("10 random chosen worst case pancake stacks");
        sorted.stream()
                .sorted(new RandomComparator<>()) // random shuffle
                .limit(10)
                .forEach(result -> System.out.println("Pancake stack "
                        + result.getPrevious().getPancakes() + " -> " + result.getSolved().getPancakes()
                        + " operations " + result.getFlippingOrder().getFlippingOperations()));
        long count = sorted.stream().filter(result -> {
            int size = result.getFlippingOrder().getFlippingOperations().size();
            return size == highestOperations;
        }).count();
        System.out.println("PWUE of number " + number + " is " + highestOperations);
        System.out.println("There are " + count + " worst case stacks");

        System.out.println("Sorter map contains " + pancakeSorter.getMapEntryCount() + " entries");

        System.out.println("Timings report");
        System.out.println("Time spend finding PWUE nr " + (postGen - preGen) + " ms");

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
