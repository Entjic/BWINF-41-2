package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackSortingResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class PancakeStackGenerator {

    public Set<PancakeStackSortingResult> generateAllOfHeightAndApply(int height,
                                                                      Function<PancakeStack, PancakeStackSortingResult> sort) {
        List<Integer> pancakes = this.generatePancakeListOfHeight(height);
        Set<PancakeStackSortingResult> results = new HashSet<>();
        this.permuteAndApply(pancakes, 0, sort, results);
        return results;
    }

    public Set<PancakeStack> generateAllOfHeight(int height) {
        List<Integer> pancakes = this.generatePancakeListOfHeight(height);
        Set<PancakeStack> pancakeStacks = new HashSet<>();
        permute(pancakes, 0, pancakeStacks);
        return pancakeStacks;
    }

    private List<Integer> generatePancakeListOfHeight(int height) {
        List<Integer> pancakes = new ArrayList<>();
        for (int i = 1; i <= height; i++) {
            pancakes.add(i);
        }
        return pancakes;
    }


    private void permuteAndApply(List<Integer> pancakes, int height,
                                 Function<PancakeStack, PancakeStackSortingResult> sort,
                                 Set<PancakeStackSortingResult> results) {
        for (int i = height; i < pancakes.size(); i++) {
            java.util.Collections.swap(pancakes, i, height);
            permuteAndApply(pancakes, height + 1, sort, results);
            java.util.Collections.swap(pancakes, height, i);
        }
        if (height == pancakes.size() - 1) {
            PancakeStackSortingResult pancakeStackSortingResult =
                    sort.apply(new PancakeStack(pancakes.toArray(new Integer[0])));
            results.add(pancakeStackSortingResult);
        }
    }

    private void permute(List<Integer> pancakes, int height, Set<PancakeStack> results) {
        for (int i = height; i < pancakes.size(); i++) {
            java.util.Collections.swap(pancakes, i, height);
            permute(pancakes, height + 1, results);
            java.util.Collections.swap(pancakes, height, i);
        }
        if (height == pancakes.size() - 1) {
            results.add(new PancakeStack(pancakes.toArray(new Integer[0])));
        }
    }


}
