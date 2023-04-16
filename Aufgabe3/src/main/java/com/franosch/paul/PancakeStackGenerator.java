package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class PancakeStackGenerator {

    private AtomicInteger currentWorstCase;
    private final Set<CompletableFuture<Void>> allFutures = ConcurrentHashMap.newKeySet();

    @SneakyThrows
    public Set<PancakeStackSortingResult> generateAllOfHeightAndApply(int height,
                                                                      Function<PancakeStack, PancakeStackSortingResult> sort,
                                                                      boolean useMultiThreading) {
        this.currentWorstCase = new AtomicInteger(0);
        List<Byte> pancakes = this.generatePancakeListOfHeight(height);
        Set<PancakeStackSortingResult> results = ConcurrentHashMap.newKeySet();
        this.permuteAndApply(pancakes, 0, sort, results, useMultiThreading);
        System.out.println("futures " + allFutures.size());
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(allFutures.toArray(CompletableFuture[]::new));
        completableFuture.join();
        System.out.println("futures done");
        return results;
    }

    public Set<PancakeStack> generateAllOfHeight(int height) {
        List<Byte> pancakes = this.generatePancakeListOfHeight(height);
        Set<PancakeStack> pancakeStacks = new HashSet<>();
        permute(pancakes, 0, pancakeStacks);
        return pancakeStacks;
    }

    private List<Byte> generatePancakeListOfHeight(int height) {
        List<Byte> pancakes = new ArrayList<>();
        for (byte i = 1; i <= height; i++) {
            pancakes.add(i);
        }
        return pancakes;
    }


    private void permuteAndApply(List<Byte> pancakes, int height,
                                 Function<PancakeStack, PancakeStackSortingResult> sort,
                                 Set<PancakeStackSortingResult> results, boolean useAsync) {
        for (int i = height; i < pancakes.size(); i++) {
            Collections.swap(pancakes, i, height);
            permuteAndApply(pancakes, height + 1, sort, results, useAsync);
            Collections.swap(pancakes, height, i);
        }
        if (height == pancakes.size() - 1) {
            PancakeStack pancakeStack = new PancakeStack(pancakes.toArray(new Byte[0]));
            if (useAsync) {
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> addToResult(pancakeStack, sort, results));
                allFutures.add(completableFuture);
                return;
            }
            addToResult(pancakeStack, sort, results);
        }
    }

    private void addToResult(final PancakeStack pancakeStack,
                             final Function<PancakeStack, PancakeStackSortingResult> sort,
                             final Set<PancakeStackSortingResult> results) {
        PancakeStackSortingResult pancakeStackSortingResult = sort.apply(pancakeStack);
        int size = pancakeStackSortingResult.getFlippingOrder().getFlippingOperations().size();
        int currentMax = currentWorstCase.get();
        while (size > currentMax) {
            if (currentWorstCase.compareAndSet(currentMax, size)) {
                results.clear();
                results.add(pancakeStackSortingResult);
                System.out.println("new biggest int is " + size);
                break;
            }
            currentMax = currentWorstCase.get();
        }
        // results.add(pancakeStackSortingResult);
    }

    private void permute(List<Byte> pancakes, int height, Set<PancakeStack> results) {
        for (int i = height; i < pancakes.size(); i++) {
            java.util.Collections.swap(pancakes, i, height);
            permute(pancakes, height + 1, results);
            java.util.Collections.swap(pancakes, height, i);
        }
        if (height == pancakes.size() - 1) {
            results.add(new PancakeStack(pancakes.toArray(new Byte[0])));
        }
    }


}
