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
        solver.findPWUE(4); // 9 -> 5
    }


}
