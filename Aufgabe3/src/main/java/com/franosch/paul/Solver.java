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
        return this.findPWUE(number, 1);
    }

    public int findPWUE(int number, int amountOfWorstCaseExamples) {
        PancakeFlipper pancakeFlipper = new PancakeFlipper();
        PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);

        System.out.println("generating and solving pancake stacks");
        long preGen = System.currentTimeMillis();
        PWUENumberCalculator pwueNumberCalculator = new PWUENumberCalculator(pancakeSorter, pancakeFlipper);

        Set<PancakeStackSortingResult> result = pwueNumberCalculator.calcPWUE(number, amountOfWorstCaseExamples);

        PancakeStackSortingResult sortingResult = result.stream().findAny().orElseThrow();

        long postGen = System.currentTimeMillis();
        int pwue = sortingResult.getFlippingOrder().getFlippingOperations().size();
        System.out.println("PWUE of number " + number + " is " + pwue);

        System.out.println("Pfandkuchenstapel -> Sortierter Pfandkuchenstapel -> Indizes benötigter Wende-und-Essoperationen");
        for (final PancakeStackSortingResult pancakeStackSortingResult : result) {
            System.out.println("Example Worstcase Pancakestack");
            this.printPancakeStackSortingResult(pancakeStackSortingResult);
        }

        System.out.println("Sorter map contains " + pancakeSorter.getMapEntryCount() + " entries");

        System.out.println("Timings report");
        System.out.println("Time spend finding PWUE nr " + (postGen - preGen) + " ms");
        return pwue;
    }

    public void solveFile(int number, boolean useTestResources, boolean printDebug) {
        PancakeFlipper pancakeFlipper = new PancakeFlipper();
        PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        PancakeStackSorter pancakeSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);
        FileReader fileReader = new FileReader(useTestResources);
        PancakeStack pancakeStack = fileReader.read("pancake" + number);
        PancakeStackSortingResult sort = pancakeSorter.sort(pancakeStack, printDebug);
        this.printPancakeStackSortingResult(sort);
        System.out.println("Benötigte Wende-und-Essoperationen " + sort.getFlippingOrder().getFlippingOperations().size());


    }

    private void printPancakeStackSortingResult(PancakeStackSortingResult result){
        System.out.println("Ursprünglicher Pfandkuchenstapel -> Sortierter Pfandkuchenstapel -> Indizes benötigter Operationen");
        System.out.println(Arrays.toString(result.getPrevious().getPancakes().getPancakes()));
        System.out.println(Arrays.toString(result.getSolved().getPancakes().getPancakes()));
        System.out.println(Arrays.toString(result.getFlippingOrder().getFlippingOperations().toArray(new Byte[0])));

    }


}
