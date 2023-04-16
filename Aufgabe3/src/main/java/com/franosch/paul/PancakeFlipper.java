package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import com.franosch.paul.model.PancakeStackData;

import java.util.Arrays;

public class PancakeFlipper {

    /**
     * Flips the pancake stack between index - 1 and index, then eats the last pancake.
     * Also performs the same operations on the normalized pancake stack.
     *
     * @param pancakeStack The pancake stack to flip and eat.
     * @param index The index to flip the pancakes.
     */
    public void flip(PancakeStack pancakeStack, int index) {
        Byte[] normalizedPancakes = pancakeStack.getNormalizedPancakes().getPancakes();
        if (index < 0 || index > normalizedPancakes.length) {
            throw new IllegalArgumentException("Flip operation out of bounds. Trying to flip between " + (index - 1) +
                    " and " + index + " but current stack only has " + normalizedPancakes.length + " pancakes!");
        }

        Byte[] normalizedFlipped = flip(index, normalizedPancakes);
        byte aboutToBeEaten = normalizedFlipped[normalizedFlipped.length - 1];
        pancakeStack.setNormalizedPancakes(new PancakeStackData(normalize(eat(normalizedFlipped), aboutToBeEaten)));

        Byte[] pancakes = pancakeStack.getPancakes().getPancakes();
        Byte[] flipped = flip(index, pancakes);
        pancakeStack.setPancakes(new PancakeStackData(eat(flipped)));
    }

    /**
     * Flips a given pancake stack between index - 1 and index.
     *
     * @param index The index to flip the pancakes.
     * @param pancakes The pancake stack to flip.
     * @return The flipped pancake stack.
     */
    private Byte[] flip(int index, Byte[] pancakes) {
        if (index == 0 || index == pancakes.length) {
            return reverse(pancakes);
        }

        Byte[] flipped = Arrays.copyOf(pancakes, pancakes.length);
        for (int i = index, j = pancakes.length - 1; i < j; i++, j--) {
            byte temp = flipped[i];
            flipped[i] = flipped[j];
            flipped[j] = temp;
        }

        return flipped;
    }

    /**
     * Removes the last pancake from the stack.
     *
     * @param current The pancake stack to remove the last pancake from.
     * @return The pancake stack with the last pancake removed.
     */
    private Byte[] eat(Byte[] current) {
        Byte[] eaten = new Byte[current.length - 1];
        System.arraycopy(current, 0, eaten, 0, current.length - 1);
        return eaten;
    }

    /**
     * Adjusts the pancake stack after eating the last pancake.
     *
     * @param current The pancake stack to normalize.
     * @param removed The value of the removed pancake.
     * @return The normalized pancake stack.
     */
    private Byte[] normalize(Byte[] current, byte removed) {
        for (int i = 0; i < current.length; i++) {
            byte value = current[i];
            if (value > removed) {
                current[i] = (byte) (value - 1);
            }
        }
        return current;
    }

    /**
     * Reverses the order of the given pancake stack.
     *
     * @param current The pancake stack to reverse.
     * @return The reversed pancake stack.
     */
    private Byte[] reverse(Byte[] current) {
        Byte[] reversed = Arrays.copyOf(current, current.length);
        for (int i = 0, j = current.length - 1; i < j; i++, j--) {
            byte temp = reversed[i];
            reversed[i] = reversed[j];
            reversed[j] = temp;
        }
        return reversed;
    }

}
