package com.franosch.paul;

import com.franosch.paul.solver.SolvingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolverTest {

    private TravelingSalesmanSolver solver;

    @BeforeEach
    public void setUp() {
        solver = new TravelingSalesmanSolver();
    }

    @Test
    public void solveFirstByShortestEdgeFirst() {
        solver.solve(1, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/qifafudoli.css
    }

    @Test
    public void solveSecondByShortestEdgeFirst() {
        solver.solve(2, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/kefegobeja.css
    }

    @Test
    public void solveThirdByShortestEdgeFirst() {
        solver.solve(3, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/xayigarati.css
    }

    @Test
    public void solveFourthByShortestEdgeFirst() {
        solver.solve(4, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/kicemeyoga.css
    }

    @Test
    public void solveFifthByShortestEdgeFirst() { // TODO: 29.03.2023 prove this is not solvable
        solver.solve(5, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/aqigunotub.css
    }

    @Test
    public void solveSixthByShortestEdgeFirst() {
        solver.solve(6, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/joxezedezo.css
    }

    @Test
    public void solveSeventhByShortestEdgeFirst() {
        solver.solve(7, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/iyufokufak.css
    }


}
