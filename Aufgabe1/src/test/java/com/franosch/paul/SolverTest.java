package com.franosch.paul;

import com.franosch.paul.model.Graph;
import com.franosch.paul.solver.SolvingStrategy;
import com.franosch.paul.solver.post_optimization.ParameterConfiguration;
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
        solver.solve(1, true, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/qifafudoli.css
    }

    @Test
    public void solveSecondByShortestEdgeFirst() {
        solver.solve(2, true, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/kefegobeja.css
    }

    @Test
    public void solveThirdByShortestEdgeFirst() {
        solver.solve(3, true, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/xayigarati.css
    }

    @Test
    public void solveFourthByShortestEdgeFirst() {
        solver.solve(4, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/kicemeyoga.css
    }

    @Test
    public void solveFifthByShortestEdgeFirst() {
        solver.solve(5, true, SolvingStrategy.TRAVERSABLE_ORIENTED_WEIGHTED_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/vodemexuca.css
    }

    @Test
    public void solveSixthByShortestEdgeFirst() {
        solver.solve(6, true, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/lowofozese.css
    }

    @Test
    public void solveSeventhByShortestEdgeFirst() {
        solver.solve(7, true, SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC);
        // https://paste.myplayplanet.tools/iyufokufak.css
    }

    @Test
    public void solveFourthWithGoodParams(){
        Graph graph = solver.generateGraphFromFile(4, true);
        solver.solveAndOptimizeViaTwoOptPostOptimization(graph, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC,
                //new ParameterConfiguration(1450.0, 0.975, 10, 19)
                new ParameterConfiguration(500, 0.95, 20, 50)
        );

    }

    @Test
    public void solveFifthWithGoodParams() {
        Graph graph = solver.generateGraphFromFile(5, true);
        solver.solveAndOptimizeViaTwoOptPostOptimization(graph, SolvingStrategy.TRAVERSABLE_ORIENTED_WEIGHTED_NEAREST_NEIGHBOUR_HEURISTIC,
                new ParameterConfiguration(1300.0, 0.985, 10, 16));
        /*

        // https://paste.myplayplanet.tools/ulehitejev.css new ParameterConfiguration(1400.0, 0.9, 11, 16)
        // https://paste.myplayplanet.tools/ajalowodec.css new ParameterConfiguration(1400.0, 0.9, 11, 16)

        // https://paste.myplayplanet.tools/egasiqejoz.css new ParameterConfiguration(1500.0, 0.97, 16, 16));

        BEST BY MIN ParameterConfiguration[startingTemperature=500.0, temperaturModifier=0.99, improvingIterationsUntilCooling=16, iterationsUntilCooling=16] -> 3381.157719454162
        BEST BY MIN ParameterConfiguration[startingTemperature=1300.0, temperaturModifier=0.985, improvingIterationsUntilCooling=10, iterationsUntilCooling=16] -> 3381.157719454162
        BEST BY MIN ParameterConfiguration[startingTemperature=700.0, temperaturModifier=0.985, improvingIterationsUntilCooling=10, iterationsUntilCooling=16] -> 3381.157719454162
        // https://paste.myplayplanet.tools/nibehaqidi.css best ParameterConfiguration[startingTemperature=1300.0, temperaturModifier=0.985, improvingIterationsUntilCooling=10, iterationsUntilCooling=16]

         */
    }

    @Test
    public void solveSixthWithGoodParams(){
        Graph graph = solver.generateGraphFromFile(6, true);
        solver.solveAndOptimizeViaTwoOptPostOptimization(graph, SolvingStrategy.TRAVERSABLE_ORIENTED_WEIGHTED_NEAREST_NEIGHBOUR_HEURISTIC,
                new ParameterConfiguration(1500.0, 0.97, 16, 16));

    }

    @Test
    public void solveSeventhWithGoodParams(){
        Graph graph = solver.generateGraphFromFile(7, true);
        solver.solveAndOptimizeViaTwoOptPostOptimization(graph, SolvingStrategy.ALL_STARTING_NODES_NEAREST_NEIGHBOUR_HEURISTIC,
                //new ParameterConfiguration(1450.0, 0.975, 10, 19)
                new ParameterConfiguration(500, 0.97, 20, 50)
        );

    }


}
