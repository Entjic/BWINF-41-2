package com.franosch.paul;

import org.junit.jupiter.api.Test;

public class SolverTest {

    @Test
    public void solveFirst() {
        Solver solver = new Solver(1, true);
        solver.solve(); // current best 2019.348
    }

    @Test
    public void solveSecond() {
        Solver solver = new Solver(2, true);
        solver.solve(); // current best 4487.818
    }

    @Test
    public void solveThird() {
        Solver solver = new Solver(3, true);
        solver.solve(); // current best 3834.651
    }

    @Test
    public void solveFourth() {
        Solver solver = new Solver(4, true);
        solver.solve(); // current best 2030.169
        
    }

    @Test
    public void solveFifth() {
        Solver solver = new Solver(5, true);
        solver.solve(); // current best 4618.504
    }

    @Test
    public void solveSixth() {
        Solver solver = new Solver(6, true);
        solver.solve(); // current best 5801.475
    }

    @Test
    public void solveSeventh() {
        Solver solver = new Solver(7, true);
        solver.solve(); // current best 6998.678
    }


}
