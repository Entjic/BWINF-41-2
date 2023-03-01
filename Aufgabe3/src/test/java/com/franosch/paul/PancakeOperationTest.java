package com.franosch.paul;

import com.franosch.paul.model.PancakeStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PancakeOperationTest {

    PancakeFlipper pancakeFlipper;

    @BeforeEach
    public void setUp() {
        this.pancakeFlipper = new PancakeFlipper();
    }

    @Test
    public void pancakeNotSolvedRecognitionTest() {
        PancakeStack first = new PancakeStack(new Byte[]{3, 1, 2});
        Assertions.assertFalse(first.isSolved());
    }

    @Test
    public void pancakeSolvedRecognitionTest() {
        PancakeStack second = new PancakeStack(new Byte[]{3, 2, 1});
        Assertions.assertTrue(second.isSolved());
    }

    @Test
    public void wholePancakeStackFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Byte[]{4, 2, 1, 3});
        this.pancakeFlipper.flip(pancakeStack, 0);
        Assertions.assertArrayEquals(new Byte[]{3, 1, 2}, pancakeStack.getNormalizedPancakes().getPancakes());
    }

    @Test
    public void topPancakeFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Byte[]{5, 1, 4, 2, 3});
        this.pancakeFlipper.flip(pancakeStack, 4);
        Assertions.assertArrayEquals(new Byte[]{5, 1, 4, 2}, pancakeStack.getPancakes().getPancakes());
        Assertions.assertArrayEquals(new Byte[]{4, 1, 3, 2}, pancakeStack.getNormalizedPancakes().getPancakes());
    }

    @Test
    public void outOfBouncePancakeFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Byte[]{1, 2, 3, 4, 5});
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> this.pancakeFlipper.flip(pancakeStack, 7));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> this.pancakeFlipper.flip(pancakeStack, - 3));
    }

    @Test
    public void complexPancakeFlippingTest() {
        PancakeStack pancakeStack = new PancakeStack(new Byte[]{3, 2, 5, 1, 4});
        this.pancakeFlipper.flip(pancakeStack, 3);
        Assertions.assertArrayEquals(new Byte[]{3, 2, 5, 4}, pancakeStack.getPancakes().getPancakes());
        Assertions.assertArrayEquals(new Byte[]{2, 1, 4, 3}, pancakeStack.getNormalizedPancakes().getPancakes());
    }


}
