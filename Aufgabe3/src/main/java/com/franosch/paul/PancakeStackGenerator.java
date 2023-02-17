package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PancakeStackGenerator {

    public Set<PancakeStack> generateAllOfHeight(int height) {
        List<Integer> pancakes = new ArrayList<>();
        for (int i = 1; i < height + 1; i++) {
            pancakes.add(i);
        }
        Set<PancakeStack> pancakeStacks = new HashSet<>();
        permute(pancakes, 0, pancakeStacks);
        return pancakeStacks;
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
