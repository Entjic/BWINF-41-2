package com.franosch.paul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileSolvingTest {

    Solver solver;

    @BeforeEach
    void setUp() {
        solver = new Solver();
    }

    @Test
    public void solveZeroth() {
        solver.solveFile(0, true, true);
    }

    @Test
    public void solveFirst() {
        solver.solveFile(1, true, true);
    }

    @Test
    public void solveSecond() {
        solver.solveFile(2, true, true);
    }

    @Test
    public void solveThird() {
        solver.solveFile(3, true, true);
    }

    @Test
    public void solveFourth() {
        solver.solveFile(4, true, true);
    }

    @Test
    public void solveFifth() {
        solver.solveFile(5, true, true);
    }

    @Test
    public void solveSixth() {
        solver.solveFile(6, true, true);
    }

    @Test
    public void solveSeventh() {
        solver.solveFile(7, true, true);
    }

    @Test
    public void solveCustomHard() {
        solver.solveFile(8, true, true);
    }

}
