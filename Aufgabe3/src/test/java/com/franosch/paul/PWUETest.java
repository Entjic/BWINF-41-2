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
    public void calcPWUEForNine(){
        int pwue = solver.findPWUE(9);
        Assertions.assertEquals(5, pwue);
    }

    @Test
    public void calcPWUEForTen(){
        int pwue = solver.findPWUE(10, 10);
        Assertions.assertEquals(6, pwue);
    }

    @Test
    public void calcPWUEForEleven(){
        int pwue = solver.findPWUE(11, 10);
        Assertions.assertEquals(6, pwue);
    }

    @Test
    public void calcPWUEForTwelve(){
        int pwue = solver.findPWUE(12, 10);
        Assertions.assertEquals(7, pwue);
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

        11
        https://paste.myplayplanet.tools/imezinicof.cpp
        10 random chosen worst case pancake stacks
        Pancake stack PancakeStackData{pancakes=[4, 11, 6, 8, 7, 1, 5, 10, 3, 9, 2]} -> PancakeStackData{pancakes=[8, 6, 5, 3, 2]} operations [1, 6, 2, 6, 2, 0]
        Pancake stack PancakeStackData{pancakes=[4, 10, 7, 5, 9, 6, 2, 11, 3, 1, 8]} -> PancakeStackData{pancakes=[11, 10, 9, 8, 1]} operations [6, 3, 7, 2, 0, 1]
        Pancake stack PancakeStackData{pancakes=[7, 1, 11, 4, 9, 5, 10, 3, 8, 2, 6]} -> PancakeStackData{pancakes=[11, 10, 9, 8, 6]} operations [1, 6, 4, 5, 0, 4]
        Pancake stack PancakeStackData{pancakes=[3, 9, 7, 2, 6, 1, 10, 5, 11, 4, 8]} -> PancakeStackData{pancakes=[10, 9, 7, 6, 4]} operations [3, 5, 8, 0, 1, 3]
        Pancake stack PancakeStackData{pancakes=[3, 5, 11, 6, 9, 1, 7, 2, 8, 4, 10]} -> PancakeStackData{pancakes=[10, 9, 6, 5, 2]} operations [5, 6, 0, 6, 2, 0]
        Pancake stack PancakeStackData{pancakes=[1, 9, 11, 5, 3, 6, 4, 8, 10, 7, 2]} -> PancakeStackData{pancakes=[11, 10, 7, 6, 3]} operations [0, 3, 3, 0, 4, 1]
        Pancake stack PancakeStackData{pancakes=[6, 8, 3, 11, 7, 5, 1, 10, 4, 9, 2]} -> PancakeStackData{pancakes=[9, 8, 7, 5, 1]} operations [0, 2, 4, 3, 0, 0]
        Pancake stack PancakeStackData{pancakes=[5, 9, 1, 4, 6, 2, 10, 7, 11, 8, 3]} -> PancakeStackData{pancakes=[10, 8, 6, 4, 1]} operations [5, 8, 7, 0, 2, 2]
        Pancake stack PancakeStackData{pancakes=[7, 8, 2, 10, 4, 3, 11, 6, 1, 5, 9]} -> PancakeStackData{pancakes=[7, 6, 5, 4, 3]} operations [6, 1, 7, 2, 2, 3]
        Pancake stack PancakeStackData{pancakes=[5, 11, 3, 6, 1, 4, 10, 2, 7, 9, 8]} -> PancakeStackData{pancakes=[9, 8, 6, 3, 2]} operations [4, 9, 1, 3, 0, 5]
        PWUE of number 11 is 6
        There are 2978556 worst case stacks
        Sorter map contains 43954702 entries
        Timings report
        Time spend finding PWUE nr 464637 ms

        */
    }


}
