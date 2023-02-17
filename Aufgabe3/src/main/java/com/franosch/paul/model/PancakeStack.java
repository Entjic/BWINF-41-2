package com.franosch.paul.model;


import lombok.Getter;

import java.util.Arrays;

public class PancakeStack implements Cloneable {
    @Getter
    private Integer[] pancakes; // bottom to top

    public PancakeStack(Integer[] pancakes) {
        this.pancakes = pancakes;
    }

    public boolean isSolved() {
        int last = -1;
        for (final Integer pancake : this.pancakes) {
            if (last == -1) {
                last = pancake;
                continue;
            }
            if (last <= pancake) {
                return false;
            }
            last = pancake;
        }
        return true;
    }

    public void flip(int index) { // flips between index - 1 and index
        if (index < 0 || index > this.pancakes.length) {
            throw new IllegalArgumentException("Flip operation out of bounds. Trying to flip between " + (index - 1) +
                    " and " + index + " but current stack only has " + this.pancakes.length + " pancakes!");
        }
        // System.out.println("Previous: " + Arrays.toString(this.getPancakes()));
        Integer[] flipped = new Integer[0];
        if (index == 0) {
            flipped = this.reverse(this.pancakes);
        }
        if (index == this.pancakes.length) {
            flipped = this.pancakes;
        }
        if (index > 0 && index < this.pancakes.length) {
            Integer[] bottom = Arrays.copyOfRange(this.pancakes, 0, index);
            Integer[] top = Arrays.copyOfRange(this.pancakes, index, this.pancakes.length);
            top = this.reverse(top);
            flipped = this.merge(bottom, top);
        }
        // System.out.println("Flipped: " + Arrays.toString(flipped));
        this.pancakes = this.eat(flipped);
        // System.out.println("Eaten: " + Arrays.toString(pancakes));
    }

    private Integer[] eat(Integer[] current) {
        Integer[] eaten = new Integer[current.length - 1];
        System.arraycopy(current, 0, eaten, 0, current.length - 1);
        return eaten;
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

    @Override
    public String toString() {
        return "PancakeStack{" +
                "pancakes=" + Arrays.toString(pancakes) +
                '}';
    }

    @Override
    public PancakeStack clone() {
        return new PancakeStack(Arrays.copyOf(this.getPancakes(), this.getPancakes().length));
    }
}
