package com.franosch.paul;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PWUETest {

    Solver solver;

    @BeforeEach
    void setUp(){
        this.solver = new Solver();
    }

    @Test
    public void calcPWUEForOne() {
        int pwue = solver.findPWUE(1);
        Assertions.assertEquals(0, pwue);
    }

    @Test
    public void calcPWUEForTwo() {
        int pwue = solver.findPWUE(2);
        Assertions.assertEquals(1, pwue);
    }

    @Test
    public void calcPWUEForThree() {
        int pwue = solver.findPWUE(3);
        Assertions.assertEquals(2, pwue);
    }

    @Test
    public void calcPWUEForFour() {
        int pwue = solver.findPWUE(4);
        Assertions.assertEquals(2, pwue);
    }

    @Test
    public void calcPWUEForFive() {
        int pwue = solver.findPWUE(5);
        Assertions.assertEquals(3, pwue);
    }

    @Test
    public void calcPWUEForSix() {
        int pwue = solver.findPWUE(6);
        Assertions.assertEquals(3, pwue);
    }
    @Test
    public void calcPWUEForSeven() {
        int pwue = solver.findPWUE(7);
        Assertions.assertEquals(4, pwue);
    }

    @Test
    public void calcPWUEForEight() {
        int pwue = solver.findPWUE(8);
        Assertions.assertEquals(5, pwue);
    }

    @Test
    public void calcPWUE() {
        solver.findPWUE(11);
        /*
        PWUE of number 9 is 5
        Pancake stack PancakeStackData{pancakes=[7, 3, 8, 9, 1, 5, 2, 6, 4]} -> PancakeStackData{pancakes=[9, 8, 5, 2]} operations [4, 5, 1, 3, 0]
        There are 29091 worst case stacks
        Timings report
        Time spend finding PWUE nr 8297
        Time spend generating pancake stacks 686
        Time spend sorting pancake stacks 7611


        PWUE of number 10 is 6
        Pancake stack PancakeStackData{pancakes=[1, 6, 9, 3, 10, 2, 8, 4, 7, 5]} -> PancakeStackData{pancakes=[5, 4, 3, 2]} operations [0, 8, 1, 1, 2, 3]
        There are 2547 worst case stacks
        Timings report
        Time spend finding PWUE nr 133560
        Time spend generating pancake stacks 5311
        Time spend sorting pancake stacks 128249

        */
    }


}
