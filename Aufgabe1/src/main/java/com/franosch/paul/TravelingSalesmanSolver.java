package com.franosch.paul;

import com.franosch.paul.eval.SolutionEvaluator;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import com.franosch.paul.solver.*;
import com.franosch.paul.solver.post_optimization.PostOptimization;
import com.franosch.paul.solver.post_optimization.PostOptimizationStrategy;
import com.franosch.paul.solver.post_optimization.TwoOptPostOptimization;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TravelingSalesmanSolver {

    private final static SolvingStrategy DEFAULT_SOLVING_STRATEGY = SolvingStrategy.NEAREST_NEIGHBOUR_HEURISTIC;
    private final static PostOptimizationStrategy DEFAULT_POST_OPTIMIZATION_STRATEGY = PostOptimizationStrategy.TWO_OPT_HEURISTIC;

    public void solve(int number, boolean useTestResources) {
        this.solve(number, useTestResources, DEFAULT_SOLVING_STRATEGY);
    }

    public void solve(int number) {
        this.solve(number, DEFAULT_SOLVING_STRATEGY);
    }

    public void solve(int number, SolvingStrategy solvingStrategy) {
        this.solve(number, false, solvingStrategy);
    }

    public void solve(int number, boolean testResources, SolvingStrategy solvingStrategy) {
        this.solve(number, testResources, solvingStrategy, DEFAULT_POST_OPTIMIZATION_STRATEGY);
    }

    public void solve(int number, boolean useTestResources, SolvingStrategy solvingStrategy, PostOptimizationStrategy postOptimizationStrategy) {
        final GraphGenerator graphGenerator = new GraphGenerator(number, useTestResources);

        Graph graph = graphGenerator.generateGraph();
        Node first = graphGenerator.findStartNode(graph);

        Solver solver = this.createSolver(solvingStrategy);

        List<Node> solved = solver.solve(graph, first);

        final SolutionEvaluator solutionEvaluator = new SolutionEvaluator();

        Double evalNaive = solutionEvaluator.evaluate(graph, solved);

        // this.printInitResult(solved, evalNaive, solvingStrategy);

        PostOptimization postOptimization = this.createPostOptimizer(postOptimizationStrategy);

        List<Node> optimize = postOptimization.optimize(graph, solved);

        Double evalOptimized = solutionEvaluator.evaluate(graph, optimize);

        // this.printPostOptimizationResult(optimize, evalOptimized, postOptimizationStrategy);

        System.out.println("eval naive " + evalNaive);
        System.out.println("eval optimized " + evalOptimized);

        System.out.println("improvement achieved by post optimization algorithm " + (evalNaive - evalOptimized));

    }


    private void printInitResult(List<Node> solved, Double length, SolvingStrategy solvingStrategy) {

        System.out.println("Solving strategy " + solvingStrategy.name() + " resulted in solution with length " + length);
        System.out.println("Full solution: ");
        System.out.println(solved);

    }


    private void printPostOptimizationResult(List<Node> solved, Double length, PostOptimizationStrategy postOptimizationStrategy) {

        System.out.println("Post-optimization strategy " + postOptimizationStrategy.name() + " resulted in solution with length " + length);
        System.out.println("Full solution: ");
        System.out.println(solved);

    }

    private Solver createSolver(SolvingStrategy solvingStrategy) {
        switch (solvingStrategy) {
            case NEAREST_NEIGHBOUR_HEURISTIC -> {
                return new NearestNeighbourHeuristic();
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private PostOptimization createPostOptimizer(PostOptimizationStrategy postOptimizationStrategy) {
        switch (postOptimizationStrategy) {
            case TWO_OPT_HEURISTIC -> {
                return new TwoOptPostOptimization(new SolutionEvaluator());
            }
            default -> throw new IllegalArgumentException();
        }
    }

}
