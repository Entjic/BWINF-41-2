package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PancakeOperationTest {

    @Test
    public void pancakeNotSolvedRecognitionTest() {
        PancakeStack first = new PancakeStack(new Integer[]{3, 1, 2});
        Assertions.assertFalse(first.isSolved());
    }

    @Test
    public void pancakeSolvedRecognitionTest() {
        PancakeStack second = new PancakeStack(new Integer[]{3, 2, 1});
        Assertions.assertTrue(second.isSolved());
    }

    @Test
    public void wholePancakeStackFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Integer[]{4, 2, 1, 3});
        pancakeStack.flip(0);
        Assertions.assertArrayEquals(new Integer[]{3, 1, 2}, pancakeStack.getPancakes());
    }

    @Test
    public void topPancakeFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Integer[]{5, 1, 4, 2, 3});
        pancakeStack.flip(4);
        Assertions.assertArrayEquals(new Integer[]{5, 1, 4, 2}, pancakeStack.getPancakes());
    }

    @Test
    public void outOfBouncePancakeFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Integer[]{1, 2, 3, 4, 5});
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> pancakeStack.flip(7));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> pancakeStack.flip(-3));
    }

    @Test
    public void complexPancakeFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Integer[]{3, 2, 5, 1, 4});
        pancakeStack.flip(3);
        Assertions.assertArrayEquals(new Integer[]{3, 2, 5, 4}, pancakeStack.getPancakes());
    }


}
