package com.franosch.paul;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PWUENumberCalculatorTest {

    private PWUENumberCalculator pwueNumberCalculator;

    @BeforeEach
    public void setUp() {

        final PancakeFlipper pancakeFlipper = new PancakeFlipper();
        final PancakeFlippingOrderApplier pancakeFlippingOrderApplier = new PancakeFlippingOrderApplier(pancakeFlipper);
        final PancakeStackSorter pancakeStackSorter = new PancakeStackSorter(pancakeFlipper, pancakeFlippingOrderApplier);
        this.pwueNumberCalculator = new PWUENumberCalculator(pancakeStackSorter, pancakeFlipper);

    }

    @Test
    public void correctPWUEForHeight1(){
        Assertions.assertEquals(0, pwueNumberCalculator.calcPWUEForHeight(1));
    }

    @Test
    public void correctPWUEForHeight2(){
        Assertions.assertEquals(1,pwueNumberCalculator.calcPWUEForHeight(2));
    }
    @Test
    public void correctPWUEForHeight3(){
        Assertions.assertEquals(2,pwueNumberCalculator.calcPWUEForHeight(3));
    }
    @Test
    public void correctPWUEForHeight4(){
        Assertions.assertEquals(2,pwueNumberCalculator.calcPWUEForHeight(4));
    }
    @Test
    public void correctPWUEForHeight5(){
        Assertions.assertEquals(3,pwueNumberCalculator.calcPWUEForHeight(5));
    }
    @Test
    public void correctPWUEForHeight6(){
        Assertions.assertEquals(3,pwueNumberCalculator.calcPWUEForHeight(6));
    }
    @Test
    public void correctPWUEForHeight7(){
        Assertions.assertEquals(4,pwueNumberCalculator.calcPWUEForHeight(7));
    }

    @Test
    public void correctPWUEForHeight8() {
        Assertions.assertEquals(5,pwueNumberCalculator.calcPWUEForHeight(8));
    }

    @Test
    public void correctPWUEForHeight9(){
        Assertions.assertEquals(5,pwueNumberCalculator.calcPWUEForHeight(9));
    }

    @Test
    public void correctPWUEForHeight10(){
        Assertions.assertEquals(6,pwueNumberCalculator.calcPWUEForHeight(10));
    }

    @Test
    public void correctPWUEForHeight11(){
        Assertions.assertEquals(6,pwueNumberCalculator.calcPWUEForHeight(11));
    }

    @Test
    public void correctPWUEForHeight12(){
        Assertions.assertEquals(7,pwueNumberCalculator.calcPWUEForHeight(12));
    }



}
