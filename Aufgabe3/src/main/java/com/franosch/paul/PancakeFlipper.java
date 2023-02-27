package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackData;

import java.util.Arrays;

public class PancakeFlipper {

    public void flip(PancakeStack pancakeStack, int index) { // flips between index - 1 and index
        Integer[] normalizedPancakes = pancakeStack.getNormalizedPancakes().getPancakes();
        if (index < 0 || index > normalizedPancakes.length) {
            throw new IllegalArgumentException("Flip operation out of bounds. Trying to flip between " + (index - 1) +
                    " and " + index + " but current stack only has " + normalizedPancakes.length + " pancakes!");
        }
        // System.out.println("Previous: " + Arrays.toString(this.getPancakes()));
        Integer[] normalizedFlipped = this.flip(index, normalizedPancakes);
        int aboutToBeEaten = normalizedFlipped[normalizedFlipped.length - 1];
        // System.out.println("Flipped: " + Arrays.toString(normalizedFlipped));
        pancakeStack.setNormalizedPancakes(
                new PancakeStackData(this.normalize(this.eat(normalizedFlipped), aboutToBeEaten)));

        Integer[] pancakes = pancakeStack.getPancakes().getPancakes();
        Integer[] flipped = this.flip(index, pancakes);
        pancakeStack.setPancakes(new PancakeStackData(this.eat(flipped)));
        // System.out.println("Eaten: " + Arrays.toString(pancakes));
    }

    private Integer[] flip(int index, Integer[] pancakes) {
        if (index == 0) {
            return this.reverse(pancakes);
        }
        if (index == pancakes.length) {
            return pancakes;
        }
        Integer[] bottom = Arrays.copyOfRange(pancakes, 0, index);
        Integer[] top = Arrays.copyOfRange(pancakes, index, pancakes.length);
        top = this.reverse(top);
        return this.merge(bottom, top);
    }

    private Integer[] eat(Integer[] current) {
        Integer[] eaten = new Integer[current.length - 1];
        System.arraycopy(current, 0, eaten, 0, current.length - 1);
        return eaten;
    }

    private Integer[] normalize(Integer[] current, int removed) {
        for (int i = 0; i < current.length; i++) {
            int value = current[i];
            if (value > removed) {
                current[i] = value - 1;
            }
        }
        return current;
    }

    private Integer[] reverse(Integer[] current) {
        Integer[] reverse = new Integer[current.length];
        for (int i = 0; i < current.length; i++) {
            reverse[current.length - 1 - i] = current[i];
        }
        return reverse;
    }

    private Integer[] merge(Integer[] first, Integer[] second) {
        Integer[] combined = new Integer[first.length + second.length];
        System.arraycopy(first, 0, combined, 0, first.length);
        System.arraycopy(second, 0, combined, first.length, second.length);
        return combined;
    }

}
