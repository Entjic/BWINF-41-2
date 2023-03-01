package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackData;

import java.util.Arrays;

public class PancakeFlipper {

    public void flip(PancakeStack pancakeStack, int index) { // flips between index - 1 and index
        Byte[] normalizedPancakes = pancakeStack.getNormalizedPancakes().getPancakes();
        if (index < 0 || index > normalizedPancakes.length) {
            throw new IllegalArgumentException("Flip operation out of bounds. Trying to flip between " + (index - 1) +
                    " and " + index + " but current stack only has " + normalizedPancakes.length + " pancakes!");
        }
        // System.out.println("Previous: " + Arrays.toString(this.getPancakes()));
        Byte[] normalizedFlipped = this.flip(index, normalizedPancakes);
        byte aboutToBeEaten = normalizedFlipped[normalizedFlipped.length - 1];
        // System.out.println("Flipped: " + Arrays.toString(normalizedFlipped));
        pancakeStack.setNormalizedPancakes(
                new PancakeStackData(this.normalize(this.eat(normalizedFlipped), aboutToBeEaten)));

        Byte[] pancakes = pancakeStack.getPancakes().getPancakes();
        Byte[] flipped = this.flip(index, pancakes);
        pancakeStack.setPancakes(new PancakeStackData(this.eat(flipped)));
        // System.out.println("Eaten: " + Arrays.toString(pancakes));
    }

    private Byte[] flip(int index, Byte[] pancakes) {
        if (index == 0) {
            return this.reverse(pancakes);
        }
        if (index == pancakes.length) {
            return pancakes;
        }
        Byte[] bottom = Arrays.copyOfRange(pancakes, 0, index);
        Byte[] top = Arrays.copyOfRange(pancakes, index, pancakes.length);
        top = this.reverse(top);
        return this.merge(bottom, top);
    }

    private Byte[] eat(Byte[] current) {
        Byte[] eaten = new Byte[current.length - 1];
        System.arraycopy(current, 0, eaten, 0, current.length - 1);
        return eaten;
    }

    private Byte[] normalize(Byte[] current, byte removed) {
        for (byte i = 0; i < current.length; i++) {
            byte value = current[i];
            if (value > removed) {
                byte a = (byte) ( value - 1);
                current[i] = a;
            }
        }
        return current;
    }

    private Byte[] reverse(Byte[] current) {
        Byte[] reverse = new Byte[current.length];
        for (int i = 0; i < current.length; i++) {
            reverse[current.length - 1 - i] = current[i];
        }
        return reverse;
    }

    private Byte[] merge(Byte[] first, Byte[] second) {
        Byte[] combined = new Byte[first.length + second.length];
        System.arraycopy(first, 0, combined, 0, first.length);
        System.arraycopy(second, 0, combined, first.length, second.length);
        return combined;
    }

}
