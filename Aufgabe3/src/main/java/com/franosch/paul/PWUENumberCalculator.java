package com.franosch.paul;


import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackData;
import com.franosch.paul.model.PancakeStackSortingResult;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class PWUENumberCalculator {

    private final PancakeStackSorter pancakeStackSorter;
    private final PancakeFlipper pancakeFlipper;

    private final Map<Integer, AtomicInteger> heightToPWUE = new ConcurrentHashMap<>(); // height to pwue nr
    private final Map<PancakeStackData, Integer> flippingOrderLengthMap = new ConcurrentHashMap<>(); // pancake stack to flipping order length
    private final static Byte[][] cleanseOperations = new Byte[][]{
            new Byte[]{}, // 0
            new Byte[]{}, // 1
            new Byte[]{}, // 2
            new Byte[]{3, 2}, // 3
            new Byte[]{3, 2, 2}, // 4
            new Byte[]{4, 3, 3, 2}, // 5
            new Byte[]{4, 3, 3, 2, 2}, // 6
            new Byte[]{5, 4, 4, 3, 3, 2}, // 7
            new Byte[]{5, 4, 4, 3, 3, 2, 2},// 8
            new Byte[]{6, 5, 5, 4, 4, 3, 3, 2}, // 9
            new Byte[]{6, 5, 5, 4, 4, 3, 3, 2, 2}, // 10
            new Byte[]{7, 6, 6, 5, 5, 4, 4, 3, 3, 2}, // 11
            new Byte[]{7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2}, // 12
            new Byte[]{8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2} // 13
    };


    private int startedTasks = 0;
    private final AtomicInteger finishedTasks = new AtomicInteger(0);
    private final static int BATCH_SIZE = 10000;


    // bottom up pwue number generation
    public PancakeStackSortingResult calcPWUE(int height) {
        int pwue = this.calcPWUEForHeight(height);
        return this.findAndSolveStackWithPWUECountAmount(pwue, height);
    }

    public Set<PancakeStackSortingResult> calcPWUE(int height, int amount) {
        int pwue = this.calcPWUEForHeight(height);
        Set<PancakeStackSortingResult> set = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            set.add(this.findAndSolveStackWithPWUECountAmount(pwue, height));
        }
        return set;
    }

    private PancakeStackSortingResult findAndSolveStackWithPWUECountAmount(int pwue, int height) {
        List<Byte> pancakes = this.generateInitialPancakes(height);
        PancakeStack pancakeStack;
        PancakeStackSortingResult result;
        do {
            Collections.shuffle(pancakes);
            pancakeStack = new PancakeStack(pancakes.toArray(Byte[]::new));
            result = this.pancakeStackSorter.sort(pancakeStack);
        } while (result.getFlippingOrder().getFlippingOperations().size() != pwue);
        return result;
    }

    public int calcPWUEForHeight(int height) {
        this.flippingOrderLengthMap.put(new PancakeStackData(new Byte[]{1}), 0);
        this.heightToPWUE.put(1, new AtomicInteger(0));

        this.flippingOrderLengthMap.put(new PancakeStackData(new Byte[]{1, 2}), 1);
        this.heightToPWUE.put(2, new AtomicInteger(1));
        for (int i = 3; i <= height; i++) {
            // System.out.println("NOW GENERATING MAP FOR " + i);
            this.permute(i, i != height);
            while (startedTasks - finishedTasks.get() != 0) {
                try {
                    System.out.println("waiting for " + (startedTasks - finishedTasks.get()) + " tasks");
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // System.out.println(this.heightToPWUE);
            // System.out.println("pwue by height " + this.heightToPWUE);
            System.out.println(countPancakeStackEntriesByLength());
            if (i != height) {
                this.cleanse(i, height);
                System.out.println(countPancakeStackEntriesByLength());
            }
            // System.out.println(flippingOrderLengthMap);
        }
        return this.heightToPWUE.get(height).get();
    }


    private Map<Integer, Integer> countPancakeStackEntriesByLength() {
        Map<Integer, Integer> map = new HashMap<>();

        for (final PancakeStackData pancakeStackData : this.flippingOrderLengthMap.keySet()) {
            Integer integer = map.getOrDefault(pancakeStackData.getPancakes().length, 0);
            integer++;
            map.put(pancakeStackData.getPancakes().length, integer);
        }
        return map;
    }

    private void cleanse(int current, int max) {

        this.flippingOrderLengthMap.entrySet().removeIf(entry -> {
            int entryHeight = entry.getKey().getPancakes().length;
            int entryValue = entry.getValue();

            if (entryHeight <= current - 1) {
                return true;
            }

            int pwue = this.heightToPWUE.get(entryHeight).get();
            return entryValue <= pwue - cleanseOperations[max][current - 1];
        });

    }

    private void permute(int height, boolean writeToMap) {
        List<Byte> pancakes = this.generateInitialPancakes(height);
        permute(pancakes, 0, height, writeToMap);
    }

    private List<Byte> generateInitialPancakes(int height) {
        List<Byte> bytes = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            byte b = (byte) i;
            bytes.add(b);
        }
        return bytes;
    }

    private void permute(List<Byte> pancakes, int height, int initialHeight, boolean writeToMap) {
        for (int i = height; i < pancakes.size(); i++) {
            java.util.Collections.swap(pancakes, i, height);
            permute(pancakes, height + 1, initialHeight, writeToMap);
            java.util.Collections.swap(pancakes, height, i);
        }
        if (height == pancakes.size() - 1) {

            PancakeStack pancakeStack = new PancakeStack(pancakes.toArray(new Byte[0]));
            while (startedTasks - finishedTasks.get() > BATCH_SIZE) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // TODO: 17.04.2023 this is very hacky
            }
            startedTasks++;
            this.sortAndApplyResults(pancakeStack, writeToMap, height, initialHeight)
                    .subscribeOn(Schedulers.parallel())
                    .doOnNext(integer -> finishedTasks.incrementAndGet())
                    .subscribe();
        }

    }


    private Mono<Integer> sortAndApplyResults(PancakeStack pancakeStack, boolean writeToMap, int height, int initialHeight) {
        Mono<Integer> mono = !writeToMap && pancakeStack.getPancakes().getPancakes()[0] == height + 1 ?
                Mono.just(-1) : Mono.just(this.sort(pancakeStack, writeToMap));
        // System.out.println(Arrays.toString(pancakeStack.getPancakes().getPancakes()));
        return mono.doOnNext(requiredFlippingOperationsCount -> {
            if (requiredFlippingOperationsCount == -1) {
                return;
            }
            AtomicInteger currentWorstCase = heightToPWUE.getOrDefault(initialHeight, new AtomicInteger(Integer.MIN_VALUE));
            int currentWorstCaseValue = currentWorstCase.get();
            // System.out.println("current worst " + currentWorstCaseValue + " current required operations " + requiredFlippingOperationsCount);
            if (requiredFlippingOperationsCount <= currentWorstCaseValue) {
                return;
            }
            // System.out.println("current worst " + currentWorstCaseValue + " current required operations " + requiredFlippingOperationsCount);
            currentWorstCase.compareAndSet(currentWorstCaseValue, requiredFlippingOperationsCount);
            this.heightToPWUE.put(initialHeight, currentWorstCase);
            // System.out.println("added to pwue map key " + initialHeight + " value " + currentWorstCase.get());
            // System.out.println("pwue map " + heightToPWUE);
        });
    }

    public int sort(PancakeStack pancakeStack, boolean writeToMap) {
        return this.optimalFlippingOrder(pancakeStack, writeToMap);
    }

    private int optimalFlippingOrder(PancakeStack pancakeStack, boolean writeToMap) {
        // System.out.println("current stack " + pancakeStack.getNormalizedPancakes());

        if (pancakeStack.isSolved()) {
            // System.out.println("stack is already solved");
            // this.flippingOrderLengthMap.put(pancakeStack.getNormalizedPancakes(), 0);
            return -1;
        }
        // Arrays.equals(pancakeStack.getNormalizedPancakes().getPancakes(), new Byte[]{7, 6, 5, 4, 1, 3, 2})
        Set<Integer> possibleNextOperations = new HashSet<>();
        // System.out.println("map " + this.flippingOrderLengthMap);
        for (byte i = 0; i < pancakeStack.getNormalizedPancakes().getPancakes().length; i++) {
            PancakeStack clonedPancakeStack = pancakeStack.clone();
            this.pancakeFlipper.flip(clonedPancakeStack, i);

            if (clonedPancakeStack.isSolved()) {
                // if (writeToMap) {
                this.flippingOrderLengthMap.put(pancakeStack.getNormalizedPancakes(), 1);
                // }
                return 1;
            }

            // System.out.println("flipped at " + i + " -> " + clonedPancakeStack.getNormalizedPancakes());
            if (!this.flippingOrderLengthMap.containsKey(clonedPancakeStack.getNormalizedPancakes())) {
                // System.out.println("map does not contain key");
                return -1;
            }
            int operations = this.flippingOrderLengthMap.get(clonedPancakeStack.getNormalizedPancakes());
            possibleNextOperations.add(operations);
            // System.out.println("map contains key, value is " + operations);
            // System.out.println("added to options " + (integer + 1));
        }

        if (possibleNextOperations.isEmpty()) return -1;
        int best = this.findBest(possibleNextOperations) + 1;

        if (writeToMap) {
            this.flippingOrderLengthMap.put(pancakeStack.getNormalizedPancakes(), best);
        }
        return best;
    }

    private int findBest(Set<Integer> flippingOrders) {
        int length = Integer.MAX_VALUE;
        for (Integer integer : flippingOrders) {
            if (integer < length) {
                length = integer;
            }
        }
        return length;
    }
}
